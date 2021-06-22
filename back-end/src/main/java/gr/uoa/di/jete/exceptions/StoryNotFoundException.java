package gr.uoa.di.jete.exceptions;

import gr.uoa.di.jete.models.StoryId;

public class StoryNotFoundException extends RuntimeException {
    public StoryNotFoundException(StoryId id) {
        super("Could not find Story "+ id.getId() + " in epic " + id.getEpic_id() + " and sprint " + id.getSprint_id() );
    }
}