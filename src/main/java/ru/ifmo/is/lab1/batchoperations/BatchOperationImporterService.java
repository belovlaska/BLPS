package ru.ifmo.is.lab1.batchoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationUnitDto;
import ru.ifmo.is.lab1.common.errors.ImportError;
import ru.ifmo.is.lab1.common.errors.PolicyViolationError;
import ru.ifmo.is.lab1.common.errors.ResourceAlreadyExists;

import ru.ifmo.is.lab1.disciplines.DisciplineImportService;
import ru.ifmo.is.lab1.applications.LabWorkImportService;
import ru.ifmo.is.lab1.locations.LocationImportService;
import ru.ifmo.is.lab1.people.PersonImportService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchOperationImporterService {

  private final BatchOperationRepository repository;

  private final CoordinateImportService coordinateImportService;
  private final DisciplineImportService disciplineImportService;
  private final LabWorkImportService labWorkImportService;
  private final LocationImportService locationImportService;
  private final PersonImportService personImportService;

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public BatchOperation doImport(BatchOperation batchOperation, List<BatchOperationUnitDto> operations)
    throws ImportError, PolicyViolationError, ResourceAlreadyExists {

    for (var operation : operations) {
      switch (operation.getResourceType()) {
        case COORDINATES  -> coordinateImportService.handle(batchOperation, operation);
        case DISCIPLINES -> disciplineImportService.handle(batchOperation, operation);
        case LABWORKS      -> labWorkImportService.handle(batchOperation, operation);
        case LOCATIONS    -> locationImportService.handle(batchOperation, operation);
        case PEOPLE       -> personImportService.handle(batchOperation, operation);
        default -> throw new ImportError("Unhandled resource type: " + operation.getResourceType());
      }
    }

    return repository.save(batchOperation);
  }
}
