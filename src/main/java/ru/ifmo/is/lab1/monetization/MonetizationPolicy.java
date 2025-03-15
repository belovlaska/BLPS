package ru.ifmo.is.lab1.monetization;

import org.springframework.stereotype.Component;
import ru.ifmo.is.lab1.common.framework.CrudPolicy;

@Component
public class MonetizationPolicy extends CrudPolicy<Monetization> {

  @Override
  public String getPolicySubject() {
    return "monetization";
  }
}
