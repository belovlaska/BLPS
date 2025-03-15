package ru.ifmo.is.lab1.coordinates;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.is.lab1.common.framework.CrudController;
import ru.ifmo.is.lab1.coordinates.dto.*;

@RestController
@RequestMapping(value = "/api/coordinates", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Coordinate")
public class CoordinateController
  extends CrudController<
    Coordinate,
    CoordinateDto,
    CoordinateCreateDto,
    CoordinateUpdateDto,
    CoordinateService
  > {

  public CoordinateController(
    CoordinateService service
  ) {
    super(service);
  }
}
