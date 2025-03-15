package ru.ifmo.is.lab1.applications.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ifmo.is.lab1.common.framework.dto.AuditableDto;
import ru.ifmo.is.lab1.monetization.Monetization;

import ru.ifmo.is.lab1.users.User;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationDto extends AuditableDto {
  private int id;
  private String name;
  private Monetization monetization;
  private User author;
  private String description;
  private Integer cost;

}
