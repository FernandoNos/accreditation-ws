package io.test.accreditation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAccreditationRequestException extends RuntimeException{
    public InvalidAccreditationRequestException(String message){
        super(message);
    }
}
