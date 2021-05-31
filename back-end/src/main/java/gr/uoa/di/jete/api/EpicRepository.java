package gr.uoa.di.jete.api;

import org.springframework.data.jpa.repository.JpaRepository;

interface EpicRepository extends JpaRepository<Epic, EpicId>{

}