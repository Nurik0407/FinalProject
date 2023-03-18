package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.responses.restaurant.RestaurantAllResponse;
import peaksoft.dto.responses.restaurant.RestaurantResponse;
import peaksoft.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // nujno dobavit sredniy  chek
    @Query("select new peaksoft.dto.responses.restaurant.RestaurantResponse(r.name,r.location,r.resType,r.numberOfEmployees,r.service) from Restaurant r  where r.id=:id")
   Optional<RestaurantResponse> findRestaurantResponseById(Long id);
    @Query("select new peaksoft.dto.responses.restaurant.RestaurantAllResponse(r.name,r.location,c.priceAverage) from Restaurant r join r.users u join u.cheques c  ")
    List<RestaurantAllResponse> findAllRestaurantResponses();
}