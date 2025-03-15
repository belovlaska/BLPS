package ru.ifmo.is.lab1.applications;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.applications.dto.ApplicationBatchDto;
import ru.ifmo.is.lab1.batchoperations.BatchOperation;
import ru.ifmo.is.lab1.batchoperations.contract.ImportService;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationUnitDto;
import ru.ifmo.is.lab1.batchoperations.dto.OperationType;
import ru.ifmo.is.lab1.disciplines.DisciplineImportService;
import ru.ifmo.is.lab1.applications.mappers.LabWorkBatchMapper;
import ru.ifmo.is.lab1.applications.mappers.LabWorkMapper;
import ru.ifmo.is.lab1.events.ResourceType;
import ru.ifmo.is.lab1.monetization.MonetizationImportService;
import ru.ifmo.is.lab1.people.PersonImportService;

@Service
@RequiredArgsConstructor
public class ApplicationImportService implements ImportService<Application> {


  private final MonetizationImportService monetizationImportService;
  private final LabWorkBatchMapper batchMapper;
  private final LabWorkMapper mapper;
  private final ApplicationService service;

  public Pair<BatchOperation, Application> handle(BatchOperation batchOperation, BatchOperationUnitDto dto) {
    switch (dto.getType()) {
      case CREATE -> {
        batchOperation.incAddedCount();
        var body = (ApplicationBatchDto) dto.getBody();


        var createDto = batchMapper.toCreate(body);
        var objDto = service.create(createDto);
        return new ImmutablePair<>(batchOperation, mapper.map(objDto));
      }
      case UPDATE -> {
        batchOperation.incUpdatedCount();
        var body = (ApplicationBatchDto) dto.getBody();

        // Monetization
        // TO DO
        if (body.getMonetization() != null) {
          var monetizationId = monetizationImportService.handle(batchOperation, new BatchOperationUnitDto(
            OperationType.CREATE, ResourceType.COORDINATES, null, body.getMonetization()
          )).getRight().getId();
          body.setCoordinatesId(monetizationId);
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
