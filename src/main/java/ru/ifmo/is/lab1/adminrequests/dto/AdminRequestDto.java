package ru.ifmo.is.lab1.adminrequests.dto;

import lombok.Data;
import ru.ifmo.is.lab1.adminrequests.Status;
import ru.ifmo.is.lab1.users.User;
import java.time.ZonedDateTime;

@Data
public class AdminRequestDto {
  private int id;
  private User user;
  private Status status;
  private User approvedBy;
  private ZonedDateTime approvalDate;
  private ZonedDateTime createdAt;
}
