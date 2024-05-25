package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.AppUser;
import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.model.response.RegisterResponse;
import com.enigmacamp.reservationcampus.repository.UserRepository;
import com.enigmacamp.reservationcampus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AppUser loadUserById(String id) { //method untuk verif token JWT
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid credential User"));
        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().getName())
                .build();
    }

    @Override //method untuk memverifikasi USERNAME/EMAIL
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid credential User"));
        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().getName())
                .build();
    }

//    @Override
//    public User updateUser(String id, User user) {
//        return userRepository.save(user);
//    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUserAccount(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserDetails(String id, String newEmail, String newPassword) {
        return null;
    }

//    @Override
//    public User editUser(String id, User updatedUser) {
//        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        existingUser.setEmail(updatedUser.getEmail());
//        existingUser.setPassword((passwordEncoder.encode(updatedUser.getPassword())));
//        return userRepository.save(existingUser);
//    }

    @Override
    public User editUser(String id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return userRepository.save(existingUser);
    }



}
