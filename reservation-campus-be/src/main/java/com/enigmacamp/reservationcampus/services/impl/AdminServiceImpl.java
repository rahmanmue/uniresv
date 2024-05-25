package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.Profile;
import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.model.entity.constant.Role;
import com.enigmacamp.reservationcampus.model.request.AuthRequestStudent;
import com.enigmacamp.reservationcampus.model.response.RegisterResponse;
import com.enigmacamp.reservationcampus.repository.UserRepository;
import com.enigmacamp.reservationcampus.services.AdminService;
import com.enigmacamp.reservationcampus.services.ProfileService;
import com.enigmacamp.reservationcampus.services.constant.RoleService;
import com.enigmacamp.reservationcampus.utils.constant.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProfileService profileService;

    @Override
    public RegisterResponse registerStudent(AuthRequestStudent authRequestStudent) {
        try{
            // TODO 1 : SET ROLE
            Role role = roleService.getOrSave(ERole.ROLE_STUDENT);

            // TODO 2 : SET CREDENTIALS / USERS
            User user = User.builder()
                    .email(authRequestStudent.getEmail().toLowerCase())
                    .password(passwordEncoder.encode(authRequestStudent.getPassword()))
                    .roles(role)
                    .build();
            userRepository.save(user);

            // TODO 3 : SET PROFILE
            Profile profile = Profile.builder()
                    .NIM(authRequestStudent.getNim())
                    .fullName(authRequestStudent.getFullName())
                    .dateofbirth(authRequestStudent.getBirthDate())
                    .phone(authRequestStudent.getPhone())
                    .photo(authRequestStudent.getPhoto())
                    .user(user)
                    .build();
            profileService.saveProfile(profile);

            // TODO 4 : CREATE RESPONSE
            return RegisterResponse.builder()
                    .email(user.getEmail())
                    .role(role.getName())
                    .build();
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL ALREADY EXITS");
        }
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (user != null){
            profileService.deleteProfile(user.getId());
        }
        userRepository.deleteById(id);
    }

    @Override
    public void deleteStudent(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (user != null){
            profileService.deleteProfile(user.getId());
        }
        userRepository.deleteById(id);
    }


}
