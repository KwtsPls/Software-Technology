package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Sprint;
import gr.uoa.di.jete.models.SprintId;
import gr.uoa.di.jete.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SprintRepository extends JpaRepository<Sprint, SprintId>{

    @Query("select max(s.id) from Sprint s")
    Optional<Long> findMaxId();

    @Query("select st from Story st,Sprint s where s.id=?1 and s.project_id=?2 and s.id=st.sprint_id and s.project_id = st.project_id")
    List<Story> findAllStories(Long id, Long project_id);

    @Query("select s from Sprint s,Project p where s.status=?2 and s.project_id = p.id and p.id = ?1")
    Optional<Sprint> findSprintByStatusInProject(Long project_id,Long status);

    @Transactional
    @Modifying
    @Query("update Sprint s set s.status = s.status - 1 where s.project_id=?1 and s.status > 0")
    void updateActiveSprintsInProject(Long project_id);

    @Transactional
    @Modifying
    @Query("update Story st set st.sprint_id = ?2 where st.sprint_id=?1 and st.status=0")
    void transferStories(Long old_id,Long new_id);

    @Transactional
    @Modifying
    @Query("update Task t set t.sprint_id = ?2 where t.sprint_id=?1 and t.status=0")
    void transferTasks(Long old_id,Long new_id);
}