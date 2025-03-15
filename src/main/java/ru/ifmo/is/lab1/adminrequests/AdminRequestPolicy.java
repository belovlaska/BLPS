package ru.ifmo.is.lab1.adminrequests;

import org.springframework.stereotype.Component;
import ru.ifmo.is.lab1.common.policies.Policy;
import ru.ifmo.is.lab1.users.User;

@Component
public class AdminRequestPolicy extends Policy<AdminRequest> {

  @Override
  public boolean canShowAll(User user) {
    return true;
  }

  @Override
  public boolean canSearch(User user) {
    return false;
  }

  @Override
  public boolean canShow(User user, AdminRequest adminRequest) {
    return user.isAdmin() || user.equals(adminRequest.getUser());
  }

  @Override
  public boolean canCreate(User user) {
    return !user.isAdmin();
  }

  @Override
  public boolean canDelete(User user, AdminRequest adminRequest) {
    return false;
  }

  @Override
  public boolean canUpdate(User user, AdminRequest adminRequest) {
    return user.isAdmin();
  }

  @Override
  public String getPolicySubject() {
    return "admin requests";
  }
}
