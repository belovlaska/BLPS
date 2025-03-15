package ru.ifmo.is.lab1.batchoperations;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.networknt.schema.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationDto;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationUnitDto;
import ru.ifmo.is.lab1.common.errors.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/import", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Batch Operations")
@Slf4j
public class BatchOperationController {

  private static final String SCHEMA_VALIDATION_FILE = "/schemas/batch-operation.json";

  private final BatchOperationService service;
  private final ObjectMapper objectMapper;

  @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @Operation(summary = "Импортировать объекты", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<BatchOperationDto> create(@RequestParam("file") MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      throw new FileIsEmptyError("File not found");
    }
    var extension = FilenameUtils.getExtension(file.getOriginalFilename());
    if (extension == null || !Objects.equals(extension.toLowerCase(), "json")) {
      throw new BadFileExtensionError("File extension must be .json");
    }

    SchemaValidatorsConfig config = SchemaValidatorsConfig.builder()
      .pathType(PathType.LEGACY)
      .locale(Locale.ENGLISH)
      .errorMessageKeyword("errorMessage")
      .nullableKeywordEnabled(true)
      .typeLoose(false)
      .build();

    var jsonContent = file.getInputStream();
    var schema = jsonSchema(config);
    var jsonNode = objectMapper.readTree(jsonContent);
    Set<ValidationMessage> errors = schema.validate(jsonNode);

    if (!errors.isEmpty()) {
      var error = new ApiError(
        HttpStatus.UNPROCESSABLE_ENTITY,
        "Invalid JSON format",
        errors.stream().map(ValidationMessage::getMessage).collect(Collectors.toList())
      );
      throw new DetailedApiError(error);
    }

    var operations = objectMapper
      .configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)
      .readValue(file.getInputStream(), new TypeReference<List<BatchOperationUnitDto>>(){});

    var obj = service.create(operations);
    return ResponseEntity.status(HttpStatus.CREATED).body(obj);
  }

  @GetMapping
  @Operation(summary = "Получить все операции импорта", security = @SecurityRequirement(name = "bearerAuth"))
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  public ResponseEntity<Page<BatchOperationDto>> index(@PageableDefault(size = 20) Pageable pageable) {
    var batchOperations = service.getAll(pageable);
    return ResponseEntity.ok()
      .header("X-Total-Count", String.valueOf(batchOperations.getTotalElements()))
      .body(batchOperations);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @Operation(summary = "Получить операцию импорта по ID", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<BatchOperationDto> show(@PathVariable int id) {
    var batchOperations = service.getById(id);
    return ResponseEntity.ok(batchOperations);
  }

  private JsonSchema jsonSchema(SchemaValidatorsConfig config) {
    return JsonSchemaFactory
      .getInstance(SpecVersion.VersionFlag.V7)
      .getSchema(getClass().getResourceAsStream(SCHEMA_VALIDATION_FILE), config);
  }
}
