package org.dance.dsappbackend.config;


import org.dance.dsappbackend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация Spring Security.
 *
 * <p>Ключевые решения:
 * <ul>
 *   <li><b>Stateless сессии</b> — {@link SessionCreationPolicy#STATELESS}. Сервер не хранит
 *       никакого состояния сессии. Каждый запрос полностью идентифицируется по JWT.</li>
 *   <li><b>CSRF отключён</b> — для REST API с JWT в заголовке CSRF-атаки невозможны,
 *       поскольку браузер автоматически не добавляет заголовок Authorization.</li>
 *   <li><b>Открытые эндпоинты</b> — /api/auth/** (логин, регистрация, рефреш) и
 *       Swagger-UI доступны без токена.</li>
 *   <li><b>Всё остальное</b> — требует валидного access-токена.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    /**
     * Публичные пути, не требующие аутентификации.
     */
    private static final String[] PUBLIC_PATHS = {
            "/api/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsService userDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Отключаем CSRF — не нужен для stateless REST API с JWT
                .csrf(AbstractHttpConfigurer::disable)

                // Отключаем хранение сессий на сервере
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Настройка правил доступа
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_PATHS).permitAll()
                        .anyRequest().authenticated()
                )

                // Подключаем наш провайдер аутентификации (DaoAuthenticationProvider)
                .authenticationProvider(authenticationProvider())

                // Добавляем JWT-фильтр перед стандартным фильтром логина/пароля
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    /**
     * DaoAuthenticationProvider — стандартный провайдер, который загружает пользователя
     * через UserDetailsService и проверяет пароль через PasswordEncoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * BCrypt — рекомендованный алгоритм хэширования паролей.
     * Коэффициент сложности по умолчанию — 10.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager нужен сервису аутентификации для ручной проверки логин/пароль.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
