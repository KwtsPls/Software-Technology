package gr.uoa.di.jete.models;

public class ProjectInvitation {
    Long project_id;
    String title;
    String owner_username;

    public ProjectInvitation(Long project_id,String title,String owner_username){
        this.project_id = project_id;
        this.title = title;
        this.owner_username = owner_username;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public void setOwner_username(String owner_username) {
        this.owner_username = owner_username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
