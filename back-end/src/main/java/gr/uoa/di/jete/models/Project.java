package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;


@Entity
public
class Project {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String title;
    private String description;

    public Project(){}
    Project(String title,String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Project))
            return false;
        Project project = (Project) o;
        return Objects.equals(this.id, project.id) && Objects.equals(this.title, project.title)
                && Objects.equals(this.description, project.description);

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.title);
    }

    @Override
    public String toString(){
        return "Project{" + "id=" + this.id + ", title='" + this.title
                + '\'' + ", description='" + this.description + '\'' + '}';
    }
}