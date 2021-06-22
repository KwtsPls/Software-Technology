package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(EpicId.class)
@Table(name="epic")
public
class Epic {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private  @Id Long project_id;
    private Long status;
    private String description;
    private String title;

    public Epic(){}
    public Epic(Long project_id,Long status,String description,String title){
        this.project_id = project_id;
        this.status = status;
        this.description = description;
        this.title = title;
    }

    public Long getId() {return this.id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProject_id() { return project_id; }

    public void setProject_id(Long project_id) { this.project_id = project_id; }

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