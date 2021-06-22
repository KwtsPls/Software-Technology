package gr.uoa.di.jete.repositories;

import gr.uoa.di.jete.models.Wallet;
//import gr.uoa.di.jete.models.WalletId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    @Query("select w from Wallet w, User u where u.username = ?1 and u.id = w.id")
    Optional<Wallet> findByUsername(String username);


}
