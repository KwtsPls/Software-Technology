package gr.uoa.di.jete.models;

import com.sun.istack.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class StoryId implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private Long epic_id;
    @NotNull
    private Long sprint_id;
    @NotNull
    private Long project_id;

    public StoryId() {}
    public StoryId(Long id, Long epic_id, Long sprint_id, Long project_id) {
        this.id = id;
        this.epic_id = epic_id;
        this.sprint_id = sprint_id;
        this.project_id = project_id;
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

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof StoryId))
            return false;
        StoryId story_id = (StoryId) o;
        return Objects.equals(this.id, story_id.id)
                && Objects.equals(this.epic_id, story_id.epic_id) && Objects.equals(this.sprint_id, story_id.sprint_id)
                && Objects.equals(this.project_id, story_id.project_id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.sprint_id,this.epic_id,this.project_id);
    }
}
