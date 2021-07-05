package gr.uoa.di.jete.models;

public class ProjectTasks {
    Long id;
    Long story_id;
    Long epic_id;
    Long sprint_id;
    Long project_id;
    String title;
    String description;
    Long status;
    String story_title;
    String epic_title;

    public ProjectTasks(Long id,Long story_id,Long epic_id,Long sprint_id,Long project_id
            ,String title,String description,Long status,String story_title,String epic_title){

        this.id = id;
        this.story_id = story_id;
        this.epic_id = epic_id;
        this.sprint_id = sprint_id;
        this.project_id = project_id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.story_title = story_title;
        this.epic_title = epic_title;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStatus() {
        return status;
    }

    public String getEpic_title() {
        return epic_title;
    }

    public Long getEpic_id() {
        return epic_id;
    }

    public Long getSprint_id() {
        return sprint_id;
    }

    public void setSprint_id(Long sprint_id) {
        this.sprint_id = sprint_id;
    }

    public void setStory_id(Long story_id) {
        this.story_id = story_id;
    }

    public Long getStory_id() {
        return story_id;
    }

    public String getStory_title() {
        return story_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEpic_id(Long epic_id) {
        this.epic_id = epic_id;
    }

    public void setEpic_title(String epic_title) {
        this.epic_title = epic_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

}
