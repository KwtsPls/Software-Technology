package gr.uoa.di.jete.api;

class SprintNotFoundException extends RuntimeException {
    SprintNotFoundException(SprintId id) {
        super("Could not find Sprint "+ id.getId() + " in project " + id.getProjectId() );
    }
}
