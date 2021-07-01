package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Story;
import gr.uoa.di.jete.models.StoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, StoryId>{

    @Query("select st from Story st where st.project_id = ?1 and st.sprint_id=?2")
    List<Story> findAllByProjectAndSprintId(Long project_id,Long sprint_id);

    @Query("select st from Story st where st.project_id = ?1 and st.epic_id=?2")
    List<Story> findAllByProjectAndEpicId(Long project_id,Long epic_id);
}