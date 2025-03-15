package ru.ifmo.is.lab1.applications;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.ifmo.is.lab1.applications.dto.ApplicationDto;
import ru.ifmo.is.lab1.applications.mappers.ApplicationMapper;
import ru.ifmo.is.lab1.common.caching.RequestCache;
import ru.ifmo.is.lab1.common.errors.AdminRequestAlreadyProcessed;
import ru.ifmo.is.lab1.common.errors.ResourceNotFoundException;
import ru.ifmo.is.lab1.common.errors.SomePendingRequestsExists;
import ru.ifmo.is.lab1.events.EventService;
import ru.ifmo.is.lab1.events.EventType;
import ru.ifmo.is.lab1.users.User;
import ru.ifmo.is.lab1.users.UserService;

@Service
@RequiredArgsConstructor
public class ApplicationRequestService {

  private final ApplicationRepository repository;
  private final ApplicationMapper mapper;
  private final ApplicationPolicy policy;
  private final UserService userService;
  private final EventService<Application> eventService;

  public Page<ApplicationDto> getAll(Pageable pageable) {
    var user = currentUser();
    policy.showAll(user);

    if (user.isAdmin()) {
      return repository.findAllByOrderByCreatedAtDesc(pageable).map(mapper::map);
    }

    return repository.findAllByAuthorOrderByCreatedAtDesc(user, pageable).map(mapper::map);
  }

  public Page<ApplicationDto> getAllPending(Pageable pageable) {
    policy.showAll(currentUser());

    var applicationsRequests = repository.findAllByStatusOrderByCreatedAtDesc(Status.PENDING, pageable);
    return applicationsRequests.map(mapper::map);
  }

  public ApplicationDto getById(int id) {
    var applicationRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
    policy.show(currentUser(), applicationRequest);

    return mapper.map(applicationRequest);
  }

  @Transactional
  public ApplicationDto create() {
    var user = currentUser();
    policy.create(user);

    var previousRequests = repository.findByStatusAndAuthorOrderByCreatedAtDesc(Status.PENDING, user);
    if (previousRequests.isPresent()) {
      throw new SomePendingRequestsExists("У вас уже есть необработанная заявка.");
    }

    var applicationRequest = Application.builder()
      .author(user)
      .status(Status.PENDING)
      .build();

    repository.save(applicationRequest);
    eventService.notify(EventType.CREATE, applicationRequest);
    return mapper.map(applicationRequest);
  }

  @Transactional
  public ApplicationDto process(int id, boolean approved) {
    var currentUser = currentUser();
    var applicationRequest = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
    policy.update(currentUser, applicationRequest);

    if (!applicationRequest.getStatus().equals(Status.PENDING)) {
      throw new AdminRequestAlreadyProcessed("Запрос на публикацию уже был обработан.");
    }

    if (approved) {
      applicationRequest.setStatus(Status.APPROVED);
    } else {
      applicationRequest.setStatus(Status.REJECTED);
    }

    repository.save(applicationRequest);
    eventService.notify(EventType.UPDATE, applicationRequest);
    return mapper.map(applicationRequest);
  }

  @RequestCache
  private User currentUser() {
    return userService.getCurrentUser();
  }
}
