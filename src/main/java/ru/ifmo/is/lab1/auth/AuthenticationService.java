package ru.ifmo.is.lab1.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.ifmo.is.lab1.auth.dto.AuthenticationDto;
import ru.ifmo.is.lab1.auth.dto.SignInDto;
import ru.ifmo.is.lab1.auth.dto.SignUpDto;
import ru.ifmo.is.lab1.users.User;
import ru.ifmo.is.lab1.users.Role;
import ru.ifmo.is.lab1.users.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  /**
   * Регистрация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  @Transactional
  public AuthenticationDto signUp(SignUpDto request) {
    var user = User.builder()
      .username(request.getUsername())
      .password(passwordEncoder.encode(request.getPassword()))
      .role(Role.ROLE_USER)
      .build();

    userService.create(user);

    var jwt = jwtService.generateToken(user);
    return new AuthenticationDto(jwt, user);
  }

  /**
   * Аутентификация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  @Transactional
  public AuthenticationDto signIn(SignInDto request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
      request.getUsername(),
      request.getPassword()
    ));

    var user = userService
      .userDetailsService()
      .loadUserByUsername(request.getUsername());

    var jwt = jwtService.generateToken(user);
    return new AuthenticationDto(jwt, userService.getByUsername(user.getUsername()));
  }
}
