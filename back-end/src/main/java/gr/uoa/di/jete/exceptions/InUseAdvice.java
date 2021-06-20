package gr.uoa.di.jete.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InUseAdvice{
    @ResponseBody
    @ExceptionHandler(EmailInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String emailInUseHandler(EmailInUseException ex){return ex.getMessage();}

    @ResponseBody
    @ExceptionHandler(UserInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String emailInUseHandler(UserInUseException ex){return ex.getMessage();}


}
