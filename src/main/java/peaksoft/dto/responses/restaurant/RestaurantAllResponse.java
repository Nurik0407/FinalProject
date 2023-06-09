package peaksoft.dto.responses.restaurant;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses
 * 17.03.2023
 **/
@Builder
public record RestaurantAllResponse(
        Long id,
        String name,
        String location,
        Integer averageCheck
) {
}
