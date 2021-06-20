package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;

enum epic_status{
    IN_PROGRESS,
    IDLE,
    DONE
}

@Entity
@IdClass(EpicId.class)
public
class Epic {
    private @Id Long id;
    private  @Id Long project_id;
    private Long status;
    private String description;
    private String title;

    public Epic(){}
    Epic(Long project_id,Long status,String description,String title){
        this.project_id = project_id;
        this.status = status;
        this.description = description;
        this.title = title;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) { this.status = status; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Epic))
            return false;
        Epic epic = (Epic) o;
        return Objects.equals(this.id, epic.id)
                && Objects.equals(this.project_id, epic.project_id)
                && Objects.equals(this.status, epic.status)
                && Objects.equals(this.description, epic.description)
                && Objects.equals(this.title, epic.title);

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.project_id,this.title);
    }

    @Override
    public String toString(){
        return "Epic{" + "id=" + this.id + ", project_id=" + this.project_id
                + ", status=" + this.status + ", description='" + this.description + '\'' +
                ", title='" + this.title + '\'' + '}';
    }
}