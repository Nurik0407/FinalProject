package peaksoft.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.ChequeRequest;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.dto.responses.cheque.ChequeResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.OptionalDouble;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.impl
 * 18.03.2023
 **/
@Service
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    public ChequeServiceImpl(ChequeRepository chequeRepository, UserRepository userRepository, MenuItemRepository menuItemRepository) {
        this.chequeRepository = chequeRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<ChequeResponse> findAll() {
        return null;
    }

    @Override
    public SimpleResponse save(ChequeRequest request) {

        MenuItem menuItem = menuItemRepository.findById(request.menuItemId())
                .orElseThrow(() -> new NoSuchElementException(String.format("MenuItem with id: %s not found", request.menuItemId())));

        Cheque cheque = Cheque.builder()
                .user(userRepository.findById(request.userId())
                        .orElseThrow(() -> new NoSuchElementException(String.format("User with id: %s not found", request.userId()))))
                .createdAt(LocalDate.now())
                .build();
        menuItem.addCheque(cheque);
        chequeRepository.save(cheque);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Cheque with MenuItem name: %s successfully saved", menuItem.getName()))
                .build();
    }

    @Override
    public ChequeResponse findById(Long id) {

        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id: %s not found", id)));
        User user = cheque.getUser();
        int sum = cheque.getMenuItems().stream().mapToInt(MenuItem::getPrice).sum();
        cheque.setPriceAverage(sum);
        chequeRepository.save(cheque);


        return ChequeResponse.builder()
                .waiterFullName(user.getLastName() + " " + user.getFirstName())
                .items(menuItemRepository.findAllMenuItem())
                .averagePrice(cheque.getPriceAverage())
                .service(user.getRestaurant().getService())
                .total(cheque.getPriceAverage() + cheque.getPriceAverage() * user.getRestaurant().getService() / 100)
                .build();
    }


    @Override
    public SimpleResponse update(Long id, ChequeRequest request) {

        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with id: %s not found", id)));

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Cheque with id: %s successfully updated", id))
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!chequeRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("Cheque with id: %s not found", id))
                    .build();
        }

        chequeRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Cheque with id: %s successfully deleted", id))
                .build();
    }

    @Override
    public SimpleResponse totalSum(Long id, LocalDate date) {

        Integer top = chequeRepository.getTopByCreatedAt(date, id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("User with id: %s not found", id)));

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Total number of %s %s checks as of the date of %s total: %s",
                        user.getLastName(), user.getFirstName(), date, top))
                .build();
    }


    @Override
    public SimpleResponse avg(LocalDate date) {
        Integer avg = chequeRepository.avg(date);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Average check as of date %s total : %s", date, avg))
                .build();
    }
}
