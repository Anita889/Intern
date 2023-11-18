package com.example.essentialinternship.service.clas;


import com.example.essentialinternship.exeptions.RepositoryException;
import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Transactions;
import com.example.essentialinternship.models.Users;
import com.example.essentialinternship.repositories.AccountRepository;
import com.example.essentialinternship.repositories.UsersRepository;
import com.example.essentialinternship.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceClas implements AccountService {
    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public AccountServiceClas(AccountRepository accountRepository, UsersRepository usersRepository) {
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void saveAccount(Accounts account) {
        if (isSaveAbleAccount(account)) {
            account.setUsers(usersRepository.findByEmail(account.getEmail()));
            accountRepository.save(account);
        }        else
            throw new RepositoryException("Not valid account save");
    }

    private boolean isSaveAbleAccount(Accounts account){
        return account.getAccountId()!=null
                && account.getAccountType()!=null
                && account.getUsers()!=null
                && account.getBalance()!=null
                && account.getOpenDate()!=null
                && account.getEmail()!=null;
    }

    @Override
    public Accounts findById(Integer id) {
        if(accountRepository.findById(id).isPresent())
          return accountRepository.findById(id).get();
        else
             throw new RepositoryException("Not account find by this id");
    }

    @Override
    public Accounts findByUser(Users user) {
     if(accountRepository.findByUser(user)!=null)
        return accountRepository.findByUser(user);
     else
         throw new RepositoryException("Not account find by this user");
    }

    @Override
    public void updateAccount(Accounts account, Transactions transactions) {
      if(isSaveAbleAccount(account)){
          Accounts accountUpdate=accountRepository.findByUser(account.getUsers());
          accountUpdate.setAccountId(account.getAccountId());
          accountUpdate.setAccountType(account.getAccountType());
          accountUpdate.setUsers(account.getUsers());
          accountUpdate.setBalance(account.getBalance());
          List<Transactions> listTrans=account.getTransactions();
          listTrans.add(transactions);
          accountUpdate.setTransactions(listTrans);
          accountRepository.updateAccounts(accountUpdate,account.getAccountId());
      }
      else
          throw new RepositoryException("Not updateAble account");
    }
}
