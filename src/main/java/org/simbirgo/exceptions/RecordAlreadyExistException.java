package org.simbirgo.exceptions;

public class RecordAlreadyExistException extends RuntimeException {

    public RecordAlreadyExistException(String message){
        super(message);
    }
}
