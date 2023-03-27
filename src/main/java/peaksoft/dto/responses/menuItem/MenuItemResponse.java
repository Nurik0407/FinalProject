package peaksoft.dto.responses.menuItem;


/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses.menuItem
 * 17.03.2023
 **/
public record MenuItemResponse(
        Long id,
        String name,
        String image,
        Integer price,
        String description,
        Boolean isVegetarian
) {
}
