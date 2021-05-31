package gr.uoa.di.jete.api;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{


    @Query("select u from User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);
}
