package ru.ifmo.is.lab1.monetization.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
public class MonetizationUpdateDto {
  @NotNull
  @Min(0)
  private JsonNullable<Float> sum;

}
