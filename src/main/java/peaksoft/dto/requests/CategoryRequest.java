package peaksoft.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.requests
 * 17.03.2023
 **/
@Builder
public record CategoryRequest(
        @NotBlank(message = "Name cannot be empty!")
        String name
) {
}
