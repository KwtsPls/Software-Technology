package gr.uoa.di.jete.models;

import javax.persistence.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@IdClass(SprintId.class)
@Table(name="sprint")
public
class Sprint {
    private @Id Long id;
    private  @Id Long project_id;
    private String title;
    private Long status;
    private Date date_from;
    private Date date_to;

    public Sprint(){}
    public Sprint(Long id,Long project_id,String title,Long status,Date dateFrom,Date dateTo){
        this.id = id;
        this.project_id = project_id;
        this.title = title;
        this.status = status;
        this.date_from = dateFrom;
        this.date_to = dateTo;
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

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
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
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dateFromStr = df.format(date_from);
        String dateToStr = df.format(date_to);

        return "Sprint{" + "id=" + this.id + ", project_id=" + this.id
                + '\'' + ", status=" + this.status
                + ", title='" + this.title + '\'' + ", dateFrom="+dateFromStr+", dateTo="+dateToStr +'}';
    }


}
