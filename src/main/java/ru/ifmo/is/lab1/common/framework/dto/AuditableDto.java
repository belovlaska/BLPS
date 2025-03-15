package ru.ifmo.is.lab1.common.framework.dto;

import lombok.Getter;
import lombok.Setter;
import ru.ifmo.is.lab1.users.User;

import java.time.ZonedDateTime;

@Getter
@Setter
public abstract class AuditableDto {
  private User createdBy;
  private ZonedDateTime createdAt;
  private User updatedBy;
  private ZonedDateTime updatedAt;
}
