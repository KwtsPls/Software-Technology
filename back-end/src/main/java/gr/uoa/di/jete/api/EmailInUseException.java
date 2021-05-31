package gr.uoa.di.jete.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmailInUseException extends IllegalStateException{
    EmailInUseException(String email){super("Email " + email + " already in use");}
}
