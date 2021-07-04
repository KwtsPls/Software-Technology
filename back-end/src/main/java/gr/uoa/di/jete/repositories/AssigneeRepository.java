package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Assignee;
import gr.uoa.di.jete.models.AssigneeId;
import gr.uoa.di.jete.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssigneeRepository extends JpaRepository<Assignee, AssigneeId>{

    @Query("select u from User u,Assignee a where a.user_id = u.id and a.project_id=?1 and a.task_id=?2")
    List<User> findUsersInTask(Long project_id, Long task_id);

}
