package ru.ifmo.is.lab1.applications;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.is.lab1.common.framework.CrudController;
import ru.ifmo.is.lab1.applications.dto.*;

@RestController
@RequestMapping(value = "/api/applications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Appllication")
public class ApplicationController
  extends CrudController<
  Application,
  ApplicationDto,
  ApplicationCreateDto,
  ApplicationUpdateDto,
  ApplicationService
  > {

  public ApplicationController(
    ApplicationService service
  ) {
    super(service);
  }
//  ApplicationService service;
//  @PatchMapping("/monetization")
//  @PreAuthorize("hasRole('USER')")
//  @Operation(summary = "Добавить монетизацию", security = @SecurityRequirement(name = "bearerAuth"))
//  public ResponseEntity<ApplicationDto> addMonetization(@PathVariable int id) {
//    var application = service.addMonetization(id);
//    return ResponseEntity.ok(application);
//  }
}
