package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(DeveloperId.class)
@Table(name="developer")
public
class Developer {
    private @Id Long user_id;
    private  @Id Long project_id;
    private Long role;
    private Long accepted;

    public Developer(){}
    public Developer(Long user_id, Long project_id, Long role,Long accepted){
        this.user_id = user_id;
        this.project_id = project_id;
        this.role = role;
        this.accepted = accepted;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getAccepted() {
        return accepted;
    }

    public void setAccepted(Long accepted) {
        this.accepted = accepted;
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
        return "Developer{" + "user_id=" + this.getUser_id() + ", project_id=" + this.getProject_id()
                + '\'' + ", role=" + this.role + '\'' + '}';
    }
}
