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
    void setStatusToArchived(Long id);

    @Transactional
    @Modifying
    @Query("delete from Task t where t.project_id=?1")
    void deleteAllTasksInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("delete from Story s where s.project_id=?1")
    void deleteAllStoriesInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("delete from Sprint s where s.project_id=?1")
    void deleteAllSprintsInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("delete from Epic e where e.project_id=?1")
    void deleteAllEpicsInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("update Task t set t.status=1 where t.project_id=?1")
    void archiveAllTasksInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("update Story s set s.status=1 where s.project_id=?1")
    void archiveAllStoriesInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("update Sprint s set s.status=0 where s.project_id=?1")
    void archiveAllSprintsInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("update Epic e set e.status=1 where e.project_id=?1")
    void archiveAllEpicsInProject(Long project_id);
}