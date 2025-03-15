package ru.ifmo.is.lab1.batchoperations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationUnitDto;
import ru.ifmo.is.lab1.common.errors.ImportError;
import ru.ifmo.is.lab1.common.errors.PolicyViolationError;
import ru.ifmo.is.lab1.common.errors.ResourceAlreadyExists;

import ru.ifmo.is.lab1.applications.ApplicationImportService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchOperationImporterService {

  private final BatchOperationRepository repository;

  //private final MonetizationImportService monetizationImportService;
  private final ApplicationImportService applicationImportService;

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public BatchOperation doImport(BatchOperation batchOperation, List<BatchOperationUnitDto> operations)
    throws ImportError, PolicyViolationError, ResourceAlreadyExists {

    for (var operation : operations) {
      switch (operation.getResourceType()) {
        //case MONETIZATIONS  -> monetizationImportService.handle(batchOperation, operation);
        case APPLICATIONS      -> applicationImportService.handle(batchOperation, operation);
        default -> throw new ImportError("Unhandled resource type: " + operation.getResourceType());
      }
    }

    return repository.save(batchOperation);
  }
}
