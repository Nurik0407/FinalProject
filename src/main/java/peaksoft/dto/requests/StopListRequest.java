package peaksoft.dto.requests;

import lombok.Builder;

import java.time.LocalDate;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.requests
 * 18.03.2023
 **/
@Builder
public record StopListRequest(
        String reason,
        LocalDate date,
        Long menuItemId
) {
}
