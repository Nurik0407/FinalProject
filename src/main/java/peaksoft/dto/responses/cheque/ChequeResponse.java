package peaksoft.dto.responses.cheque;

import lombok.Builder;
import peaksoft.dto.responses.menuItem.MenuItemAllResponse;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses.cheque
 * 18.03.2023
 **/
@Builder
public record ChequeResponse(
        String waiterFullName,
        List<MenuItemAllResponse> items,
        Integer averagePrice,
        Integer service,
        Integer total
) {
}
