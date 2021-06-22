package gr.uoa.di.jete.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PaymentsNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(PaymentsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String paymentNotFound(PaymentsNotFoundException ex){
        return ex.getMessage();
    }
}
