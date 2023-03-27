package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.requests.AuthUserRequest;
import peaksoft.dto.requests.UserRequest;
import peaksoft.dto.responses.*;
import peaksoft.dto.responses.user.*;
import peaksoft.entity.Cheque;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.impl
 * 16.03.2023
 **/
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.restaurantRepository = restaurantRepository;
    }


    @PostConstruct
    public void init() {
        User user = User.builder()
                .firstName("Admin")
                .lastName("Adminov")
                .email("admin@gmail.com")
                .password(encoder.encode("admin123"))
                .role(Role.ADMIN)
                .accepted(false)
                .build();
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    public UserTokenResponse authenticate(AuthUserRequest userRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(),
                        userRequest.password()
                )
        );

        User user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with email: %s not found!", userRequest.email())));

        String token = jwtUtil.generateToken(user);

        return UserTokenResponse.builder()
                .email(user.getEmail())
                .token(token)
                .build();
    }

    @Override
    public List<UserAllResponse> findAll(String role, String lastName) {
        if (role == null && lastName == null) {
            return userRepository.findAllUserResponses();
        } else if (role != null) {
            return userRepository.findAllUserResponsesByRole(role.toUpperCase() + "%");
        } else {
            return userRepository.findAllUserResponsesByLastName(lastName + "%");
        }
    }

    @Override
    public SimpleResponse save(UserRequest request) {

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with id: %s not found!", request.restaurantId())));

        uniqueEmailAndPhoneNumber(request.email(),request.phoneNumber());
        validationUser(request);

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .dateOfBirth(request.dateOfBirth())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(request.role())
                .phoneNumber(request.phoneNumber())
                .experience(request.experience())
                .restaurant(restaurant)
                .accepted(true)
                .build();

        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with fullName: %s %s successfully saved!", user.getLastName(), user.getFirstName()))
                .build();
    }

    @Override
    public UserResponse findById(Long id) {
        return userRepository.findUserResponseById(id)
                .orElseThrow(() ->
                        new NotFoundException(String.format("User with id: %s doesn't exist!", id)));
    }

    @Override
    public SimpleResponse update(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id: %s not found!", id)));

        /** Validations **/

        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new AlreadyExistException(String.format("User with email: %s already exists!", request.email()));
        }

        if (userRepository.existsByPhoneNumberAndIdNot(request.phoneNumber(), id)) {
            throw new AlreadyExistException(String.format("User with phone number: %s already exists!", request.phoneNumber()));
        }

      validationUser(request);

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setDateOfBirth(request.dateOfBirth());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        user.setRole(request.role());
        user.setExperience(request.experience());

        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id: %s successfully updated", id))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {

        if (!userRepository.existsById(id)) {
            throw new NotFoundException(
                    String.format("User with id: %s not found!", id));
        }

        User user = userRepository.findById(id).
                orElseThrow(() -> new NotFoundException(
                        String.format("User with id: %s not found!", id)));

        List<Cheque> cheques = user.getCheques();

        cheques.forEach(s -> s.setUser(null));

        user.getRestaurant().deleteUser(user);

        userRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("User with id: %s successfully deleted", id))
                .build();
    }

    @Override
    public SimpleResponse application(UserRequest request) {

        if (request.role().equals(Role.ADMIN)) {
            throw new BadRequestException("There is no vacancy for the administrator!");
        }
        /** Validations **/

        uniqueEmailAndPhoneNumber(request.email(), request.phoneNumber());
        validationUser(request);

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .dateOfBirth(request.dateOfBirth())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(request.role())
                .phoneNumber(request.phoneNumber())
                .experience(request.experience())
                .accepted(false)
                .build();

        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your application has been successfully sent")
                .build();
    }

    @Override
    public AbstractApplicationClass applications(Long id, Boolean accepted) {

        if (accepted == null && id != null) {

            if (userRepository.findAllByAcceptedFalse().stream().noneMatch(u -> u.getId().equals(id))) {
                return new ApplicationResponse(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id: %s not in requests", id),
                        null);
            }

            return new ApplicationResponse(
                    HttpStatus.OK,
                    "User with id: " + id,
                    userRepository.findUserResponseById(id)
                            .orElseThrow(() -> new NotFoundException(
                                    String.format("User with id: %s not found!", id))));
        }

        if (id != null && Boolean.TRUE.equals(accepted)) {
            List<User> users = userRepository.findAllByAcceptedFalse();
            if (users != null) {

                boolean isExists = userRepository.findAllByAcceptedFalse().stream().anyMatch(u -> u.getId().equals(id));
                if (!isExists) {
                    return new ApplicationResponse(
                            HttpStatus.NOT_FOUND,
                            String.format("User with id: %s not in requests", id),
                            null);
                }

                Restaurant restaurant = restaurantRepository.findById(1L)
                        .orElseThrow(() -> new NotFoundException(
                                String.format("Restaurant with id: %s not found!", 1L)));

                User user = users.stream().filter(u -> u.getId().equals(id)).findFirst().
                        orElseThrow(() -> new NotFoundException(
                                String.format("User with id: %s not found!", id)));

                user.setRestaurant(restaurant);
                user.setAccepted(true);

                userRepository.save(user);

                return new ApplicationResponse(
                        HttpStatus.OK,
                        String.format("User with fullName: %s %s successfully accepted",
                                user.getLastName(), user.getFirstName()),
                        userRepository.findUserResponseById(id)
                                .orElseThrow(() -> new NotFoundException(
                                        String.format("User with ID: %s not found", id))));

            }

        } else if (id != null) {
            List<User> users = userRepository.findAllByAcceptedFalse();
            if (users != null) {

                boolean isExists = userRepository.findAllByAcceptedFalse().stream().anyMatch(u -> u.getId().equals(id));
                if (!isExists) {
                    return new ApplicationResponse(
                            HttpStatus.NOT_FOUND,
                            String.format("User with id: %s not in requests", id)
                    );
                }

                userRepository.deleteById(id);
                return new ApplicationResponse(
                        HttpStatus.OK,
                        String.format("User with id: %s successfully not accepted!", id),
                        null);
            }
        } else if (accepted == null) {
            return new ApplicationsResponse(
                    HttpStatus.OK,
                    "Not accepted",
                    userRepository.findAllUserResponsesByAcceptedFalse());
        }
        return new ApplicationsResponse(
                HttpStatus.OK,
                "Not accepted",
                userRepository.findAllUserResponsesByAcceptedFalse());
    }

    private void validationUser(UserRequest request) {

        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with id: %s not found!", request.restaurantId())));

        if (restaurant.getUsers().size() > 15) {
            throw new BadRequestException("The number of employees cannot be more than 15");
        }

        if (request.role().equals(Role.CHEF)) {
            Period period = Period.between(request.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 25 || period.getYears() > 45) {
                throw new BadRequestException("For the vacancy of a cook, the age range is from 25 to 45 years!");
            }
            if (request.experience() <= 1) {
                throw new BadRequestException("Cooking experience must be at least 2 years!");
            }
        } else if (request.role().equals(Role.WAITER)) {
            Period period = Period.between(request.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 18 || 30 < period.getYears()) {
                throw new BadRequestException("For the vacancy of a waiter, the age range is from 18 to 30 years!");
            }
            if (request.experience() <= 1) {
                throw new BadRequestException("Experience as a waiter must be at least 1 year!");
            }
        }
    }

    private void uniqueEmailAndPhoneNumber(String email, String phoneNumber) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistException(String.format("User with email: %s already exists!", email));
        }

        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new AlreadyExistException(String.format("User with phone number: %s already exists!", phoneNumber));
        }


    }


}

