package gr.uoa.di.jete.repositories;


import gr.uoa.di.jete.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query("select u from User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.username = ?1 and u.password = ?2")
    Optional<User> findByLogin(String username, String password);

    @Query("select u from User u,Project p,Developer d where d.user_id = u.id and d.project_id = p.id and d.project_id=?1")
    List<User> findAllByProjectId(Long Id);
}
