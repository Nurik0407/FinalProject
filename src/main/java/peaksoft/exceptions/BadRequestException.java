package peaksoft.exceptions;

/**
 * Zholdoshov Nuradil
 * peaksoft.exceptions.handler
 * 23.03.2023
 **/
public class BadRequestException extends RuntimeException{
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
