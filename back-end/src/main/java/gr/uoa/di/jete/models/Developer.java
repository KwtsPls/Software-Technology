package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(DeveloperId.class)
public
class Developer {
    private @Id Long user_id;
    private  @Id Long project_id;
    private Long role;

    public Developer(){}
    public Developer(Long user_id, Long project_id, Long role){
        this.user_id = user_id;
        this.project_id = project_id;
        this.role = role;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public Long getProjectId() {
        return project_id;
    }

    public void setProjectId(Long project_id) {
        this.project_id = project_id;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Developer))
            return false;
        Developer developer = (Developer) o;
        return Objects.equals(this.user_id, developer.user_id)
                && Objects.equals(this.project_id, developer.project_id)
                && Objects.equals(this.role, developer.role);

    }

    @Override
    public int hashCode(){
       return Objects.hash(this.user_id,this.project_id);
    }

    @Override
    public String toString(){
        return "Developer{" + "user_id=" + this.getUserId() + ", project_id=" + this.getProjectId()
                + '\'' + ", role=" + this.role + '\'' + '}';
    }
}
