package org.dance.dsappbackend.service;


import org.dance.dsappbackend.dto.AuthResponse;
import org.dance.dsappbackend.dto.LoginRequest;
import org.dance.dsappbackend.dto.RefreshRequest;
import org.dance.dsappbackend.dto.RegisterRequest;
import org.dance.dsappbackend.entity.User;
import org.dance.dsappbackend.repository.UserRepository;
import org.dance.dsappbackend.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Регистрирует нового пользователя и сразу возвращает пару токенов.
     *
     * @throws IllegalArgumentException если username уже занят
     */
    public String createUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username '" + request.username() + "' is already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        if (request.role() != null) {
            user.setRole(request.role());
        } else {
            user.setRole(User.Role.ROLE_USER);
        }
        userRepository.save(user);

        return "User " + user.getUsername() + " successfully registered with role " + user.getRole();
    }

    /**
     * Аутентифицирует пользователя по логину и паролю.
     * Spring Security сам проверяет пароль через DaoAuthenticationProvider.
     *
     * @throws AuthenticationException если учётные данные неверны
     */
    public AuthResponse login(LoginRequest request) {
        // Вызов authenticationManager проверяет логин/пароль.
        // Если данные неверны — выбрасывается BadCredentialsException.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        return issueTokens(request.username());
    }

    /**
     * Выдаёт новый access-токен по валидному refresh-токену.
     *
     * <p>Важно: refresh-токен нигде не хранится на сервере — проверка
     * происходит только по подписи (stateless refresh).
     * Это значит, что "инвалидировать" конкретный refresh-токен нельзя.
     *
     * @throws IllegalArgumentException если refresh-токен невалиден или истёк
     */
    public AuthResponse refresh(RefreshRequest request) {
        if (!jwtTokenProvider.isRefreshTokenValid(request.refreshToken())) {
            throw new IllegalArgumentException("Refresh token is invalid or expired");
        }

        String username = jwtTokenProvider.extractUsername(request.refreshToken());
        return issueTokens(username);
    }

    private AuthResponse issueTokens(String username) {
        String accessToken = jwtTokenProvider.generateAccessToken(username);
        String refreshToken = jwtTokenProvider.generateRefreshToken(username);
        return new AuthResponse(accessToken, refreshToken);
    }
}
