package org.simbirgo.exceptions.exceptionhandlers;


import org.simbirgo.exceptions.InvalidDataException;
import org.simbirgo.exceptions.NoRecordFoundException;
import org.simbirgo.exceptions.RecordAlreadyExistException;
import org.simbirgo.exceptions.UserAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<?> handleInvalidDataException(InvalidDataException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoRecordFoundException.class)
    public ResponseEntity<?> handleNoRecordFoundException(NoRecordFoundException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RecordAlreadyExistException.class)
    public ResponseEntity<?> handleRecordAlreadyExistException(RecordAlreadyExistException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAccessException.class)
    public ResponseEntity<?> handleUserAccessException(UserAccessException e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
