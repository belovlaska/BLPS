package ru.ifmo.is.lab1.specialoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.applications.mappers.LabWorkMapper;
import ru.ifmo.is.lab1.applications.LabWorkRepository;
import ru.ifmo.is.lab1.applications.LabWorkService;
import ru.ifmo.is.lab1.applications.dto.LabWorkCreateDto;
import ru.ifmo.is.lab1.applications.dto.LabWorkDto;
import ru.ifmo.is.lab1.monetization.MonetizationRepository;
import ru.ifmo.is.lab1.monetization.MonetizationService;
import ru.ifmo.is.lab1.monetization.dto.MonetizationCreateDto;
import ru.ifmo.is.lab1.monetization.dto.MonetizationDto;
import ru.ifmo.is.lab1.monetization.mappers.MonetizationMapper;


@Service
@RequiredArgsConstructor
public class SpecialOperationService {

//  private final LabWorkRepository repository;
//  private final LabWorkService labWorkService;
//  private final LabWorkMapper mapper;

  private final MonetizationRepository repository;
  private final MonetizationService monetizationService;
  private final MonetizationMapper mapper;

//  public LabWorkDto addLabWorkToDiscipline(int id, LabWorkCreateDto dto) {
//    dto.setDisciplineId(id);
//    return labWorkService.create(dto);
//  }

  public MonetizationDto addMonetizationToApplication(float sum, MonetizationCreateDto dto) {
    dto.setSum(sum);
    return monetizationService.create(dto);
  }

}
