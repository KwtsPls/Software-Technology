package gr.uoa.di.jete.api;


class UserNotFoundException extends RuntimeException {
    UserNotFoundException(Long id) {
        super("Could not find User " + id);
    }
}
