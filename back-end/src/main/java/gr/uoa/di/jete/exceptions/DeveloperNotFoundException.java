package gr.uoa.di.jete.exceptions;


import gr.uoa.di.jete.models.DeveloperId;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(DeveloperId id) {
        super("Could not find Developer "+ id.getUser_id() + " " + id.getProject_id() );
    }
}