package peaksoft.dto.responses.subCategory;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses.subCategory
 * 17.03.2023
 **/
public record SubCategoryResponsesByCategory(
        @JsonIgnore
        String categoryName,
        Long id,
        String subCategoryName
) {
}
