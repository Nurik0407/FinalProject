package peaksoft.dto.responses.menuItem;

import java.math.BigDecimal;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses.menuItem
 * 17.03.2023
 **/
public record MenuItemResponse(
        String name,
        String image,
        Integer price,
        String description,
        Boolean isVegetarian
) {
}
