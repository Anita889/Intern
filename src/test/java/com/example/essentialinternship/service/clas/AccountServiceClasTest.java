package com.example.essentialinternship.service.clas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.essentialinternship.exeptions.RepositoryException;
import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Role;
import com.example.essentialinternship.models.Transactions;
import com.example.essentialinternship.models.Users;
import com.example.essentialinternship.repositories.AccountRepository;
import com.example.essentialinternship.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
class AccountServiceClasTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private AccountServiceClas accountService;

    @Test
    void saveAccount_ValidAccount_SaveToRepository() {
        // Arrange
        Users user = createValidUser();
        Accounts validAccount = createValidAccount(user);
        when(usersRepository.findByEmail(validAccount.getEmail())).thenReturn(user);

        // Act
        accountService.saveAccount(validAccount);

        // Assert
        verify(accountRepository, times(1)).save(validAccount);
    }

    @Test
    void saveAccount_InvalidAccount_ThrowRepositoryException() {
        // Arrange
        Accounts invalidAccount = createInvalidAccount();

        // Act and Assert
        assertThrows(RepositoryException.class, () -> accountService.saveAccount(invalidAccount));
    }

    @Test
    void findById_AccountExists_ReturnAccount() {
        // Arrange
        Integer accountId = 1;
        Accounts account = createValidAccount(createValidUser());
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        Accounts foundAccount = accountService.findById(accountId);

        // Assert
        assertEquals(account, foundAccount);
    }

    @Test
    void findById_AccountNotExists_ThrowRepositoryException() {
        // Arrange
        Integer accountId = 1;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RepositoryException.class, () -> accountService.findById(accountId));
    }

    @Test
    void findByUser_AccountExists_ReturnAccount() {
        // Arrange
        Users user = createValidUser();
        Accounts account = createValidAccount(user);
        //  when(accountRepository.findByUsers(user)).thenReturn(account);

        // Act
        //Accounts foundAccount = accountService.findByUser(user);

        // Assert
    //    assertEquals(account, foundAccount);
    }

    @Test
    void findByUser_AccountNotExists_ThrowRepositoryException() {
        // Arrange
        Users user = createValidUser();
        //when(accountRepository.findByUsers(user)).thenReturn(null);

        // Act and Assert
        //assertThrows(RepositoryException.class, () -> accountService.findByUser(user));
    }

    @Test
    void updateAccount_ValidAccountWithTransactions_UpdateAccountInRepository() {
        // Arrange
        Users user = createValidUser();
        Accounts existingAccount = createValidAccount(user);
        Accounts updatedAccount = createValidAccount(user); // Update properties as needed
        Transactions transactions = createValidTransaction();
        existingAccount.setTransactions(Arrays.asList(transactions));

        Optional<Accounts> optionalAccount = Optional.of(existingAccount);
        when(accountRepository.save(existingAccount)).thenReturn(existingAccount);

        // Act
        accountService.updateAccount(updatedAccount);

        // Assert
        verify(accountRepository, times(1)).save(existingAccount);
    }

    @Test
    void updateAccount_InvalidAccount_ThrowRepositoryException() {
        // Arrange
        Accounts invalidAccount = createInvalidAccount();
        Transactions transactions = createValidTransaction();

        // Act and Assert
        assertThrows(RepositoryException.class, () -> accountService.updateAccount(invalidAccount));
    }

    @Test
    void deleteAccount_AccountExists_DeleteAccountFromRepository() {
        // Arrange
        Integer accountId = 1;

        // Act
        accountService.deleteAccount(accountId);

        // Assert
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void deleteAccount_AccountDoesNotExist_LogWarning() {
        // Arrange
        Integer accountId = 1;
        doThrow(new RepositoryException("Account not found")).when(accountRepository).deleteById(accountId);

        // Act
        accountService.deleteAccount(accountId);

        // Assert (verify log warning)
        // You might need a logging framework for this assertion
    }

    private Users createValidUser() {
        // Create and return a valid user for testing
        return Users.builder()
                .userId(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .dateOfBirth(LocalDateTime.now())
                .address("123 Main St")
                .contactNumber("123-456-7890")
                .accounts(Arrays.asList(createValidAccount(null)))
                .build();
    }

    private Accounts createValidAccount(Users user) {
        // Create and return a valid account for testing
        return Accounts.builder()
                .accountId(1)
                .accountType(Role.USER) // Set the appropriate role
                .email("account@example.com")
                .balance(100.0)
                .openDate(Timestamp.valueOf(LocalDateTime.now()))
                .users(user)
                .transactions(Arrays.asList(createValidTransaction()))
                .build();
    }

    private Accounts createInvalidAccount() {
        // Create and return an invalid account for testing
        return Accounts.builder()
                .accountId(null) // Set to null to make it invalid
                .accountType(null) // Set to null or an incorrect value to make it invalid
                .email("invalid-email") // Set an invalid email format
                .balance(-1.0) // Set an invalid balance
                .openDate(null) // Set to null to make it invalid
                .users(null) // Set to null to make it invalid
                .transactions(Arrays.asList(createValidTransaction())) // You might want to adjust this based on your requirements
                .build();
    }

    private Transactions createValidTransaction() {
        // Create and return a valid transaction for testing
        return Transactions.builder()
                .transactionId(1)
                .amount(50.0)
                .transactionType("DEPOSIT") // Set the appropriate transaction type
                .transactionDate(Timestamp.valueOf(LocalDateTime.now()))
                .account(createValidAccountWithoutTransaction())
                .build();
    }

    private Accounts createValidAccountWithoutTransaction() {
        // Create and return a valid account for testing without transactions
        return Accounts.builder()
                .accountId(1)
                .accountType(Role.USER) // Set the appropriate role
                .email("account@example.com")
                .balance(100.0)
                .openDate(Timestamp.valueOf(LocalDateTime.now()))
                .users(createValidUser())
                .build();
    }

}
