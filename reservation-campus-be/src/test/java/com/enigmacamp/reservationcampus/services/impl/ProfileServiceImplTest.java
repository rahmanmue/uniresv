package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.Profile;
import com.enigmacamp.reservationcampus.model.request.AuthRequestStudent;
import com.enigmacamp.reservationcampus.model.entity.User;
import com.enigmacamp.reservationcampus.repository.ProfileRepository;
import com.enigmacamp.reservationcampus.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProfile() {
        Profile profile = new Profile();
        profile.setId("1");

        when(profileRepository.save(profile)).thenReturn(profile);

        Profile savedProfile = profileService.saveProfile(profile);

        assertEquals(profile.getId(), savedProfile.getId());
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    public void testGetProfileById() {
        Profile profile = new Profile();
        profile.setId("1");

        when(profileRepository.findById("1")).thenReturn(Optional.of(profile));

        Profile retrievedProfile = profileService.getProfileById("1");

        assertEquals(profile.getId(), retrievedProfile.getId());
        verify(profileRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateProfile() {
        Profile profile = new Profile();
        profile.setId("1");
        profile.setNIM(123456);
        profile.setFullName("John Doe");

        when(profileRepository.findById("1")).thenReturn(Optional.of(profile));
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile updatedProfile = profileService.updateProfile(profile);

        assertEquals(profile.getId(), updatedProfile.getId());
        assertEquals("123456", updatedProfile.getNIM());
        assertEquals("John Doe", updatedProfile.getFullName());
        verify(profileRepository, times(1)).findById("1");
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    public void testDeleteProfile() {
        Profile profile = new Profile();
        profile.setId("1");

        when(profileRepository.findById("1")).thenReturn(Optional.of(profile));

        Profile deletedProfile = profileService.deleteProfile("1");

        assertEquals(profile.getId(), deletedProfile.getId());
        verify(profileRepository, times(1)).findById("1");
        verify(profileRepository, times(1)).delete(profile);
    }

    @Test
    public void testUpdateProfileById() {
        Profile profile = new Profile();
        profile.setId("1");
        AuthRequestStudent authRequestStudent = new AuthRequestStudent();
        authRequestStudent.setNim(123456);
        authRequestStudent.setFullName("John Doe");

        User user = new User();
        user.setId("1");
        profile.setUser(user);

        when(profileRepository.findById("1")).thenReturn(Optional.of(profile));
        when(profileRepository.save(profile)).thenReturn(profile);
        when(userRepository.save(user)).thenReturn(user);

        Profile updatedProfile = profileService.updateProfileById("1", authRequestStudent);

        assertEquals(profile.getId(), updatedProfile.getId());
        assertEquals("123456", updatedProfile.getNIM());
        assertEquals("John Doe", updatedProfile.getFullName());
        verify(profileRepository, times(1)).findById("1");
        verify(profileRepository, times(1)).save(profile);
        verify(userRepository, times(1)).save(user);
    }
}
