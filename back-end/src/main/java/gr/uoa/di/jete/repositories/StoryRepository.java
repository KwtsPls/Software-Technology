package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Story;
import gr.uoa.di.jete.models.StoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, StoryId>{

    @Query("select max(st.id) from Story st")
    Optional<Long> findMaxId();

    @Query("select st from Story st where st.project_id = ?1 and st.sprint_id=?2")
    List<Story> findAllByProjectAndSprintId(Long project_id,Long sprint_id);

    @Query("select st from Story st, Sprint s where st.project_id = ?1 and st.sprint_id=s.id and s.project_id = ?1 and s.status>0")
    List<Story> findAllByProjectAndActiveSprints(Long project_id);

    @Query("select st from Story st, Sprint s where st.project_id = ?1 and st.sprint_id=s.id and s.project_id = ?1 and s.status=0")
    List<Story> findAllByProjectAndArchivedSprints(Long project_id);

    @Query("select st from Story st, Sprint s where st.project_id = ?1 and st.sprint_id=s.id and s.project_id = ?1 and s.status=?2")
    List<Story> findAllByProjectAndSprintStatus(Long project_id,Long status);

    @Query("select st from Story st where st.project_id = ?1 and st.epic_id=?2")
    List<Story> findAllByProjectAndEpicId(Long project_id,Long epic_id);

    // ---------------------- Delete for story ---------------//
    @Transactional
    @Modifying
    @Query("delete from Task t where t.story_id=?1 and t.project_id=?2 and t.sprint_id=?3 and t.epic_id=?4")
    int deleteAllTasksInStory(Long story_id,Long project_id,Long sprint_id,Long epic_id);

    @Transactional
    @Modifying
    @Query("delete from Assignee a where a.story_id=?1 and a.project_id=?2 and a.sprint_id=?3 and a.epic_id=?4")
    int deleteAllAssigneesInStory(Long story_id,Long project_id,Long sprint_id,Long epic_id);
    //---------------------------------------------------------//

    // ---------------------- archive for story ---------------//
    @Transactional
    @Modifying
    @Query("update Story st set st.status=1 where st.id=?1 and st.project_id=?2 and st.sprint_id=?3 and st.epic_id=?4")
    int archiveStory(Long id,Long project_id,Long sprint_id,Long epic_id);

    @Transactional
    @Modifying
    @Query("update Task t set t.status=1 where t.story_id=?1 and t.project_id=?2 and t.sprint_id=?3 and t.epic_id=?4")
    int archiveAllTasksInStory(Long story_id,Long project_id,Long sprint_id,Long epic_id);
    //---------------------------------------------------------//
}