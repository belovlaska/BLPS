package ru.ifmo.is.lab1.applications.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ifmo.is.lab1.common.framework.dto.AuditableDto;
import ru.ifmo.is.lab1.coordinates.Coordinate;
import ru.ifmo.is.lab1.disciplines.Discipline;
import ru.ifmo.is.lab1.applications.Cost;
import ru.ifmo.is.lab1.people.Person;

@Data
@EqualsAndHashCode(callSuper = true)
public class LabWorkDto extends AuditableDto {
  private int id;
  private String name;
  private Coordinate coordinates;
  private Discipline discipline;
  private Person author;
  private String description;
  private Cost difficulty;
  private Integer minimalPoint;

}
