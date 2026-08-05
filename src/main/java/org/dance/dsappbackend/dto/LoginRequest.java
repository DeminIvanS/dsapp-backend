package org.dance.dsappbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Учётные данные пользователя")
public record LoginRequest(

        @Schema(description = "Логин", example = "admin")
        String username,

        @Schema(description = "Пароль", example = "admin", format = "password")
        String password
) {
}
