package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Sprint;
import gr.uoa.di.jete.models.SprintId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, SprintId>{

}