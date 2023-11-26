package com.example.essentialinternship.service;

import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Transactions;
import com.example.essentialinternship.models.Users;

public interface AccountService {
    Accounts saveAccount(Accounts account);

    Accounts findById(Integer id);

    Accounts updateAccountFields(Accounts account);

    Accounts updateAccountPayment(Accounts account);

    Boolean deleteAccount(Integer id);
}
