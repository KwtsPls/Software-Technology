package gr.uoa.di.jete.api;

public class UserInUseException extends IllegalStateException{
    UserInUseException(String username){super("Username " + username + " already in use");}

}
