package ru.ifmo.is.lab1.applications;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.batchoperations.BatchOperation;
import ru.ifmo.is.lab1.batchoperations.contract.ImportService;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationUnitDto;
import ru.ifmo.is.lab1.batchoperations.dto.OperationType;
import ru.ifmo.is.lab1.coordinates.CoordinateImportService;
import ru.ifmo.is.lab1.disciplines.DisciplineImportService;
import ru.ifmo.is.lab1.applications.dto.LabWorkBatchDto;
import ru.ifmo.is.lab1.applications.mappers.LabWorkBatchMapper;
import ru.ifmo.is.lab1.applications.mappers.LabWorkMapper;
import ru.ifmo.is.lab1.events.ResourceType;
import ru.ifmo.is.lab1.people.PersonImportService;

@Service
@RequiredArgsConstructor
public class LabWorkImportService implements ImportService<Application> {

  private final CoordinateImportService coordinateImportService;

  private final LabWorkBatchMapper batchMapper;
  private final LabWorkMapper mapper;
  private final LabWorkService service;

  public Pair<BatchOperation, Application> handle(BatchOperation batchOperation, BatchOperationUnitDto dto) {
    switch (dto.getType()) {
      case CREATE -> {
        batchOperation.incAddedCount();
        var body = (LabWorkBatchDto) dto.getBody();

        // Coordinates
        if (body.getCoordinates() != null) {
          var coordinatesId = coordinateImportService.handle(batchOperation, new BatchOperationUnitDto(
            OperationType.CREATE, ResourceType.COORDINATES, null, body.getCoordinates()
          )).getRight().getId();
          body.setCoordinatesId(coordinatesId);
        }



        // Author
        if (body.getAuthor() != null) {
          var authorId = personImportService.handle(batchOperation, new BatchOperationUnitDto(
            OperationType.CREATE, ResourceType.PEOPLE, null, body.getAuthor()
          )).getRight().getId();
          body.setAuthorId(authorId);
        }

        // Discipline
        if (body.getDiscipline() != null) {
          var disciplineId = disciplineImportService.handle(batchOperation, new BatchOperationUnitDto(
            OperationType.CREATE, ResourceType.DISCIPLINES, null, body.getDiscipline()
          )).getRight().getId();
          body.setDisciplineId(disciplineId);
        }

        var createDto = batchMapper.toCreate(body);
        var objDto = service.create(createDto);
        return new ImmutablePair<>(batchOperation, mapper.map(objDto));
      }
      case UPDATE -> {
        batchOperation.incUpdatedCount();
        var body = (LabWorkBatchDto) dto.getBody();

        // Coordinates
        if (body.getCoordinates() != null) {
          var coordinatesId = coordinateImportService.handle(batchOperation, new BatchOperationUnitDto(
            OperationType.CREATE, ResourceType.COORDINATES, null, body.getCoordinates()
          )).getRight().getId();
          body.setCoordinatesId(coordinatesId);
        }


        // Author
        if (body.getAuthor() != null) {
          var authorId = personImportService.handle(batchOperation, new BatchOperationUnitDto(
            OperationType.CREATE, ResourceType.PEOPLE, null, body.getAuthor()
          )).getRight().getId();
          body.setAuthorId(authorId);
        }

        // Discipline
        if (body.getDiscipline() != null) {
          var disciplineId = disciplineImportService.handle(batchOperation, new BatchOperationUnitDto(
            OperationType.CREATE, ResourceType.DISCIPLINES, null, body.getDiscipline()
          )).getRight().getId();
          body.setDisciplineId(disciplineId);
        }

        var updateDto = batchMapper.toUpdate(body);
        var objDto = service.update(updateDto, dto.getResourceId());
        return new ImmutablePair<>(batchOperation, mapper.map(objDto));
      }
      case DELETE -> {
        batchOperation.incDeletedCount();
        service.delete(dto.getResourceId());
        return new ImmutablePair<>(batchOperation, null);
      }
    }
    return new ImmutablePair<>(batchOperation, null);
  }
}
