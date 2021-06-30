package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Assignee;
import gr.uoa.di.jete.models.AssigneeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssigneeRepository extends JpaRepository<Assignee, AssigneeId>{

}
