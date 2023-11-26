package com.example.essentialinternship.repositories;

import com.example.essentialinternship.models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {
    Optional<Accounts> findById(Integer id);
    Accounts save(Accounts account);
    Accounts findByPassword(String password);
}
