package io.test.accreditation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDocumentDateException extends RuntimeException{
    public InvalidDocumentDateException(){
        super("The documents must be the last two years tax returns");
    }
}
