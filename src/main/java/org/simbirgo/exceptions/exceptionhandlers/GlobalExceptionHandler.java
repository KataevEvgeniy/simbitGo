package org.simbirgo.exceptions.exceptionhandlers;


import org.simbirgo.exceptions.InvalidDataException;
import org.simbirgo.exceptions.NoRecordFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<?> handleInvalidDataException(InvalidDataException e){
        System.out.println("Invalid data ianaidgnasdkmnjjfjnfdn");
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoRecordFoundException.class)
    public ResponseEntity<?> handleNoRecordFound(NoRecordFoundException e){
        System.out.println("Invalid data ianaidgnasdkmnjjfjnfdn");
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
