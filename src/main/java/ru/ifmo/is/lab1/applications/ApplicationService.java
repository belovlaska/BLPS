package ru.ifmo.is.lab1.applications;

import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.applications.dto.ApplicationCreateDto;
import ru.ifmo.is.lab1.applications.dto.ApplicationDto;
import ru.ifmo.is.lab1.applications.dto.ApplicationUpdateDto;
import ru.ifmo.is.lab1.applications.mappers.ApplicationMapper;
import ru.ifmo.is.lab1.common.context.ApplicationLockBean;
import ru.ifmo.is.lab1.common.errors.ResourceAlreadyExists;
import ru.ifmo.is.lab1.common.framework.CrudService;
import ru.ifmo.is.lab1.common.search.SearchMapper;
import ru.ifmo.is.lab1.events.EventService;
import ru.ifmo.is.lab1.users.UserService;

@Service
public class ApplicationService
  extends CrudService<
  Application,
  ApplicationRepository,
  ApplicationMapper,
        ApplicationPolicy,
  ApplicationDto,
  ApplicationCreateDto,
  ApplicationUpdateDto
  > {

  private final ApplicationLockBean applicationLock;
  public ApplicationService(
    ApplicationRepository repository,
    ApplicationMapper mapper,
    ApplicationPolicy policy,
    SearchMapper<Application> searchMapper,
    UserService userService,
    EventService<Application> eventService, ApplicationLockBean applicationLock
  ) {
    super(repository, mapper, policy, searchMapper, userService, eventService);
    this.applicationLock = applicationLock;
  }

  @Override
  protected void preAction() {
    super.preAction();
    applicationLock.getImportLock().lock();
  }

  @Override
  protected void postAction() {
    applicationLock.getImportLock().unlock();
    super.postAction();
  }

  @Override
  public void validateCreate(Application obj, ApplicationCreateDto dto) {
    if (this.repository.countByName(dto.getName()) != 0L) {
      throw new ResourceAlreadyExists(errorMessage(dto.getName()));
    }
  }

  @Override
  public void validateUpdate(Application obj, ApplicationUpdateDto dto) {
    if (dto.getName().isPresent()) {
      if (this.repository.countByName(dto.getName().get()) != 0L) {
        throw new ResourceAlreadyExists(errorMessage(dto.getName().get()));
      }
    }
  }

  private String errorMessage(String name) {
    return "Application with name '" + name + "' already exists";
  }
}
