package ru.ifmo.is.lab1.applications;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.is.lab1.applications.dto.ApplicationDto;

@RestController
@RequestMapping(value = "/api/application-requests", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Application Request")
public class ApplicationRequestController {

  private final ApplicationRequestService service;

  @GetMapping
  @Operation(summary = "Получить все запросы на публикацию", security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<Page<ApplicationDto>> index(@PageableDefault(size = 20) Pageable pageable) {
    var adminRequests = service.getAll(pageable);
    return ResponseEntity.ok()
      .header("X-Total-Count", String.valueOf(adminRequests.getTotalElements()))
      .body(adminRequests);
  }

  @GetMapping("/pending")
  @Operation(summary = "Получить все нерассмотренные запросы на публикацию", security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<ApplicationDto>> indexPending(@PageableDefault(size = 20) Pageable pageable) {
    var applicationRequests = service.getAllPending(pageable);
    return ResponseEntity.ok()
      .header("X-Total-Count", String.valueOf(applicationRequests.getTotalElements()))
      .body(applicationRequests);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @Operation(summary = "Получить запрос на публикацию по ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<ApplicationDto> show(@PathVariable int id) {
    var applicationRequest = service.getById(id);
    return ResponseEntity.ok(applicationRequest);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('USER')")
  @Operation(summary = "Подать запрос на публикацию", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<ApplicationDto> create() {
    var applicationRequest = service.create();
    return ResponseEntity.status(HttpStatus.CREATED).body(applicationRequest);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Обработать запрос на публикацию по ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<ApplicationDto> process(@PathVariable int id, @RequestParam boolean approved) {
    var applicationRequest = service.process(id, approved);
    return ResponseEntity.ok(applicationRequest);
  }
}
