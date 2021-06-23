package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Project;
import gr.uoa.di.jete.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    @Query("select p from Project p,User u,Developer d where d.project_id = p.id and u.id = d.user_id and d.user_id=?1")
    List<Project> findAllByUserId(Long Id);

    @Query("select u from User u, Project p, Developer d where u.id = d.user_id and p.id = d.project_id and p.id = ?1 and u.username=?2")
    Optional<User> findUserByUsernameInProject(Long Id, String username);

    @Transactional
    @Modifying
    @Query("update Project p set p.status=1 where p.id=?1")
    int setStatusToArchived(Long id);
}