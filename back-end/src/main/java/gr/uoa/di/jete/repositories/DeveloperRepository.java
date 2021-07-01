package gr.uoa.di.jete.repositories;


import gr.uoa.di.jete.models.Developer;
import gr.uoa.di.jete.models.DeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, DeveloperId>{

    @Query(value = "select u.id,u.username,d.accepted,d.role from User u,Developer d where u.id = d.user_id and d.project_id = ?1")
    List<?> findDeveloperInfoInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("update Developer d set d.accepted=1 where d.user_id=?1 and d.project_id=?2")
    int setDeveloperAcceptedTrue(Long user_id,Long project_id);

    @Query("select count(d.accepted) from Developer d where d.accepted=0 and d.user_id=?1")
    List<?> getProjectsNotificationNumber(Long user_id);

}
