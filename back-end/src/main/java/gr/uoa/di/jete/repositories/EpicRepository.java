package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Epic;
import gr.uoa.di.jete.models.EpicId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpicRepository extends JpaRepository<Epic, EpicId>{

}