package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    @Query("select p from Payments p, User u where u.username = ?1 and u.id = p.user_id")
    Optional<List<Payments>> findAllByUsername(String username);

    @Query("select p from Payments p, User u where p.id = ?1 and u.id = ?2 and u.id = p.user_id")
    Optional<Payments> findById(Long id, Long userId);
}
