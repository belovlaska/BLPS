package ru.ifmo.is.lab1.monetization;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.is.lab1.common.framework.CrudController;
import ru.ifmo.is.lab1.monetization.dto.*;

@RestController
@RequestMapping(value = "/api/coordinates", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Coordinate")
public class MonetizationController
  extends CrudController<
  Monetization,
  MonetizationDto,
  MonetizationCreateDto,
  MonetizationUpdateDto,
  MonetizationService
  > {

  public MonetizationController(
    MonetizationService service
  ) {
    super(service);
  }
}
