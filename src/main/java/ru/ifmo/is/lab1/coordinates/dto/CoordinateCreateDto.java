package ru.ifmo.is.lab1.coordinates.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CoordinateCreateDto {
  @Max(903)
  private float x;

  @NotNull
  @Min(-901)
  private Float y;
}
