package peaksoft.exceptions;

/**
 * Zholdoshov Nuradil
 * peaksoft.exceptions.handler
 * 20.03.2023
 **/
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
