package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Epic;
import gr.uoa.di.jete.models.EpicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EpicRepository extends JpaRepository<Epic, EpicId>{

    @Query("select max(e.id) from Epic e")
    Optional<Long> findMaxId();

}