package ru.ifmo.is.lab1.coordinates;

import org.springframework.stereotype.Component;
import ru.ifmo.is.lab1.common.framework.CrudPolicy;

@Component
public class CoordinatePolicy extends CrudPolicy<Coordinate> {

  @Override
  public String getPolicySubject() {
    return "coordinates";
  }
}
