package gr.uoa.di.jete.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long id) {
        super("Could not find Project" + id);
    }
}