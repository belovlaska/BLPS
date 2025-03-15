package ru.ifmo.is.lab1.coordinates.dto;

import ru.ifmo.is.lab1.common.framework.dto.BatchDto;
import ru.ifmo.is.lab1.events.ResourceType;

public class CoordinateBatchDto extends CoordinateUpdateDto implements BatchDto {
  @Override
  public ResourceType getResourceType() {
    return ResourceType.COORDINATES;
  }
}
