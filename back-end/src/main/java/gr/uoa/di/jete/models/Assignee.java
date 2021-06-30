package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(AssigneeId.class)
public
class Assignee {
    private @Id Long user_id;
    private @Id Long project_id;
    private @Id Long story_id;
    private @Id Long epic_id;
    private @Id Long sprint_id;
    private @Id Long task_id;

    public Assignee(){}
    Assignee(Long epic_id, Long sprint_id, Long project_id, Long story_id, String title, Long status, String description, Long user_id, Long task_id){
        this.epic_id = epic_id;
        this.sprint_id = sprint_id;
        this.project_id = project_id;
        this.story_id = story_id;
        this.user_id = user_id;
        this.task_id = task_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
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
        if(!(o instanceof Assignee))
            return false;
        Assignee assignee = (Assignee) o;
        return Objects.equals(this.user_id, assignee.user_id)
                && Objects.equals(this.epic_id, assignee.epic_id)
                && Objects.equals(this.sprint_id, assignee.sprint_id)
                && Objects.equals(this.project_id, assignee.project_id)
                && Objects.equals(this.story_id,assignee.story_id)
                && Objects.equals(this.task_id,assignee.task_id);

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.user_id,this.sprint_id,this.epic_id,this.project_id,this.story_id,this.task_id);
    }

    @Override
    public String toString(){

        return "Story{" + "user_id=" + this.user_id + ", epic_id=" + this.epic_id + ", sprint_id=" + this.sprint_id + ", story_id="+this.story_id +", project_id=" + this.project_id
                + '\'' + ", task_id=" + this.task_id +'}';
    }
}
