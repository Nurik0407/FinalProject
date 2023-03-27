package peaksoft.dto.responses.subCategory;

import lombok.Builder;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses
 * 17.03.2023
 **/
@Builder
public record SubCategoryResponse(
        Long id,
        String name
) {
}
