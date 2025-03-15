package ru.ifmo.is.lab1.batchoperations.contract;

public interface BatchMapper<TBatchDto, TCreateDto, TUpdateDto> {
  TCreateDto toCreate(TBatchDto dto);
  TUpdateDto toUpdate(TBatchDto model);
}
