package com.example.essentialinternship.service;

import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Transactions;
import com.example.essentialinternship.models.Users;

public interface AccountService {
    void saveAccount(Accounts account);

    Accounts findById(Integer id);

    Accounts findByUser(Users user);

    void updateAccount(Accounts account, Transactions transactions);
}
