package gr.uoa.di.jete.exceptions;

public class InvalidUserRegistration extends RuntimeException {
    public InvalidUserRegistration() {
        super("Invalid Input for Registration");
    }
}
