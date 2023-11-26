package com.example.essentialinternship.service.clas;

import com.example.essentialinternship.exeptions.RepositoryException;
import com.example.essentialinternship.exeptions.TransactionalException;
import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Transactions;
import com.example.essentialinternship.repositories.TransactionRepository;
import com.example.essentialinternship.service.AccountService;
import com.example.essentialinternship.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceClas implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceClas(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public Transactions createTransaction(String transactionType, Double amount, Accounts account) {
        Transactions transaction = Transactions.builder()
                .transactionType(transactionType)
                .amount(amount)
                .account(account)
                .transactionDate(new Timestamp(System.currentTimeMillis()))
                .build();
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    @Transactional
    public List<Transactions> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public Transactions getTransactionById(Integer transactionId) {
        if(transactionRepository.findById(transactionId).isPresent())
        return  transactionRepository.findById(transactionId).get();
        else
            throw new RepositoryException("Nit found transaction");
    }

    @Override
    public Transactions saveTransaction(Transactions transaction) {
        if(transaction.getTransactionDate()!=null
        && transaction.getTransactionType()!=null
        && transaction.getAmount()!=null)
            return transactionRepository.save(transaction);
        else
            throw new RepositoryException("Not save able transaction");
    }

    @Override
    @Transactional
    public void deleteTransaction(Integer transactionId) {
       transactionRepository.deleteById(transactionId);
    }
}

