package io.test.accreditation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDocumentNameException extends RuntimeException{
    public InvalidDocumentNameException(){
        super("The documents must be named in the format YYYY.(pdf | jpg)");
    }
}
