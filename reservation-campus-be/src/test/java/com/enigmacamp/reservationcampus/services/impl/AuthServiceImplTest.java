package com.enigmacamp.reservationcampus.services.impl;

import com.enigmacamp.reservationcampus.model.entity.AppUser;
import com.enigmacamp.reservationcampus.model.entity.constant.Role;
import com.enigmacamp.reservationcampus.model.request.AuthRequestLogin;
import com.enigmacamp.reservationcampus.model.response.LoginResponse;
import com.enigmacamp.reservationcampus.repository.UserRepository;
import com.enigmacamp.reservationcampus.security.JwtUtil;
import com.enigmacamp.reservationcampus.services.impl.AuthServiceImpl;
import com.enigmacamp.reservationcampus.utils.constant.ERole;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void testLogin_Success() {
        // Arrange
        AuthRequestLogin authRequestLogin = new AuthRequestLogin();
        authRequestLogin.setEmail("admin@example.com");
        authRequestLogin.setPassword("password");

        LoginResponse expectedResponse = LoginResponse.builder()
                .token("jwtToken")
                .role("ROLE_ADMIN")
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        AppUser appUser = AppUser.builder()
                .id("1")
                .email("admin@example.com")
                .password("password")
                .roles(Role.builder().id("1").name(ERole.ROLE_ADMIN).build().getName())
                .build();
        when(authentication.getPrincipal()).thenReturn(appUser);

        when(jwtUtil.generateToken(appUser)).thenReturn("jwtToken");

        // Act
        LoginResponse response = authService.login(authRequestLogin);

        // Assert
        assertEquals(expectedResponse.getToken(), response.getToken());
        assertEquals(expectedResponse.getRole(), response.getRole());

        // Verify
        verify(authenticationManager, times(1)).authenticate(any());
        verify(authentication, times(1)).getPrincipal();
        verify(jwtUtil, times(1)).generateToken(appUser);
        verifyNoMoreInteractions(userRepository, passwordEncoder);
    }

    @Test
    public void testLogin_AuthenticationException() {
        // Arrange
        AuthRequestLogin authRequestLogin = new AuthRequestLogin();
        authRequestLogin.setEmail("user@example.com");
        authRequestLogin.setPassword("password");

        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Invalid credentials"));

        // Act
        LoginResponse response = authService.login(authRequestLogin);

        // Assert
        assertNull(response);

        // Verify
        verify(authenticationManager, times(1)).authenticate(any());
        verifyNoInteractions(jwtUtil, userRepository, passwordEncoder);
    }
}
