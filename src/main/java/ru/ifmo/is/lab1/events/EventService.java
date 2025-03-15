package ru.ifmo.is.lab1.events;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.is.lab1.common.caching.RequestCache;
import ru.ifmo.is.lab1.common.entity.BaseEntity;
import ru.ifmo.is.lab1.common.entity.ResourceExtractor;
import ru.ifmo.is.lab1.common.ws.WebSocketHandler;
import ru.ifmo.is.lab1.events.dto.EventCreateDto;
import ru.ifmo.is.lab1.users.User;
import ru.ifmo.is.lab1.users.UserService;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class EventService<T extends BaseEntity> {

  private static final Logger logger = LoggerFactory.getLogger(EventService.class);

  private final EventRepository repository;
  private final WebSocketHandler<T> webSocketHandler;
  private final UserService userService;
  private final ResourceExtractor resourceExtractor;
  private final HttpServletRequest httpServletRequest;

  @Transactional
  public Event create(EventCreateDto eventDto, @Nullable User creator) {
    logger.info("Event({}): {}-{} by User#{}",
      eventDto.getType(),
      eventDto.getResourceType(),
      eventDto.getResourceId(),
      creator
    );

    var event = new Event();
    event.setResourceId(eventDto.getResourceId());
    event.setResourceType(eventDto.getResourceType());
    event.setType(eventDto.getType());
    event.setCreatedBy(creator);
    event.setCreatedAt(ZonedDateTime.now());
    return repository.save(event);
  }

  @Transactional
  public void notify(EventType eventType, T entity) {
    var entityMetaInfo = resourceExtractor.getIdentification(entity);
    var resourceType = ResourceType.valueOfResource(entityMetaInfo.getLeft());
    var resourceId = entityMetaInfo.getRight();

    var eventDto = new EventCreateDto(resourceId, resourceType, eventType);
    var event = create(eventDto, currentUser());
    webSocketHandler.notifyClients(event, entity, (String) httpServletRequest.getAttribute("requestUUID"));
  }

  @RequestCache
  private User currentUser() {
    try {
      return userService.getCurrentUser();
    } catch (UsernameNotFoundException _ex) {
      return null;
    }
  }
}
