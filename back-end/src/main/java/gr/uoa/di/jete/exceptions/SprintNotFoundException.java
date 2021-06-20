package gr.uoa.di.jete.exceptions;

import gr.uoa.di.jete.models.SprintId;

public class SprintNotFoundException extends RuntimeException {
    public SprintNotFoundException(SprintId id) {
        super("Could not find Sprint "+ id.getId() + " in project " + id.getProjectId() );
    }
}
