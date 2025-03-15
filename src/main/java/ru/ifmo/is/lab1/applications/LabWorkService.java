package ru.ifmo.is.lab1.applications;

import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.common.context.ApplicationLockBean;
import ru.ifmo.is.lab1.common.errors.ResourceAlreadyExists;
import ru.ifmo.is.lab1.common.framework.CrudService;
import ru.ifmo.is.lab1.common.search.SearchMapper;
import ru.ifmo.is.lab1.events.EventService;
import ru.ifmo.is.lab1.applications.dto.LabWorkCreateDto;
import ru.ifmo.is.lab1.applications.dto.LabWorkDto;
import ru.ifmo.is.lab1.applications.dto.LabWorkUpdateDto;
import ru.ifmo.is.lab1.applications.mappers.LabWorkMapper;
import ru.ifmo.is.lab1.users.UserService;

@Service
public class LabWorkService
  extends CrudService<
  Application,
  LabWorkRepository,
        LabWorkMapper,
  LabWorkPolicy,
  LabWorkDto,
  LabWorkCreateDto,
  LabWorkUpdateDto
  > {

  private final ApplicationLockBean applicationLock;
  public LabWorkService(
    LabWorkRepository repository,
    LabWorkMapper mapper,
    LabWorkPolicy policy,
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
  public void validateCreate(Application obj, LabWorkCreateDto dto) {
    if (this.repository.countByName(dto.getName()) != 0L) {
      throw new ResourceAlreadyExists(errorMessage(dto.getName()));
    }
  }

  @Override
  public void validateUpdate(Application obj, LabWorkUpdateDto dto) {
    if (dto.getName().isPresent()) {
      if (this.repository.countByName(dto.getName().get()) != 0L) {
        throw new ResourceAlreadyExists(errorMessage(dto.getName().get()));
      }
    }
  }

  private String errorMessage(String name) {
    return "LabWork with name '" + name + "' already exists";
  }
}
