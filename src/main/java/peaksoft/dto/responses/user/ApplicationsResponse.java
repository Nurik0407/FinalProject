package peaksoft.dto.responses.user;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.dto.responses
 * 17.03.2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationsResponse implements AbstractApplicationClass {
    private HttpStatus status;
    private  String applicationStatus;
    private List<UserAllResponse> users;



}
