package com.example.essentialinternship.service;

import com.example.essentialinternship.models.Users;

public interface UsersService {
    void saveUser(Users user);

    Users findById(Integer id);

    Users findByEmail(String email);

    void updateUser(Users user,boolean verifyMessage);
}
