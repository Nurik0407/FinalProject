package peaksoft.dto.responses.menuItem;


/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses
 * 17.03.2023
 **/
public record MenuItemAllResponse(
        Long id,
        String categoryName,
        String subCategoryName,
        String name,
        Integer price

) {
}
