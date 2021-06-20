package gr.uoa.di.jete.exceptions;


import gr.uoa.di.jete.models.EpicId;

public class EpicNotFoundException extends RuntimeException {
    public EpicNotFoundException(EpicId id) {
        super("Could not find Epic "+ id.getId() + " in Project " + id.getProjectId() );
    }
}