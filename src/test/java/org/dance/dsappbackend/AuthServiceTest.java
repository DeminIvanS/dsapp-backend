package org.dance.dsappbackend;

import jakarta.inject.Inject;
import org.dance.dsappbackend.dto.AuthResponse;
import org.dance.dsappbackend.dto.LoginRequest;
import org.dance.dsappbackend.dto.RefreshRequest;
import org.dance.dsappbackend.dto.RegisterRequest;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.repository.UserRepository;
import org.dance.dsappbackend.security.JwtTokenProvider;
import org.dance.dsappbackend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private  JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthService authService;

    @Test
    void createUserTest(){

        RegisterRequest request = new RegisterRequest("vova","KID-123456", User.Role.ROLE_STUDENT);

        when(userRepository.existsByUsername("vova")).thenReturn(false);
        when(passwordEncoder.encode("KID-123456")).thenReturn("hashed_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = authService.createUser(request);

        assertNotNull(result);
        assertTrue(result.contains("vova"));
        assertTrue(result.contains("ROLE_STUDENT"));
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void createUserAlreadyExistsTest(){

        RegisterRequest request = new RegisterRequest("vova", "KID-123456", User.Role.ROLE_STUDENT);
        when(userRepository.existsByUsername("vova")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, ()->authService.createUser(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void loginSuccessTest(){
        LoginRequest request = new LoginRequest("vova", "KID-123456");

        User mockUser = new User();
        mockUser.setUsername("vova");
        org.springframework.test.util.ReflectionTestUtils.setField(mockUser,"shouldChangePassword", true);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByUsername("vova")).thenReturn(Optional.of(mockUser));

        when(jwtTokenProvider.generateAccessToken("vova", true)).thenReturn("access-token");
        when(jwtTokenProvider.generateRefreshToken("vova")).thenReturn("refresh-token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("access-token", response.accessToken());
        assertEquals("refresh-token", response.refreshToken());

        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken("vova", "KID-123456")
        );
    }

    @Test
    void loginFailedTest(){
        LoginRequest request = new LoginRequest("vova", "wrong-pass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        assertThrows(BadCredentialsException.class, ()-> authService.login(request));

        verifyNoInteractions(jwtTokenProvider);
        verifyNoInteractions(userRepository);
    }
    @Test
    void refreshSuccessTest(){

        RefreshRequest request = new RefreshRequest("valid-refresh-token");

        User mockUser = new User();
        mockUser.setUsername("vova");
        org.springframework.test.util.ReflectionTestUtils.setField(mockUser, "shouldChangePassword", false);

        when(jwtTokenProvider.isRefreshTokenValid("valid-refresh-token")).thenReturn(true);
        when(jwtTokenProvider.extractUsername("valid-refresh-token")).thenReturn("vova");
        when(userRepository.findByUsername("vova")).thenReturn(Optional.of(mockUser));

        when(jwtTokenProvider.generateAccessToken("vova", false)).thenReturn("new-access");
        when(jwtTokenProvider.generateRefreshToken("vova")).thenReturn("new-refresh");

        AuthResponse response = authService.refresh(request);

        assertNotNull(response);
        assertEquals("new-access", response.accessToken());
        assertEquals("new-refresh", response.refreshToken());
    }
    @Test
    void refreshInvalidTockenTest(){
        RefreshRequest request = new RefreshRequest("invalid-token");
        when(jwtTokenProvider.isRefreshTokenValid("invalid-token")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, ()->authService.refresh(request));
        verify(jwtTokenProvider, never()).extractUsername(anyString());
    }
}
