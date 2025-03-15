package ru.ifmo.is.lab1.batchoperations;

import org.springframework.stereotype.Component;
import ru.ifmo.is.lab1.common.policies.Policy;
import ru.ifmo.is.lab1.users.User;

@Component
public class BatchOperationPolicy extends Policy<BatchOperation> {

  @Override
  public boolean canShowAll(User user) {
    return true;
  }

  @Override
  public boolean canSearch(User user) {
    return false;
  }

  @Override
  public boolean canShow(User user, BatchOperation batchOperation) {
    return user.isAdmin() || user.equals(batchOperation.getCreatedBy());
  }

  @Override
  public boolean canCreate(User user) {
    return true;
  }

  @Override
  public boolean canDelete(User user, BatchOperation batchOperation) {
    return false;
  }

  @Override
  public boolean canUpdate(User user, BatchOperation batchOperation) {
    return false;
  }

  @Override
  public String getPolicySubject() {
    return "batch operations";
  }
}
