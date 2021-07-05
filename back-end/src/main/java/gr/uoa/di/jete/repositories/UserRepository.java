package gr.uoa.di.jete.repositories;


import gr.uoa.di.jete.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Modifying
    @Query("update User u set u.password=?2 where u.id=?1")
    int changeUserPassword(Long id,String password_hash);

    @Transactional
    @Modifying
    @Query("update User u set u.is_enabled=1 where u.verification_code=?1 and u.username=?2")
    int setEnabledToTrue(String code,String username);
}
