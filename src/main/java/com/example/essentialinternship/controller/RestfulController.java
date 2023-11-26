package com.example.essentialinternship.controller;

import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Users;
import com.example.essentialinternship.service.AccountService;
import com.example.essentialinternship.service.TransactionService;
import com.example.essentialinternship.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulController {
    private final UsersService usersService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    public RestfulController(UsersService usersService, AccountService accountService, TransactionService transactionService) {
        this.usersService = usersService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }
    @PostMapping("/api/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user){
        return ResponseEntity.ok(usersService.saveUser(user));
    }

    @PostMapping("/api/register/{userId}")
    public ResponseEntity<Users> upDateUser( @RequestBody Users user){
        return ResponseEntity.ok(usersService.updateUser(user,true));
    }
    @DeleteMapping("/api/delete/{userId}")
    public ResponseEntity<Boolean> deleteUsers(@RequestBody Users user){
        return ResponseEntity.ok(usersService.deleteUser(user.getUserId()));
    }
    @PostMapping("/api/account")
    public ResponseEntity<Accounts> registerAccount(@RequestBody Accounts account){
        return ResponseEntity.ok(accountService.saveAccount(account));
    }
    @PostMapping("/api/account/{accountId}")
    public ResponseEntity<Accounts> upDateAccount(@RequestBody Accounts account){
        return ResponseEntity.ok(accountService.updateAccountPayment(account));
    }

    @DeleteMapping("/api/account/delete/{accountId}")
    public ResponseEntity<Boolean> deleteAccount(@RequestBody Accounts accounts){
        return ResponseEntity.ok(accountService.deleteAccount(accounts.getAccountId()));
    }
}
