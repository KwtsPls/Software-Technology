package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(TaskId.class)
public
class Task {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private  @Id Long epic_id;
    private @Id Long sprint_id;
    private @Id Long project_id;
    private @Id Long story_id;
    private String title;
    private String description;
    private Long status;

    public Task(){}
    Task(Long epic_id, Long sprint_id, Long project_id, Long story_id, String title, Long status, String description){
        this.epic_id = epic_id;
        this.sprint_id = sprint_id;
        this.project_id = project_id;
        this.story_id = story_id;
        this.title = title;
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getStory_id() {
        return story_id;
    }

    public void setStory_id(Long story_id) {
        this.story_id = story_id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Task))
            return false;
        Task task = (Task) o;
        return Objects.equals(this.id, task.id)
                && Objects.equals(this.epic_id, task.epic_id)
                && Objects.equals(this.sprint_id, task.sprint_id)
                && Objects.equals(this.project_id, task.project_id)
                && Objects.equals(this.story_id,task.story_id)
                && Objects.equals(this.title, task.title)
                && Objects.equals(this.description, task.description)
                && Objects.equals(this.status, task.status);

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.sprint_id,this.epic_id,this.project_id,this.story_id,this.title);
    }

    @Override
    public String toString(){

        return "Story{" + "id=" + this.id + ", epic_id=" + this.epic_id + ", sprint_id=" + this.sprint_id + ", story_id="+this.story_id +", project_id=" + this.project_id
                + '\'' + ", status=" + this.status
                + ", title='" + this.title + '\'' + ", description="+this.description +'}';
    }
}
