package peaksoft.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.requests.RestaurantRequest;
import peaksoft.dto.responses.restaurant.RestaurantAllResponse;
import peaksoft.dto.responses.restaurant.RestaurantResponse;
import peaksoft.dto.responses.SimpleResponse;
import peaksoft.service.RestaurantService;

import java.util.List;
import java.util.NoSuchElementException;


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


    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<String> handlerExceptions(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("An error occurred: "+e.getMessage());
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public RestaurantResponse getById(@PathVariable Long id){
            return restaurantService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody RestaurantRequest request){
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
    public SimpleResponse update(@PathVariable Long id,@RequestBody RestaurantRequest request){
        return restaurantService.update(id, request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return restaurantService.delete(id);
    }



}
