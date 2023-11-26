package com.example.essentialinternship.repositories;

import com.example.essentialinternship.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findById(Integer id);
    Users save(Users user);
    Users findByEmail(String email);
    Users findByPassword(String password);
}
