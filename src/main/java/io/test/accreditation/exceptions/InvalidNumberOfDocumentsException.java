package io.test.accreditation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidNumberOfDocumentsException extends RuntimeException{
    public InvalidNumberOfDocumentsException(){
        super("At least 2 documents must be provided");
    }
}
