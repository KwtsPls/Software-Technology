package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(StoryId.class)
public
class Story {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private  @Id Long epic_id;
    private @Id Long sprint_id;
    private @Id Long project_id;
    private String title;
    private String description;
    private Long status;

    public Story(){}
    Story(Long epic_id, Long sprint_id, Long project_id, String title, Long status, String description){
        this.epic_id = epic_id;
        this.sprint_id = sprint_id;
        this.project_id = project_id;
        this.title = title;
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long id) {
        this.id = id;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getEpic_id() {
        return epic_id;
    }

    public void setEpic_id(Long epic_id) {
        this.epic_id = epic_id;
    }

    public Long getSprint_id() {
        return sprint_id;
    }

    public void setSprint_id(Long sprint_id) {
        this.sprint_id = sprint_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Story))
            return false;
        Story story = (Story) o;
        return Objects.equals(this.id, story.id)
                && Objects.equals(this.epic_id, story.epic_id)
                && Objects.equals(this.sprint_id, story.sprint_id)
                && Objects.equals(this.project_id, story.project_id)
                && Objects.equals(this.title, story.title)
                && Objects.equals(this.description, story.description)
                && Objects.equals(this.status, story.status);

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.sprint_id,this.epic_id,this.project_id,this.title);
    }

    @Override
    public String toString(){

        return "Story{" + "id=" + this.id + ", epic_id=" + this.epic_id + ", sprint_id=" + this.sprint_id + ", project_id=" + this.project_id
                + '\'' + ", status=" + this.status
                + ", title='" + this.title + '\'' + ", description="+this.description +'}';
    }
}
