package peaksoft.dto.requests;

import lombok.Builder;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.requests
 * 17.03.2023
 **/
@Builder
public record CategoryRequest(
        String name
) {
}
