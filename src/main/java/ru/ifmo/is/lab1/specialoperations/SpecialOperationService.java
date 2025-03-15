package ru.ifmo.is.lab1.specialoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.applications.Application;
import ru.ifmo.is.lab1.applications.ApplicationRepository;
import ru.ifmo.is.lab1.applications.ApplicationService;
import ru.ifmo.is.lab1.applications.dto.ApplicationCreateDto;
import ru.ifmo.is.lab1.applications.dto.ApplicationDto;


@Service
@RequiredArgsConstructor
public class SpecialOperationService {

//  private final LabWorkRepository repository;
//  private final LabWorkService labWorkService;
//  private final LabWorkMapper mapper;

  private final ApplicationRepository repository;
  private final ApplicationService applicationService;
  private final ApplicationMapper mapper;

//  public LabWorkDto addLabWorkToDiscipline(int id, LabWorkCreateDto dto) {
//    dto.setDisciplineId(id);
//    return labWorkService.create(dto);
//  }

  public ApplicationDto addMonetizationToApplication(int id, ApplicationCreateDto dto) {
    dto.setMonetizationId(id);
    return applicationService.create(dto);
  }

}
