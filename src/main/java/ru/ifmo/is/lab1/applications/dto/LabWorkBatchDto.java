package ru.ifmo.is.lab1.applications.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.ifmo.is.lab1.common.framework.dto.BatchDto;
import ru.ifmo.is.lab1.disciplines.dto.DisciplineBatchDto;
import ru.ifmo.is.lab1.applications.Cost;
import ru.ifmo.is.lab1.people.dto.PersonBatchDto;
import ru.ifmo.is.lab1.coordinates.dto.CoordinateBatchDto;
import ru.ifmo.is.lab1.events.ResourceType;

@Data
public class LabWorkBatchDto implements BatchDto {
  @NotNull
  @NotBlank
  private String name;

  private Integer coordinatesId;
  private CoordinateBatchDto coordinates;


  private Integer authorId;
  private PersonBatchDto author;

  private Integer disciplineId;
  private DisciplineBatchDto discipline;

  @NotNull
  private Cost difficulty;

  @NotBlank
  private String description;

  @Min(0)
  private Integer minimalPoint;

  @Override
  public ResourceType getResourceType() {
    return ResourceType.LABWORKS;
  }
}
