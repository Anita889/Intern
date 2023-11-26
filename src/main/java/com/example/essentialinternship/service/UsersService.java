package com.example.essentialinternship.service;

import com.example.essentialinternship.models.Users;

public interface UsersService {
    Users saveUser(Users user);

    Users findById(Integer id);

    Users findByEmail(String email);

    Users updateUser(Users user,boolean verifyMessage);

    Boolean deleteUser(Integer id);
}
