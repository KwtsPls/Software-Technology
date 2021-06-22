package gr.uoa.di.jete.exceptions;


public class PaymentsNotFoundException extends RuntimeException {
    public PaymentsNotFoundException(Long id, Long user_id) {
        super("Not found payment with id: " + id + " for user with id: " +user_id);
    }
}
