package ru.ifmo.is.lab1.batchoperations.dto;

import lombok.Data;
import ru.ifmo.is.lab1.batchoperations.Status;
import ru.ifmo.is.lab1.users.User;
import java.time.ZonedDateTime;

@Data
public class BatchOperationDto {
  private int id;
  private Status status;
  private Integer addedCount;
  private Integer updatedCount;
  private Integer deletedCount;
  private String errorMessage;
  private User createdBy;
  private ZonedDateTime createdAt;
}
