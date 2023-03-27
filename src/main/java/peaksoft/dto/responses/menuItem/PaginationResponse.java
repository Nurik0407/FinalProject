package peaksoft.dto.responses.menuItem;

import lombok.*;
import peaksoft.dto.responses.menuItem.MenuItemAllResponse;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses.pagination
 * 23.03.2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationResponse {
    private List<MenuItemAllResponse> users;
    private int currentPage;
    private int pageSize;
}
