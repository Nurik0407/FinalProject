package peaksoft.dto.responses.user;

import lombok.*;
import org.springframework.http.HttpStatus;


/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses.user
 * 17.03.2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse implements AbstractApplicationClass{
    private HttpStatus status;
    private  String applicationStatus;
    private UserResponse user;


    public ApplicationResponse(HttpStatus ok, String format) {

    }
}
