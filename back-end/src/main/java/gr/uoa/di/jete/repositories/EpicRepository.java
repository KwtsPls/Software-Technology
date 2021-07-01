package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Epic;
import gr.uoa.di.jete.models.EpicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EpicRepository extends JpaRepository<Epic, EpicId>{

    @Query("select max(e.id) from Epic e")
    Optional<Long> findMaxId();

    @Query("select e from Epic e where e.project_id=?1")
    List<Epic> findAllByProjectId(Long project_id);

    // ---------------------- Delete for epic ---------------//
    @Transactional
    @Modifying
    @Query("delete from Story st where st.epic_id=?1 and st.project_id=?2")
    int deleteAllStoriesInEpic(Long id,Long project_id);

    @Transactional
    @Modifying
    @Query("delete from Task t where t.epic_id=?1 and t.project_id=?2")
    int deleteAllTasksInEpic(Long id,Long project_id);

    @Transactional
    @Modifying
    @Query("delete from Assignee a where a.epic_id=?1 and a.project_id=?2")
    int deleteAllAssigneesInEpic(Long id,Long project_id);
    //---------------------------------------------------------//

    //---------------- Archive for epic -----------------------//
    @Transactional
    @Modifying
    @Query("update Epic e set e.status=1 where e.id=?1 and e.project_id=?2")
    int archiveEpic(Long id,Long project_id);


    @Transactional
    @Modifying
    @Query("update Story st set st.status=1 where st.epic_id=?1 and st.project_id=?2")
    int archiveAllStoriesInEpic(Long id,Long project_id);

    @Transactional
    @Modifying
    @Query("update Task t set t.status=1 where t.epic_id=?1 and t.project_id=?2")
    int archiveAllTasksInEpic(Long id,Long project_id);
    //----------------------------------------------------------//

}