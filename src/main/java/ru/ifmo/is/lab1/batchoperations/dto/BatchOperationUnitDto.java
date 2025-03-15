package ru.ifmo.is.lab1.batchoperations.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotNull;
import ru.ifmo.is.lab1.applications.dto.ApplicationBatchDto;
import ru.ifmo.is.lab1.common.framework.dto.BatchDto;
import ru.ifmo.is.lab1.events.ResourceType;


@Data
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
public class BatchOperationUnitDto {
  @NotNull
  private OperationType type;

  @NotNull
  private ResourceType resourceType;

  private Integer resourceId;

  @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "resourceType", include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
  @JsonSubTypes(value = {
    // @JsonSubTypes.Type(value = MonetizationBatchDto.class, name = "monetization"),
    @JsonSubTypes.Type(value = ApplicationBatchDto.class, name = "applications")

  })
  private BatchDto body;
}
