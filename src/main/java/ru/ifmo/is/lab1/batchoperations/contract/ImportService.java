package ru.ifmo.is.lab1.batchoperations.contract;

import org.apache.commons.lang3.tuple.Pair;
import ru.ifmo.is.lab1.batchoperations.BatchOperation;
import ru.ifmo.is.lab1.batchoperations.dto.BatchOperationUnitDto;

public interface ImportService<T> {
  Pair<BatchOperation, T> handle(BatchOperation batchOperation, BatchOperationUnitDto dto);
}
