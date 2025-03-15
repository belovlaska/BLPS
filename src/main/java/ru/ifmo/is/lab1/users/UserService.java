package ru.ifmo.is.lab1.users;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.is.lab1.common.caching.RequestCache;
import ru.ifmo.is.lab1.common.errors.UserWithThisPasswordAlreadyExists;
import ru.ifmo.is.lab1.common.errors.UserWithThisUsernameAlreadyExists;

@Service
@RequiredArgsConstructor
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository repository;

  /**
   * Сохранение пользователя
   *
   * @return сохраненный пользователь
   */
  @Transactional
  public User save(User user) {
    return repository.save(user);
  }

  /**
   * Создание пользователя
   *
   * @return созданный пользователь
   */
  @Transactional
  public User create(User user) {
    if (repository.existsByUsername(user.getUsername())) {
      throw new UserWithThisUsernameAlreadyExists("Пользователь с таким именем уже существует");
    }


    if (repository.count() == 0) {
      logger.info("Creating first user with ADMIN role");
      user.setRole(Role.ROLE_ADMIN);
    } else {
      user.setRole(Role.ROLE_USER);
    }
    return save(user);
  }

  /**
   * Получение пользователя по имени пользователя
   *
   * @return пользователь
   */
  @RequestCache
  public User getByUsername(String username) {
    return repository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
  }

  /**
   * Получение пользователя по имени пользователя
   * <p>
   * Нужен для Spring Security
   *
   * @return пользователь
   */
  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  /**
   * Получение текущего пользователя
   *
   * @return текущий пользователь
   */
  @RequestCache
  public User getCurrentUser() {
    // Получение имени пользователя из контекста Spring Security
    var username = getCurrentUsername();
    return getByUsername(username);
  }

  /**
   * Получение имени текущего пользователя
   *
   * @return имя текущего пользователь
   */
  @RequestCache
  public String getCurrentUsername() {
    // Получение имени пользователя из контекста Spring Security
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
