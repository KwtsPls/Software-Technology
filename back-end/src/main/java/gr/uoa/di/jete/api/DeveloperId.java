package gr.uoa.di.jete.api;
import com.sun.istack.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class DeveloperId implements Serializable {
    @NotNull
    private Long user_id;
    @NotNull
    private Long project_id;

    public DeveloperId() {}
    public DeveloperId(Long user_id,Long project_id) {
        this.user_id = user_id;
        this.project_id = project_id;
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

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof DeveloperId))
            return false;
        DeveloperId dev_id = (DeveloperId) o;
        return Objects.equals(this.user_id, dev_id.user_id)
                && Objects.equals(this.project_id, dev_id.project_id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.user_id,this.project_id);
    }
}
