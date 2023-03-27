package peaksoft.dto.responses.restaurant;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses
 * 16.03.2023
 **/
public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String resType,
        Integer numberOfEmployees,
        Integer service
) {
}
