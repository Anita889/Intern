package com.example.essentialinternship.service;

import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Transactions;

import java.util.List;

//this must do to inform every step of update account

public interface TransactionService {
    Transactions createTransaction(String transactionType, Double amount, Accounts account);

    List<Transactions> getAllTransactions();


    Transactions getTransactionById(Integer transactionId);

    Transactions saveTransaction(Transactions transaction);
    void deleteTransaction(Integer transactionId);
}
