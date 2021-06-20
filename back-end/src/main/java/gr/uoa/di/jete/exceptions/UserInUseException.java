package gr.uoa.di.jete.exceptions;

public class UserInUseException extends IllegalStateException{
    public UserInUseException(String username){super("Username " + username + " already in use");}

}
