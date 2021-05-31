package gr.uoa.di.jete.api;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DeveloperNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(DeveloperNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(DeveloperNotFoundException ex){
        return ex.getMessage();
    }

}