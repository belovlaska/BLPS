package ru.ifmo.is.lab1.auth.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.ifmo.is.lab1.users.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class AuthenticationDto {
  @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
  private String token;
  private User user;
}
