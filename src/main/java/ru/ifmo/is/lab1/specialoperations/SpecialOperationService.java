package ru.ifmo.is.lab1.specialoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.applications.Application;
import ru.ifmo.is.lab1.applications.ApplicationRepository;
import ru.ifmo.is.lab1.applications.ApplicationService;
import ru.ifmo.is.lab1.applications.dto.ApplicationCreateDto;
import ru.ifmo.is.lab1.applications.dto.ApplicationDto;
import ru.ifmo.is.lab1.applications.dto.ApplicationUpdateDto;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class SpecialOperationService {

//  private final LabWorkRepository repository;
//  private final LabWorkService labWorkService;
//  private final LabWorkMapper mapper;

  private final ApplicationRepository repository;
  private final ApplicationService applicationService;

//  public LabWorkDto addLabWorkToDiscipline(int id, LabWorkCreateDto dto) {
//    dto.setDisciplineId(id);
//    return labWorkService.create(dto);
//  }
//
//  @PreAuthorize("hasRole('')")
//  public ApplicationDto addMonetizationToApplication(int id, ApplicationDto dto) {
//    Random rand = new Random();
//    float monetization = rand.nextFloat(1,1000);
//    dto.setMonetization(monetization);
//    return applicationService.create(dto);
//  }

}
