package ru.ifmo.is.lab1.monetization.dto;

import ru.ifmo.is.lab1.common.framework.dto.BatchDto;
import ru.ifmo.is.lab1.events.ResourceType;

public class MonetizationBatchDto extends MonetizationUpdateDto implements BatchDto {
  @Override
  public ResourceType getResourceType() {
    return ResourceType.COORDINATES;
  }
}
