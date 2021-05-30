package gr.uoa.di.jete.api;


class DeveloperNotFoundException extends RuntimeException {
    DeveloperNotFoundException(DeveloperId id) {
        super("Could not find Developer "+ id.getUserId() + " " + id.getProjectId() );
    }
}