package peaksoft.dto.responses.cheque;

import lombok.Builder;
import peaksoft.dto.responses.menuItem.MenuItemAllResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses.cheque
 * 18.03.2023
 **/
@Builder
public record ChequeResponse(
        Long id,
        String waiterFullName,
        List<MenuItemAllResponse> items,
        Integer averagePrice,
        String  service,
        Integer total,
        String date,
        String message

) {
}
