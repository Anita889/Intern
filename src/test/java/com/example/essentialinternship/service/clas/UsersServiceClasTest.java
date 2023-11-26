package com.example.essentialinternship.service.clas;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.essentialinternship.exeptions.RepositoryException;
import com.example.essentialinternship.models.Accounts;
import com.example.essentialinternship.models.Users;
import com.example.essentialinternship.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
class UsersServiceClasTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceClas usersService;

    @Test
    void saveUser_ValidUser_SaveToRepository() {
        // Arrange
        Users validUser = createValidUser();
        when(usersRepository.findByEmail(validUser.getEmail())).thenReturn(null);
        when(usersRepository.findByPassword(validUser.getPassword())).thenReturn(null);

        // Act
        usersService.saveUser(validUser);

        // Assert
        verify(usersRepository, times(1)).save(validUser);
    }

    @Test
    void saveUser_InvalidUser_ThrowRepositoryException() {
        // Arrange
        Users invalidUser = createInvalidUser();

        // Act and Assert
        assertThrows(RepositoryException.class, () -> usersService.saveUser(invalidUser));
    }

    @Test
    void findById_UserExists_ReturnUser() {
        // Arrange
        Integer userId = 1;
        Users user = createValidUser();
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Users foundUser = usersService.findById(userId);

        // Assert
        assertEquals(user, foundUser);
    }

    @Test
    void findById_UserNotExists_ThrowRepositoryException() {
        // Arrange
        Integer userId = 1;
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RepositoryException.class, () -> usersService.findById(userId));
    }

    @Test
    void findByEmail_UserExists_ReturnUser() {
        // Arrange
        String userEmail = "test@example.com";
        Users user = createValidUser();
        when(usersRepository.findByEmail(userEmail)).thenReturn(user);

        // Act
        Users foundUser = usersService.findByEmail(userEmail);

        // Assert
        assertEquals(user, foundUser);
    }

    @Test
    void findByEmail_UserNotExists_ThrowRepositoryException() {
        // Arrange
        String userEmail = "nonexistent@example.com";
        when(usersRepository.findByEmail(userEmail)).thenReturn(null);

        // Act and Assert
        assertThrows(RepositoryException.class, () -> usersService.findByEmail(userEmail));
    }

    @Test
    void updateUser_ValidUserWithVerifyMessage_UpdateUserInRepository() {
        // Arrange
        Users existingUser = createValidUser();
        Users updatedUser = createValidUser(); // Update properties as needed
        Optional<Users> optionalUser = Optional.of(existingUser);
        when(usersRepository.findById(existingUser.getUserId())).thenReturn(optionalUser);

        // Act
        usersService.updateUser(updatedUser, true);

        // Assert
        verify(usersRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_InvalidUser_ThrowRepositoryException() {
        // Arrange
        Users invalidUser = createInvalidUser();

        // Act and Assert
        assertThrows(RepositoryException.class, () -> usersService.updateUser(invalidUser, true));
    }

    @Test
    void deleteUser_UserExists_DeleteUserFromRepository() {
        // Arrange
        Integer userId = 1;

        // Act
        usersService.deleteUser(userId);

        // Assert
        verify(usersRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_UserDoesNotExist_LogWarning() {
        // Arrange
        Integer userId = 1;
        doThrow(new RepositoryException("User not found")).when(usersRepository).deleteById(userId);

        // Act
        usersService.deleteUser(userId);

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
                .accounts(Arrays.asList(new Accounts(/* Set account properties */)))
                .build();
    }

    private Users createInvalidUser() {
        // Create and return an invalid user for testing
        return Users.builder().build(); // You might want to set some required properties to null or incorrect values
    }
}
