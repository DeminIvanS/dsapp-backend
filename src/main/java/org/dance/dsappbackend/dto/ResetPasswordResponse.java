package org.dance.dsappbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Результат сброса пароля")
public record ResetPasswordResponse(@Schema(description = "Новый сгенерированный пароль")
                                    String newPassword) {
}
