package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.restaurant.RestaurantAllResponse;
import peaksoft.dto.responses.restaurant.RestaurantResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.service.RestaurantService;

import java.util.List;


/**
 * Zholdoshov Nuradil
 * peaksoft.api
 * 16.03.2023
 **/
@RestController
@RequestMapping("/api/restaurant")
public class RestaurantAPI {
    private final RestaurantService restaurantService;

    public RestaurantAPI(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public RestaurantResponse getById(@PathVariable Long id){
            return restaurantService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid RestaurantRequest request){
        return restaurantService.save(request);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<RestaurantAllResponse> findAll(){
        return restaurantService.findAll();
    }


    /**  Peredelat **/
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping ("/{id}")
    public SimpleResponse update(@PathVariable Long id,@RequestBody @Valid RestaurantRequest request){
        return restaurantService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return restaurantService.delete(id);
    }



}
