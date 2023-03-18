package peaksoft.dto.responses.user;

import lombok.Builder;
import peaksoft.enums.Role;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses
 * 16.03.2023
 **/
@Builder
public record UserAllResponse(
        Long id,
        String fullName,
        String email,
        Role role
){
}
