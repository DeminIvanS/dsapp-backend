package org.dance.dsappbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dance.dsappbackend.dto.AuthResponse;
import org.dance.dsappbackend.dto.LoginRequest;
import org.dance.dsappbackend.dto.RefreshRequest;
import org.dance.dsappbackend.dto.RegisterRequest;
import org.dance.dsappbackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Auth", description = "Логин и обновление токенов")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createUser(@RequestBody RegisterRequest request) {
        String message  = authService.createUser(request);
        return ResponseEntity.ok(message);
    }
    @Operation(
            summary = "Вход по логину и паролю",
            description = "Возвращает пару access/refresh токенов",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Токены выданы"),
            @ApiResponse(responseCode = "401", description = "Неверный логин или пароль",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @Operation(
            summary = "Обновление пары токенов",
            description = "Принимает refresh-токен в JSON-теле запроса и выдает новую пару токенов без проверки заголовка авторизации.",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Новые токены успешно сгенерированы"),
            @ApiResponse(responseCode = "401", description = "Невалидный или просроченный refresh-токен")
    })
    @PostMapping("/refresh")

    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        return authService.refresh(request);
    }
}
