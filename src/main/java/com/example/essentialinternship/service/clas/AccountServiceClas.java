package com.example.essentialinternship.service.clas;


import com.example.essentialinternship.exeptions.RepositoryException;
import com.example.essentialinternship.exeptions.TransactionalException;
import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Transactions;
import com.example.essentialinternship.models.Users;
import com.example.essentialinternship.repositories.AccountRepository;
import com.example.essentialinternship.repositories.UsersRepository;
import com.example.essentialinternship.service.AccountService;
import com.example.essentialinternship.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class AccountServiceClas implements AccountService {
    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;

    private final TransactionService transactionService;

    @Autowired
    public AccountServiceClas(AccountRepository accountRepository,
                              UsersRepository usersRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
        this.transactionService = transactionService;
    }

    @Override
    @Transactional
    public Accounts saveAccount(Accounts account) {
        if (isSaveAbleAccount(account)) {
            //find user of this new account
            Users user=usersRepository.findByEmail(account.getEmail());
            account.setUsers(user);
            //create new transaction of creation account
            Transactions transaction=transactionService.createTransaction("Create",
                    account.getBalance(),account);
            List<Transactions> list=new ArrayList<>();
            list.add(transaction);
            account.setTransactions(list);
            return accountRepository.save(account);
        }
        else
            throw new RepositoryException("Not valid account save");
    }
    private boolean isSaveAbleAccount(Accounts account){
        return
                accountRepository.findByPassword(account.getPassword())==null
                &&  account.getAccountType()!=null
                && account.getBalance()!=null
                && account.getEmail()!=null
                && account.getPassword()!=null;
    }
    private Accounts readyToUpdate(Accounts oldAccount,Accounts newAccount){
        if(oldAccount.getAccountType()!=newAccount.getAccountType() && newAccount.getAccountType()!=null)
            oldAccount.setAccountType(newAccount.getAccountType());
        if(!Objects.equals(oldAccount.getEmail(), newAccount.getEmail()) && newAccount.getEmail()!=null)
            oldAccount.setEmail(newAccount.getEmail());
        if(!Objects.equals(oldAccount.getPassword(), newAccount.getPassword()) && newAccount.getPassword()!=null){
            oldAccount.setPassword(newAccount.getPassword());
        }
            return oldAccount;
    }
    @Override
    public Accounts findById(Integer id) {
        if(accountRepository.findById(id).isPresent())
          return accountRepository.findById(id).get();
        else
             throw new RepositoryException("Not account find by this id");
    }


    @Override
    @Transactional
    public Accounts updateAccountFields(Accounts account) {
      if(accountRepository.findById(account.getAccountId()).isPresent()){
          Accounts accountUpdate=accountRepository.findById(account.getAccountId()).get();
          return accountRepository.save(readyToUpdate(accountUpdate,account));
      }
      else
          throw new RepositoryException("Not updateAble account");
    }

    @Override
    public Accounts updateAccountPayment(Accounts account) {
        // for payment
        // *new account balance is payment value,so it is payment
        Accounts repoAccount=accountRepository.findByPassword(account.getPassword());
        if(repoAccount.getBalance()>account.getBalance()) {
            Transactions transaction = transactionService.createTransaction("Payment",
                    account.getBalance(), repoAccount);
            repoAccount.setBalance(repoAccount.getBalance() - account.getBalance());
            List<Transactions> list = repoAccount.getTransactions();
            list.add(transaction);
            repoAccount.setTransactions(list);
            return accountRepository.save(repoAccount);
        }
        else
            throw new TransactionalException("Violation of payment");
    }

    @Override
    public Boolean deleteAccount(Integer id) {
        try {
            accountRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            throw new RepositoryException("User not delete");
        }
    }
}
