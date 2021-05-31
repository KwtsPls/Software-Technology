package gr.uoa.di.jete.api;


class EpicNotFoundException extends RuntimeException {
    EpicNotFoundException(EpicId id) {
        super("Could not find Epic "+ id.getId() + " in Project " + id.getProjectId() );
    }
}