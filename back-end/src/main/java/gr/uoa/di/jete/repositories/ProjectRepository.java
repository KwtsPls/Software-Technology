package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    @Query("select p from Project p,User u,Developer d where d.project_id = p.id and u.id = d.user_id and d.user_id=?1")
    List<Project> findAllByUserId(Long Id);
}