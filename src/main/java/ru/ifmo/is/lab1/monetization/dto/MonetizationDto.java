package ru.ifmo.is.lab1.monetization.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.ifmo.is.lab1.common.framework.dto.AuditableDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class MonetizationDto extends AuditableDto {
  private int id;
  private float sum;
}
