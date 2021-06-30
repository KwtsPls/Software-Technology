package gr.uoa.di.jete.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class AssigneeId implements Serializable {
    @NotNull
    private Long user_id;
    @NotNull
    private Long project_id;
    @NotNull
    private Long story_id;
    @NotNull
    private Long epic_id;
    @NotNull
    private Long sprint_id;
    @NotNull
    private Long task_id;

    public AssigneeId() {}
    public AssigneeId(@NotNull Long user_id, @NotNull Long epic_id, @NotNull Long sprint_id
            , @NotNull Long project_id, @NotNull Long story_id, @NotNull Long task_id) {
        this.user_id = user_id;
        this.epic_id = epic_id;
        this.sprint_id = sprint_id;
        this.project_id = project_id;
        this.story_id = story_id;
        this.task_id = task_id;
    }

    @NotNull
    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(@NotNull Long task_id) {
        this.task_id = task_id;
    }

    @NotNull
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(@NotNull Long user_id) {
        this.user_id = user_id;
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
        if(!(o instanceof AssigneeId))
            return false;
        AssigneeId assigneeId = (AssigneeId) o;
        return Objects.equals(this.user_id, assigneeId.user_id)
                && Objects.equals(this.epic_id, assigneeId.epic_id) && Objects.equals(this.sprint_id, assigneeId.sprint_id)
                && Objects.equals(this.story_id, assigneeId.story_id)
                && Objects.equals(this.project_id, assigneeId.project_id)
                && Objects.equals(this.task_id, assigneeId.task_id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.user_id,this.sprint_id,this.epic_id,this.project_id,this.story_id,this.task_id);
    }
}
