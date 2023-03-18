package peaksoft.dto.responses.stopList;

import lombok.Builder;

import java.time.LocalDate;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses.stopList
 * 18.03.2023
 **/
@Builder
public record StopListResponse(
        String menuItemName,
        String reason,
        LocalDate date
) {
}
