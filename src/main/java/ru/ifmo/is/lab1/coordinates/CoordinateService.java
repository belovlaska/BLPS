package ru.ifmo.is.lab1.coordinates;

import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.common.framework.CrudService;
import ru.ifmo.is.lab1.common.search.SearchMapper;
import ru.ifmo.is.lab1.coordinates.dto.*;
import ru.ifmo.is.lab1.coordinates.mappers.CoordinateMapper;
import ru.ifmo.is.lab1.events.EventService;
import ru.ifmo.is.lab1.users.UserService;

@Service
public class CoordinateService
  extends CrudService<
    Coordinate,
    CoordinateRepository,
    CoordinateMapper,
    CoordinatePolicy,
    CoordinateDto,
    CoordinateCreateDto,
    CoordinateUpdateDto
  > {

  public CoordinateService(
    CoordinateRepository repository,
    CoordinateMapper mapper,
    CoordinatePolicy policy,
    SearchMapper<Coordinate> searchMapper,
    UserService userService,
    EventService<Coordinate> eventService
  ) {
    super(repository, mapper, policy, searchMapper, userService, eventService);
  }
}
