package io.test.accreditation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAccreditationTypeException extends RuntimeException{
    public InvalidAccreditationTypeException(){
        super("An invalid accreditation type was received");
    }
}
