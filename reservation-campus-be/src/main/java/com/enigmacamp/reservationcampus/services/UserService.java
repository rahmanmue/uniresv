package com.enigmacamp.reservationcampus.services;

import com.enigmacamp.reservationcampus.model.entity.AppUser;
import com.enigmacamp.reservationcampus.model.entity.User;

import com.enigmacamp.reservationcampus.model.response.RegisterResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    AppUser loadUserById(String id);

//    User updateUser(String id, User user);

    void deleteUser(String id);

    void deleteUserAccount(String id);

    List<User> getAllUsers();

    User updateUserDetails(String id, String newEmail, String newPassword);

    User editUser(String id, User updatedUser);
}
