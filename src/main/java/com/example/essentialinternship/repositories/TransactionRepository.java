package com.example.essentialinternship.repositories;

import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Integer> {
    Optional<Transactions> findById(Integer id);
    Transactions save(Transactions transaction);

}
