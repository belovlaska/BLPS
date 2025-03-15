package ru.ifmo.is.lab1.monetization;

import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.common.framework.CrudService;
import ru.ifmo.is.lab1.common.search.SearchMapper;
import ru.ifmo.is.lab1.monetization.dto.*;
import ru.ifmo.is.lab1.monetization.mappers.MonetizationMapper;
import ru.ifmo.is.lab1.events.EventService;
import ru.ifmo.is.lab1.users.UserService;

@Service
public class MonetizationService
  extends CrudService<
  Monetization,
  MonetizationRepository,
  MonetizationMapper,
  MonetizationPolicy,
  MonetizationDto,
  MonetizationCreateDto,
  MonetizationUpdateDto
  > {

  public MonetizationService(
    MonetizationRepository repository,
    MonetizationMapper mapper,
    MonetizationPolicy policy,
    SearchMapper<Monetization> searchMapper,
    UserService userService,
    EventService<Monetization> eventService
  ) {
    super(repository, mapper, policy, searchMapper, userService, eventService);
  }
}
