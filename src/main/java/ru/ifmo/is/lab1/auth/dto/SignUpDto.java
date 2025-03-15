package ru.ifmo.is.lab1.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpDto {
  @Schema(description = "Имя пользователя", example = "JohnyBoy")
  @Size(min = 3, max = 255, message = "Имя пользователя должно содержать от 3 до 255 символов")
  @NotBlank(message = "Имя пользователя не может быть пустыми")
  private String username;

  @Schema(description = "Пароль", example = "my_1secret1_password")
  @Size(min = 6, max = 128, message = "Длина пароля должна быть от 6 до 128")
  @NotBlank(message = "Пароль не может быть пустыми")
  private String password;
}
