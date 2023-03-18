package peaksoft.dto.responses.user;

import lombok.Builder;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses
 * 16.03.2023
 **/
@Builder
public record UserTokenResponse (
        String email,
        String token
) {
}
