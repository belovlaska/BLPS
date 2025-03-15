package ru.ifmo.is.lab1.applications.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.ifmo.is.lab1.common.framework.dto.BatchDto;
import ru.ifmo.is.lab1.monetization.dto.MonetizationBatchDto;
import ru.ifmo.is.lab1.events.ResourceType;

@Data
public class ApplicationBatchDto implements BatchDto {
  @NotNull
  @NotBlank
  private String name;

  private Integer coordinatesId;
  private MonetizationBatchDto monetization;

  @NotBlank
  private String description;

  @Min(0)
  private Integer cost;

  @Override
  public ResourceType getResourceType() {
    return ResourceType.APPLICATIONS;
  }
}
