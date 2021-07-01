package gr.uoa.di.jete.exceptions;


import gr.uoa.di.jete.models.DeveloperId;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(DeveloperId id) {
        super("Could not find Developer "+ id.getUser_id() + " " + id.getProject_id() );
    }

    public DeveloperNotFoundException(Long user_id,Long project_id) {
        super("Current developer " + user_id + " is not the product owner of project " + project_id + ", action denied!");
    }
}