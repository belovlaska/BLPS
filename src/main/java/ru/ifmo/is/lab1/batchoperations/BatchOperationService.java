package ru.ifmo.is.lab1.batchoperations;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationDto;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationUnitDto;
import ru.ifmo.is.lab1.common.caching.RequestCache;
import ru.ifmo.is.lab1.common.context.ApplicationLockBean;
import ru.ifmo.is.lab1.common.errors.ImportError;
import ru.ifmo.is.lab1.common.errors.ResourceAlreadyExists;
import ru.ifmo.is.lab1.common.errors.ResourceNotFoundException;
import ru.ifmo.is.lab1.events.EventService;
import ru.ifmo.is.lab1.events.EventType;
import ru.ifmo.is.lab1.users.User;
import ru.ifmo.is.lab1.users.UserService;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchOperationService {

  private static final Logger logger = LoggerFactory.getLogger(BatchOperationService.class);

  private final ApplicationLockBean applicationLock;

  private final BatchOperationImporterService importer;

  private final BatchOperationRepository repository;
  private final BatchOperationMapper mapper;
  private final BatchOperationPolicy policy;
  private final UserService userService;
  private final EventService<BatchOperation> eventService;

  public BatchOperationDto create(List<BatchOperationUnitDto> operations) {
    var user = currentUser();
    policy.create(user);

    // Create BatchOperation instance
    var batchOperation = BatchOperation.builder()
      .createdBy(user)
      .createdAt(ZonedDateTime.now())
      .status(Status.IN_PROGRESS)
      .addedCount(0)
      .updatedCount(0)
      .deletedCount(0)
      .build();

    batchOperation = repository.save(batchOperation);
    eventService.notify(EventType.CREATE, batchOperation);
    applicationLock.getImportLock().lock();

    try {
      // Import objects from `operations`, update `batchOperation` object
      logger.info("Start import: {}", operations);
      var result = importer.doImport(batchOperation, operations);

      // If successfully imported, update status
      result.setStatus(Status.SUCCESS);
      result = repository.save(result);

      eventService.notify(EventType.UPDATE, result);
      return mapper.map(result);

    } catch (ImportError | ConstraintViolationException | ResourceAlreadyExists | ResourceNotFoundException e) {
      // If error happened during transaction, update status to FAILED and set error message
      batchOperation.setErrorMessage(e.getMessage());
      logger.info("Batch operation #{} failed: {}", batchOperation.getId(), e.getMessage());
    } catch (Exception e) {
      batchOperation.setErrorMessage(e.getMessage());
      logger.info("Unhandled RuntimeException in Batch operation #{} failed: {}", batchOperation.getId(), e.getMessage());
    } finally {
      applicationLock.getImportLock().unlock();
    }

    batchOperation.setStatus(Status.FAILED);
    batchOperation.setAddedCount(0);
    batchOperation.setUpdatedCount(0);
    batchOperation.setDeletedCount(0);
    batchOperation = repository.save(batchOperation);

    eventService.notify(EventType.UPDATE, batchOperation);
    return mapper.map(batchOperation);
  }

  public Page<BatchOperationDto> getAll(Pageable pageable) {
    var user = currentUser();
    policy.showAll(user);

    if (user.isAdmin()) {
      return repository.findAllByOrderByCreatedAtDesc(pageable).map(mapper::map);
    }

    return repository.findAllByCreatedByOrderByCreatedAtDesc(user, pageable).map(mapper::map);
  }

  public BatchOperationDto getById(int id) {
    var batchOperation = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));
    policy.show(currentUser(), batchOperation);

    return mapper.map(batchOperation);
  }

  @RequestCache
  private User currentUser() {
    return userService.getCurrentUser();
  }
}
