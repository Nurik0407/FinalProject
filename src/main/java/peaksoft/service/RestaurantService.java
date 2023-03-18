package peaksoft.service;

import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.restaurant.RestaurantAllResponse;
import peaksoft.dto.responses.restaurant.RestaurantResponse;
import peaksoft.dto.responses.SimpleResponse;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 16.03.2023
 **/
public interface RestaurantService {


    RestaurantResponse getById(Long id);

    SimpleResponse save(RestaurantRequest request);

    List<RestaurantAllResponse> findAll();

    SimpleResponse delete(Long id);
    SimpleResponse update(Long id,RestaurantRequest request);
}
