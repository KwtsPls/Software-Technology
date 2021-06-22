package gr.uoa.di.jete.models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

public class EpicId implements Serializable {
    private @NotNull @GeneratedValue(strategy = GenerationType.AUTO) Long id;
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
