package org.dance.dsappbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Результат сброса пароля")
public record ResetPasswordResponse(
        @Schema(description = "Логин кому сбросили пароль")
        String username,
        @Schema(description = "Новый сгенерированный пароль")
        String newPassword) {
}
