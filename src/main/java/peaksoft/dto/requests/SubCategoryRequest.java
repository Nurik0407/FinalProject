package peaksoft.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto
 * 17.03.2023
 **/
public record SubCategoryRequest(
        @NotBlank(message = "Name cannot be empty!")
        String name,

        @Positive(message = "categoryId cannot be empty!")
        Long categoryId
) {
}
