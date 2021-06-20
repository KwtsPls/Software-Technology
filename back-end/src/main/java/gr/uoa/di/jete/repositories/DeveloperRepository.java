package gr.uoa.di.jete.repositories;


import gr.uoa.di.jete.models.Developer;
import gr.uoa.di.jete.models.DeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, DeveloperId>{}
