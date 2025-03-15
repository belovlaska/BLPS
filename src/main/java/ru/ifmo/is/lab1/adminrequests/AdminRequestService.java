package ru.ifmo.is.lab1.adminrequests;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.ifmo.is.lab1.adminrequests.dto.AdminRequestDto;
import ru.ifmo.is.lab1.common.caching.RequestCache;
import ru.ifmo.is.lab1.common.errors.AdminRequestAlreadyProcessed;
import ru.ifmo.is.lab1.common.errors.ResourceNotFoundException;
import ru.ifmo.is.lab1.common.errors.SomePendingRequestsExists;
import ru.ifmo.is.lab1.events.EventService;
import ru.ifmo.is.lab1.events.EventType;
import ru.ifmo.is.lab1.users.Role;
import ru.ifmo.is.lab1.users.User;
import ru.ifmo.is.lab1.users.UserRepository;
import ru.ifmo.is.lab1.users.UserService;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class AdminRequestService {

  private final AdminRequestRepository repository;
  private final AdminRequestMapper mapper;
  private final AdminRequestPolicy policy;
  private final UserRepository userRepository;
  private final UserService userService;
  private final EventService<AdminRequest> eventService;

  public Page<AdminRequestDto> getAll(Pageable pageable) {
    var user = currentUser();
    policy.showAll(user);

    if (user.isAdmin()) {
      return repository.findAllByOrderByCreatedAtDesc(pageable).map(mapper::map);
    }

    return repository.findAllByUserOrderByCreatedAtDesc(user, pageable).map(mapper::map);
  }

  public Page<AdminRequestDto> getAllPending(Pageable pageable) {
    policy.showAll(currentUser());

    var adminRequests = repository.findAllByStatusOrderByCreatedAtDesc(Status.PENDING, pageable);
    return adminRequests.map(mapper::map);
  }

  public AdminRequestDto getById(int id) {
    var adminRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
    policy.show(currentUser(), adminRequest);

    return mapper.map(adminRequest);
  }

  @Transactional
  public AdminRequestDto create() {
    var user = currentUser();
    policy.create(user);

    var previousRequests = repository.findByStatusAndUserOrderByCreatedAtDesc(Status.PENDING, user);
    if (previousRequests.isPresent()) {
      throw new SomePendingRequestsExists("У вас уже есть необработанная заявка.");
    }

    var adminRequest = AdminRequest.builder()
      .user(user)
      .status(Status.PENDING)
      .createdAt(ZonedDateTime.now())
      .build();

    repository.save(adminRequest);
    eventService.notify(EventType.CREATE, adminRequest);
    return mapper.map(adminRequest);
  }

  @Transactional
  public AdminRequestDto process(int id, boolean approved) {
    var currentUser = currentUser();
    var adminRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
    policy.update(currentUser, adminRequest);

    if (!adminRequest.getStatus().equals(Status.PENDING)) {
      throw new AdminRequestAlreadyProcessed("Запрос на администрирование уже был обработан.");
    }

    adminRequest.setApprovalDate(ZonedDateTime.now());
    adminRequest.setApprovedBy(currentUser);

    if (approved) {
      adminRequest.setStatus(Status.APPROVED);

      var user = adminRequest.getUser();
      user.setRole(Role.ROLE_ADMIN);
      userRepository.save(user);
    } else {
      adminRequest.setStatus(Status.REJECTED);
    }

    repository.save(adminRequest);
    eventService.notify(EventType.UPDATE, adminRequest);
    return mapper.map(adminRequest);
  }

  @RequestCache
  private User currentUser() {
    return userService.getCurrentUser();
  }
}
