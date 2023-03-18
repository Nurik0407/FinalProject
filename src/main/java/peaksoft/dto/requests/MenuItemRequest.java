package peaksoft.dto.requests;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.requests
 * 17.03.2023
 **/
public record MenuItemRequest(
        String name,
        String image,
        @Min(value = 1,message = "Price cannot be negative!")
        Integer price,
        String description,
        Boolean isVegetarian,
        Long restaurantId,
        Long subCategoryId
) {
}
