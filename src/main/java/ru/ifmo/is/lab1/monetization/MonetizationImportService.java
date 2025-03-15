package ru.ifmo.is.lab1.monetization;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import ru.ifmo.is.lab1.batchoperations.BatchOperation;
import ru.ifmo.is.lab1.batchoperations.contract.ImportService;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationUnitDto;
import ru.ifmo.is.lab1.monetization.dto.MonetizationBatchDto;
import ru.ifmo.is.lab1.monetization.mappers.MonetizationBatchMapper;
import ru.ifmo.is.lab1.monetization.mappers.MonetizationMapper;

@Service
@RequiredArgsConstructor
public class MonetizationImportService implements ImportService<Monetization> {

  private final MonetizationBatchMapper batchMapper;
  private final MonetizationMapper mapper;
  private final MonetizationService service;

  public Pair<BatchOperation, Monetization> handle(BatchOperation batchOperation, BatchOperationUnitDto dto) {
    switch (dto.getType()) {
      case CREATE -> {
        batchOperation.incAddedCount();
        var body = (MonetizationBatchDto) dto.getBody();
        var createDto = batchMapper.toCreate(body);
        var objDto = service.create(createDto);
        return new ImmutablePair<>(batchOperation, mapper.map(objDto));
      }
      case UPDATE -> {
        batchOperation.incUpdatedCount();
        var body = (MonetizationBatchDto) dto.getBody();
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
