package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.AppUser;
import com.enigmacamp.reservationcampus.model.entity.Profile;
import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.model.entity.constant.Role;
import com.enigmacamp.reservationcampus.model.request.AuthRequestAdmin;
import com.enigmacamp.reservationcampus.model.request.AuthRequestGeneral;
import com.enigmacamp.reservationcampus.model.request.AuthRequestLogin;
import com.enigmacamp.reservationcampus.model.response.LoginResponse;
import com.enigmacamp.reservationcampus.model.response.RegisterResponse;
import com.enigmacamp.reservationcampus.repository.UserRepository;
import com.enigmacamp.reservationcampus.security.JwtUtil;
import com.enigmacamp.reservationcampus.services.AuthService;
import com.enigmacamp.reservationcampus.services.ProfileService;
import com.enigmacamp.reservationcampus.services.constant.RoleService;
import com.enigmacamp.reservationcampus.utils.constant.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService  {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;


    @Override
    @Transactional
    public RegisterResponse registerAdmin(AuthRequestAdmin authRequestAdmin) {
        try {
            // TODO 1 : SET ROLE
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);

            // TODO 2 : SET CREDENTIALS / USERS
            User user = User.builder()
                    .email(authRequestAdmin.getEmail().toLowerCase())
                    .password(passwordEncoder.encode(authRequestAdmin.getPassword()))
                    .roles(role)
                    .build();
            userRepository.save(user);

            // TODO 3 : CREATE RESPONSE
            return RegisterResponse.builder()
                    .email(user.getEmail())
                    .role(role.getName())
                    .build();
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "EMAIL ALREADY EXITS");
        }
    }



    @Override
    public RegisterResponse registerGeneral(AuthRequestGeneral authRequestGeneral) {
        try{
            // TODO 1 : SET ROLE
            Role role = roleService.getOrSave(ERole.ROLE_GENERAL);

            // TODO 2 : SET CREDENTIALS / USERS
            User user = User.builder()
                   .email(authRequestGeneral.getEmail().toLowerCase())
                   .password(passwordEncoder.encode(authRequestGeneral.getPassword()))
                   .roles(role)
                   .build();
            userRepository.save(user);

            // TODO 3 : SET PROFILE
            Profile profile = Profile.builder()
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
    public LoginResponse login(AuthRequestLogin authRequestLogin) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequestLogin.getEmail().toLowerCase(),
                    authRequestLogin.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            AppUser appUser = (AppUser) authentication.getPrincipal();

            String token = jwtUtil.generateToken(appUser);
            return LoginResponse.builder()
                    .token(token)
                    .role(appUser.getRoles().name())
                    .build();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
