package gr.uoa.di.jete.api;

class ProjectNotFoundException extends RuntimeException {
    ProjectNotFoundException(Long id) {
        super("Could not find Project" + id);
    }
}