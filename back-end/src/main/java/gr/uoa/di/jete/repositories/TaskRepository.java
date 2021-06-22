package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Task;
import gr.uoa.di.jete.models.TaskId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, TaskId>{

    @Query("select max(t.id) from Task t")
    Optional<Long> findMaxId();

}
