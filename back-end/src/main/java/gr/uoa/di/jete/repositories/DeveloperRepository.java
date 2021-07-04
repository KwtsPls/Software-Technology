package gr.uoa.di.jete.repositories;


import gr.uoa.di.jete.models.Developer;
import gr.uoa.di.jete.models.DeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, DeveloperId>{

    @Query(value = "select u.id,u.username,d.accepted,d.role from User u,Developer d where u.id = d.user_id and d.project_id = ?1")
    List<?> findDeveloperInfoInProject(Long project_id);

    @Query("select p.id,p.title,u1.username from Project p,User u,User u1,Developer d,Developer d1 where u.id=?1 and d.user_id=u.id and p.id = d.project_id and d.project_id = d1.project_id and d1.user_id = u1.id and d1.role=1 and d.accepted=0")
    List<Tuple> findProjectInvitations(Long user_id);

    @Transactional
    @Modifying
    @Query("update Developer d set d.accepted=1 where d.user_id=?1 and d.project_id=?2")
    int setDeveloperAcceptedTrue(Long user_id,Long project_id);

    @Query("select count(d.accepted) from Developer d where d.accepted=0 and d.user_id=?1")
    List<?> getProjectsNotificationNumber(Long user_id);

    @Transactional
    @Modifying
    @Query("delete from Assignee a where a.user_id=?1 and a.project_id=?2")
    int deleteAssigneeDeveloper(Long user_id,Long project_id);
}
