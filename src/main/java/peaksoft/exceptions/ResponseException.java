package peaksoft.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Zholdoshov Nuradil
 * peaksoft.exceptions.handler
 * 20.03.2023
 **/
@Setter
@Getter
@AllArgsConstructor
public class ResponseException {

    private HttpStatus status;
    private String message;
    private String exceptionClassName;

}
