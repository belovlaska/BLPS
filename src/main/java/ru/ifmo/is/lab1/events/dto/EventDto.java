package ru.ifmo.is.lab1.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.ifmo.is.lab1.events.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class EventDto<T> {

  public EventType eventType;
  public ResourceType resourceType;
  public Integer resourceId;
  public String requestUUID;
  public T entity;

  public Map<String, Object> getPayload() {
    Map<String, Object> payload = new HashMap<>();
    payload.put("eventType", eventType);
    payload.put("resourceType", resourceType);
    payload.put("resourceId", resourceId);
    payload.put("requestUUID", requestUUID);
    if (eventType != EventType.DELETE) {
      payload.put("entity", entity);
    }

    return payload;
  }
}
