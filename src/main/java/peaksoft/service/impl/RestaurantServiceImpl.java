package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.restaurant.RestaurantAllResponse;
import peaksoft.dto.responses.restaurant.RestaurantResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.impl
 * 16.03.2023
 **/
@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ChequeRepository chequeRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ChequeRepository chequeRepository) {
        this.restaurantRepository = restaurantRepository;
        this.chequeRepository = chequeRepository;
    }


    @Override
    public RestaurantResponse getById(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with id: %s not found!", id)));
        restaurant.setNumberOfEmployees(restaurant.getUsers().size());
        restaurantRepository.save(restaurant);

        return restaurantRepository.findRestaurantResponseById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with id: %s not found!", id)));
    }

    @Override
    public SimpleResponse save(RestaurantRequest request) {

        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .location(request.location())
                .resType(request.resType())
                .service(request.service())
                .build();

        restaurantRepository.save(restaurant);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s successfully saved!", restaurant.getName()))
                .build();
    }

    @Override
    public List<RestaurantAllResponse> findAll() {
        return restaurantRepository.findAllRestaurantResponses();
    }

    @Override
    public SimpleResponse delete(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id: %s not found!", id)));

        restaurant.getUsers().forEach(u->u.getCheques().forEach(c->chequeRepository.deleteById(c.getId())));

        restaurantRepository.deleteById(id);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with id: %s successfully deleted", id))
                .build();
    }

    @Override
    public SimpleResponse update(Long id, RestaurantRequest request) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with id: %s successfully updated", id)));
        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setResType(request.resType());
        restaurant.setService(request.service());

        restaurantRepository.save(restaurant);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with id: %s successfully updated", id))
                .build();
    }
}
