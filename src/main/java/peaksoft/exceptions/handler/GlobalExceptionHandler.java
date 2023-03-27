package peaksoft.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import peaksoft.exceptions.AlreadyExistException;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.exceptions.ResponseException;

import java.util.stream.Collectors;

/**
 * Zholdoshov Nuradil
 * peaksoft.exceptions.handler
 * 20.03.2023
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseException handlerNorFound(NotFoundException e) {
        return new ResponseException(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                NotFoundException.class.getSimpleName()
        );
    }


    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseException handlerAlreadyExists(AlreadyExistException e) {
        return new ResponseException(
                HttpStatus.CONFLICT,
                e.getMessage(),
                AlreadyExistException.class.getSimpleName()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handlerBadRequest(BadRequestException e) {
        return new ResponseException(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                BadRequestException.class.getSimpleName()
        );
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handlerAccessDenied(AccessDeniedException e){
        return new ResponseException(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                AccessDeniedException.class.getSimpleName()
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handlerMethodArgumentNotValid(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String error = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return new ResponseException(HttpStatus.BAD_REQUEST
                , error
                , MethodArgumentNotValidException.class.getSimpleName());
    }



}
