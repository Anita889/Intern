package com.example.essentialinternship.repositories;

import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {
    Optional<Accounts> findById(Integer id);
    Accounts findByUser(Users user);
    Accounts save(Accounts account);

    void updateAccounts(Accounts accounts,Integer id);
}
