package ru.ifmo.is.lab1.applications;

import org.springframework.stereotype.Component;
import ru.ifmo.is.lab1.common.framework.CrudPolicy;

@Component
public class LabWorkPolicy extends CrudPolicy<Application> {

  @Override
  public String getPolicySubject() {
    return "applications";
  }
}
