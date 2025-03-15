package ru.ifmo.is.lab1.coordinates.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ifmo.is.lab1.common.framework.dto.AuditableDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class CoordinateDto extends AuditableDto {
  private int id;
  private float x;
  private Float y;
}
