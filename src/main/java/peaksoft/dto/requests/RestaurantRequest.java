package peaksoft.dto.requests;

import jakarta.validation.constraints.NotBlank;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.requests
 * 16.03.2023
 **/
public record RestaurantRequest(
        @NotBlank(message = "name cannot be empty!")
        String name,
        String location,
        String resType,
        Integer service
) {
}
