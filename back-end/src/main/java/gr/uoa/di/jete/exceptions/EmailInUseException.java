package gr.uoa.di.jete.exceptions;

public class EmailInUseException extends IllegalStateException{
    public EmailInUseException(String email){super("Email " + email + " already in use");}
}
