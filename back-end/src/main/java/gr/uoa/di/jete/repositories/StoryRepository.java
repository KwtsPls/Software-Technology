package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Story;
import gr.uoa.di.jete.models.StoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, StoryId>{

}