package peaksoft.dto.responses;

import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses
 * 16.03.2023
 **/
@Builder
public record SimpleResponse(
        HttpStatus status,
        String message
) {
}
