package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(SprintId.class)
public
class Sprint {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private  @Id Long project_id;
    private String title;
    private Long status;

    public Sprint(){}
    Sprint(Long project_id,String title,Long status){
        this.project_id = project_id;
        this.title = title;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long id) {
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

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Sprint))
            return false;
        Sprint sprint = (Sprint) o;
        return Objects.equals(this.id, sprint.id)
                && Objects.equals(this.project_id, sprint.project_id)
                && Objects.equals(this.title, sprint.title)
                && Objects.equals(this.status, sprint.status);

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.project_id,this.title);
    }

    @Override
    public String toString(){
        return "Sprint{" + "id=" + this.id + ", project_id=" + this.id
                + '\'' + ", status=" + this.status
                + ", title='" + this.title + '\'' + '}';
    }
}
