package gr.uoa.di.jete.api;


import org.springframework.data.jpa.repository.JpaRepository;

interface DeveloperRepository extends JpaRepository<Developer,DeveloperId>{}
