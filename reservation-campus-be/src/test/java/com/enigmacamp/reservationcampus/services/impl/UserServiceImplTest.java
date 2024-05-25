package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.repository.UserRepository;
import com.enigmacamp.reservationcampus.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    void testLoadUserById_Success() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        assertEquals("1", userService.loadUserById("1").getId());
        assertEquals("test@example.com", userService.loadUserById("1").getEmail());
        assertEquals("password123", userService.loadUserById("1").getPassword());
        verify(userRepository, times(3)).findById("1");
    }

    @Test
    void testLoadUserById_UserNotFound() {
        when(userRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserById("2"));
    }

    @Test
    void testLoadUserByUsername_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        assertEquals("1", userService.loadUserByUsername("test@example.com"));
        assertEquals("test@example.com", userService.loadUserByUsername("test@example.com").getUsername());
        assertEquals("password123", userService.loadUserByUsername("test@example.com").getPassword());
        verify(userRepository, times(3)).findByEmail("test@example.com");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test@example.com"));
    }

    @Test
    void testDeleteUser_Success() {
        userService.deleteUser("1");
        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteUserAccount_Success() {
        userService.deleteUserAccount("1");
        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void testGetAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        assertEquals(1, userService.getAllUsers().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUserDetails_Success() {
        // Assuming method implementation returns the updated user
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user, userService.updateUserDetails("1", "newEmail@example.com", "newPassword123"));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testEditUser_Success() {
        User updatedUser = new User();
        updatedUser.setEmail("updatedEmail@example.com");
        updatedUser.setPassword("updatedPassword123");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        assertEquals(user, userService.editUser("1", updatedUser));
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testEditUser_UserNotFound() {
        User updatedUser = new User();
        updatedUser.setEmail("updatedEmail@example.com");
        updatedUser.setPassword("updatedPassword123");

        when(userRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> userService.editUser("2", updatedUser));
        verify(userRepository, times(1)).findById("2");
        verify(userRepository, never()).save(any());
    }
}
