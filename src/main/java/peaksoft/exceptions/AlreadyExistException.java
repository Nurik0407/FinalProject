package peaksoft.exceptions;

/**
 * Zholdoshov Nuradil
 * peaksoft.exceptions.handler
 * 23.03.2023
 **/
public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException() {
        super();
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
