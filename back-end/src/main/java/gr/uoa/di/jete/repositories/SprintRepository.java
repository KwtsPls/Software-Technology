package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Sprint;
import gr.uoa.di.jete.models.SprintId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SprintRepository extends JpaRepository<Sprint, SprintId>{

    @Query("select max(s.id) from Sprint s")
    Optional<Long> findMaxId();

}