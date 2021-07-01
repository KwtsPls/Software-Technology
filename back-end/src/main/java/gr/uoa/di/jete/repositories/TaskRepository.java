package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Task;
import gr.uoa.di.jete.models.TaskId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, TaskId>{

    @Query("select max(t.id) from Task t")
    Optional<Long> findMaxId();

    @Query("select t from Task t where t.project_id=?1 and t.story_id=?2")
    List<Task> findAllByProjectAndStoryId(Long project_id,Long story_id);

    @Query("select t from Task t where t.project_id=?1 and t.sprint_id=?2 and t.story_id=?3")
    List<Task> findAllByProjectAndSprintAndStoryId(Long project_id,Long sprint_id,Long story_id);

    @Query("select t from Task t where t.project_id=?1 and t.epic_id=?2 and t.story_id=?3")
    List<Task> findAllByProjectAndEpicAndStoryId(Long project_id,Long epic_id,Long story_id);

    @Transactional
    @Modifying
    @Query("delete from Assignee a where a.task_id=?1 and a.story_id=?2 and a.project_id=?3 and a.sprint_id=?4 and a.epic_id=?5")
    int deleteAllAssigneesInTask(Long task_id,Long story_id,Long project_id,Long sprint_id,Long epic_id);

    @Transactional
    @Modifying
    @Query("update Task t set t.status=1 where t.id=?1 and t.story_id=?2 and t.project_id=?3 and t.sprint_id=?4 and t.epic_id=?5")
    int archiveTask(Long task_id,Long story_id,Long project_id,Long sprint_id,Long epic_id);
}
