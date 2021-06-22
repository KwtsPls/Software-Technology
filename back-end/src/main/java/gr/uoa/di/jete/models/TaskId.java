package gr.uoa.di.jete.models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

public class TaskId implements Serializable {
    private @NotNull @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @NotNull
    private Long story_id;
    @NotNull
    private Long epic_id;
    @NotNull
    private Long sprint_id;
    @NotNull
    private Long project_id;

    public TaskId() {}
    public TaskId(Long id, Long epic_id, Long sprint_id, Long project_id,Long story_id) {
        this.id = id;
        this.epic_id = epic_id;
        this.sprint_id = sprint_id;
        this.project_id = project_id;
        this.story_id = story_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof TaskId))
            return false;
        TaskId task_id = (TaskId) o;
        return Objects.equals(this.id, task_id.id)
                && Objects.equals(this.epic_id, task_id.epic_id) && Objects.equals(this.sprint_id, task_id.sprint_id)
                && Objects.equals(this.story_id, task_id.story_id)
                && Objects.equals(this.project_id, task_id.project_id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.sprint_id,this.epic_id,this.project_id,this.story_id);
    }
}
