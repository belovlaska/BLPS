package ru.ifmo.is.lab1.monetization.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MonetizationCreateDto {
  @Min(0)
  private float sum;

}
