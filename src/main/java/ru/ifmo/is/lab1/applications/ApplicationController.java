package ru.ifmo.is.lab1.applications;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.is.lab1.common.framework.CrudController;
import ru.ifmo.is.lab1.applications.dto.*;

@RestController
@RequestMapping(value = "/api/applications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Appllication")
public class ApplicationController
  extends CrudController<
  Application,
    LabWorkDto,
    LabWorkCreateDto,
    LabWorkUpdateDto,
  LabWorkService
  > {

  public ApplicationController(
    LabWorkService service
  ) {
    super(service);
  }
}
