package peaksoft.dto.requests;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.requests
 * 18.03.2023
 **/
public record ChequeRequest(

        Long userId,
        List<Long> menuItemIds
)
{}
