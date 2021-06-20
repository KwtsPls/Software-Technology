package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}