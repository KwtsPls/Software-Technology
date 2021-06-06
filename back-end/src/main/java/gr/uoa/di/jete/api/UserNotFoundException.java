package gr.uoa.di.jete.api;


class UserNotFoundException extends RuntimeException {
    UserNotFoundException(Long id) {
        super("Could not find User " + id);
    }

    UserNotFoundException(String username){
        super("Could not find User " + username);
    }
}

class InvalidUserRegistration extends RuntimeException {
    InvalidUserRegistration() {
        super("Invalid Input for Registration");
    }
}
