package gr.uoa.di.jete.models;
import com.sun.istack.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class EpicId implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private Long project_id;

    public EpicId() {}
    public EpicId(Long id,Long project_id) {
        this.id = id;
        this.project_id = project_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return project_id;
    }

    public void setProjectId(Long project_id) {
        this.project_id = project_id;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof EpicId))
            return false;
        EpicId epicId = (EpicId) o;
        return Objects.equals(this.id, epicId.id)
                && Objects.equals(this.project_id, epicId.project_id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.project_id);
    }
}
