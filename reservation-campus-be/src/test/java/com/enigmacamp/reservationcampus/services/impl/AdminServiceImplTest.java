package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.Profile;
import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.model.entity.constant.Role;
import com.enigmacamp.reservationcampus.model.request.AuthRequestStudent;
import com.enigmacamp.reservationcampus.model.response.RegisterResponse;
import com.enigmacamp.reservationcampus.repository.UserRepository;
import com.enigmacamp.reservationcampus.services.ProfileService;
import com.enigmacamp.reservationcampus.services.constant.RoleService;
import com.enigmacamp.reservationcampus.utils.constant.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterStudent_Success() {
        AuthRequestStudent authRequestStudent = new AuthRequestStudent();
        authRequestStudent.setEmail("test@example.com");
        authRequestStudent.setPassword("password");
        authRequestStudent.setNim(123456);
        authRequestStudent.setFullName("John Doe");
        authRequestStudent.setBirthDate(new Date(2002-10-10));
        authRequestStudent.setPhone("1234567890");
        authRequestStudent.setPhoto("photo.jpg");

        Role role = Role.builder().id("1").name(ERole.ROLE_STUDENT).build();
        when(roleService.getOrSave(ERole.ROLE_STUDENT)).thenReturn(role);

        User user = User.builder().id("1").email("test@example.com").password("password").roles(role).build();
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userRepository.save(any())).thenReturn(user);

        Profile profile = Profile.builder()
                .id("1")
                .NIM(123456)
                .fullName("John Doe")
                .dateofbirth(new Date(2002-10-10))
                .phone("1234567890")
                .photo("photo.jpg")
                .user(user)
                .build();
        when(profileService.saveProfile(any())).thenReturn(profile);

        RegisterResponse response = adminService.registerStudent(authRequestStudent);

        assertEquals("test@example.com", response.getEmail());
        assertEquals("ROLE_STUDENT", response.getRole());
        verify(roleService, times(1)).getOrSave(ERole.ROLE_STUDENT);
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any());
        verify(profileService, times(1)).saveProfile(any());
    }

    @Test
    public void testRegisterStudent_DuplicateEmail() {
        AuthRequestStudent authRequestStudent = new AuthRequestStudent();
        authRequestStudent.setEmail("test@example.com");
        authRequestStudent.setPassword("password");

        when(roleService.getOrSave(ERole.ROLE_STUDENT)).thenReturn(Role.builder().id("1").name(ERole.ROLE_STUDENT).build());
        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(ResponseStatusException.class, () -> {
            adminService.registerStudent(authRequestStudent);
        });
        verify(roleService, times(1)).getOrSave(ERole.ROLE_STUDENT);
        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(profileService);
    }

    @Test
    public void testDeleteUser() {
        User user = User.builder().id("1").build();
        when(userRepository.findById("1")).thenReturn(java.util.Optional.of(user));

        adminService.deleteUser("1");

        verify(profileService, times(1)).deleteProfile("1");
        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteStudent() {
        User user = User.builder().id("1").build();
        when(userRepository.findById("1")).thenReturn(java.util.Optional.of(user));

        adminService.deleteStudent("1");

        verify(profileService, times(1)).deleteProfile("1");
        verify(userRepository, times(1)).deleteById("1");
    }

    // Add more test cases if needed
}
