package ru.ifmo.is.lab1.specialoperations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.is.lab1.applications.ApplicationService;
import ru.ifmo.is.lab1.monetization.MonetizationService;
import ru.ifmo.is.lab1.monetization.dto.MonetizationCreateDto;
import ru.ifmo.is.lab1.monetization.dto.MonetizationDto;


@RestController
@RequestMapping(value = "/api/special-operations", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Special Operation")
public class SpecialOperationController {

  private final SpecialOperationService service;
  private final ApplicationService labWorkService;
  private final MonetizationService monetizationService;

//  @GetMapping("/filter-by-minimalPoint")
//  @Operation(summary = "Вернуть массив объектов, значение поля `minimalpoint` которых меньше заданного.")
//  public ResponseEntity<Page<LabWorkDto>> filterLabWorksByMinimalPoint(
//    @RequestParam("minimalPoint") Integer minimalPoint,
//    @PageableDefault(size = 20) Pageable pageable
//  ) {
//    var searchCriteria = SearchCriteria.builder().filterKey("minimalPoint").operation("LT").value(minimalPoint).build();
//    var searchDto = SearchDto.builder()
//      .dataOption("ALL")
//      .searchCriteriaList(Collections.singletonList(searchCriteria))
//      .build();
//
//    var labworks = labWorkService.findBySearchCriteria(searchDto, pageable);
//    return ResponseEntity.ok()
//      .header("X-Total-Count", String.valueOf(labworks.getTotalElements()))
//      .body(labworks);
//  }

//  @GetMapping("/filter-by-author")
//  @Operation(summary = "Вернуть массив объектов, значение поля `author` которых больше заданного.")
//  public ResponseEntity<Page<LabWorkDto>> filterLabWorksByAuthor(
//    @RequestParam("author_id") Integer author,
//    @PageableDefault(size = 20) Pageable pageable
//  ) {
//    var searchCriteria = SearchCriteria.builder().filterKey("author_id").operation("GT").value(author).build();
//    var searchDto = SearchDto.builder()
//      .dataOption("ALL")
//      .searchCriteriaList(Collections.singletonList(searchCriteria))
//      .build();
//
//    var labworks = labWorkService.findBySearchCriteria(searchDto, pageable);
//    return ResponseEntity.ok()
//      .header("X-Total-Count", String.valueOf(labworks.getTotalElements()))
//      .body(labworks);
//  }

//  @PostMapping("/add-labwork-to-discipline/{id}")
//  @Operation(summary = "Добавить лабораторную работу в программу дисциплины")
//  public ResponseEntity<LabWorkDto> addLabWorkToDiscipline(@PathVariable int id, @Valid @RequestBody LabWorkCreateDto dto) {
//    var labwork = service.addLabWorkToDiscipline(id, dto);
//    return ResponseEntity.ok(labwork);
//  }

//  @GetMapping("/get-uniq-authors")
//  @Operation(summary = "Вернуть массив уникальных значений поля 'author' по всем объектам")
//  public ResponseEntity<Page<PersonDto>> getUniqAuthors(@PageableDefault(size = 20) Pageable pageable) {
//
//    var persons = personService.getAll(pageable);
//    return ResponseEntity.ok()
//            .header("X-Total-Count", String.valueOf(persons.getTotalElements()))
//            .body(persons);
//  }



  @PostMapping("/add-monetization-to-application/{sum}")
  @Operation(summary = "Добавить монетизацию приложению")
  public ResponseEntity<ApplicationDto> addMonetizationToApplication(@PathVariable int id, @Valid @RequestBody ApplicationCreateDto dto) {
    var application = service.addMonetizationToApplication(id, dto);
    return ResponseEntity.ok(application);
  }
}
