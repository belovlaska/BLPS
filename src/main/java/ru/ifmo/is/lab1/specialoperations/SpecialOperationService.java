package ru.ifmo.is.lab1.specialoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.applications.mappers.LabWorkMapper;
import ru.ifmo.is.lab1.applications.LabWorkRepository;
import ru.ifmo.is.lab1.applications.LabWorkService;
import ru.ifmo.is.lab1.applications.dto.LabWorkCreateDto;
import ru.ifmo.is.lab1.applications.dto.LabWorkDto;


@Service
@RequiredArgsConstructor
public class SpecialOperationService {

  private final LabWorkRepository repository;
  private final LabWorkService labWorkService;
  private final LabWorkMapper mapper;

  public LabWorkDto addLabWorkToDiscipline(int id, LabWorkCreateDto dto) {
    dto.setDisciplineId(id);
    return labWorkService.create(dto);
  }

}
