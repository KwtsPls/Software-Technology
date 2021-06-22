package gr.uoa.di.jete.repositories;


import gr.uoa.di.jete.models.Developer;
import gr.uoa.di.jete.models.DeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, DeveloperId>{

    @Query(value = "select u.id,u.username,d.accepted,d.role from User u,Developer d where u.id = d.user_id and d.project_id = ?1",nativeQuery = true)
    List<?> findDeveloperInfoInProject(Long project_id);

}
