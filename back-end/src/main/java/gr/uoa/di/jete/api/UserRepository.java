package gr.uoa.di.jete.api;


import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long>{}
