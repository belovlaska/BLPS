package ru.ifmo.is.lab1.applications.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ifmo.is.lab1.common.framework.dto.AuditableDto;
import ru.ifmo.is.lab1.monetization.Monetization;
import ru.ifmo.is.lab1.disciplines.Discipline;
import ru.ifmo.is.lab1.people.Person;

@Data
@EqualsAndHashCode(callSuper = true)
public class LabWorkDto extends AuditableDto {
  private int id;
  private String name;
  private Monetization coordinates;
  private Discipline discipline;
  private Person author;
  private String description;
  private Cost difficulty;
  private Integer minimalPoint;

}
