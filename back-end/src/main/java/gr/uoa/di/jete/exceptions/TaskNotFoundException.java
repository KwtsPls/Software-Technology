package gr.uoa.di.jete.exceptions;

import gr.uoa.di.jete.models.TaskId;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(TaskId id) {
        super("Could not find Story "+ id.getId() + " in epic " + id.getEpic_id() + " ,sprint " + id.getSprint_id() + "and story " + id.getStory_id() );
    }
}
