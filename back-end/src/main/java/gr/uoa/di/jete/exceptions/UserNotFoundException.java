package gr.uoa.di.jete.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find User " + id);
    }

    UserNotFoundException(String username){
        super("Could not find User " + username);
    }
}

