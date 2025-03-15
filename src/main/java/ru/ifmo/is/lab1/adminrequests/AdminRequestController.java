package ru.ifmo.is.lab1.adminrequests;

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
import ru.ifmo.is.lab1.adminrequests.dto.AdminRequestDto;

@RestController
@RequestMapping(value = "/api/admin-requests", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Admin Request")
public class AdminRequestController {

  private final AdminRequestService service;

  @GetMapping
  @Operation(summary = "Получить все запросы на администрирование", security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<Page<AdminRequestDto>> index(@PageableDefault(size = 20) Pageable pageable) {
    var adminRequests = service.getAll(pageable);
    return ResponseEntity.ok()
      .header("X-Total-Count", String.valueOf(adminRequests.getTotalElements()))
      .body(adminRequests);
  }

  @GetMapping("/pending")
  @Operation(summary = "Получить все нерассмотренные запросы на администрирование", security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Page<AdminRequestDto>> indexPending(@PageableDefault(size = 20) Pageable pageable) {
    var adminRequests = service.getAllPending(pageable);
    return ResponseEntity.ok()
      .header("X-Total-Count", String.valueOf(adminRequests.getTotalElements()))
      .body(adminRequests);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @Operation(summary = "Получить запрос на администрирование по ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<AdminRequestDto> show(@PathVariable int id) {
    var adminRequest = service.getById(id);
    return ResponseEntity.ok(adminRequest);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('USER')")
  @Operation(summary = "Подать запрос на администрирование", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<AdminRequestDto> create() {
    var adminRequest = service.create();
    return ResponseEntity.status(HttpStatus.CREATED).body(adminRequest);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Обработать запрос на администрирование по ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<AdminRequestDto> process(@PathVariable int id, @RequestParam boolean approved) {
    var adminRequest = service.process(id, approved);
    return ResponseEntity.ok(adminRequest);
  }
}
