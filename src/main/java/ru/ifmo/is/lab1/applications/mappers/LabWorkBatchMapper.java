package ru.ifmo.is.lab1.applications.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.ifmo.is.lab1.batchoperations.contract.BatchMapper;
import ru.ifmo.is.lab1.applications.dto.LabWorkBatchDto;
import ru.ifmo.is.lab1.common.mapper.JsonNullableMapper;
import ru.ifmo.is.lab1.common.mapper.ReferenceMapper;
import ru.ifmo.is.lab1.applications.dto.LabWorkCreateDto;
import ru.ifmo.is.lab1.applications.dto.LabWorkUpdateDto;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabWorkBatchMapper implements BatchMapper<LabWorkBatchDto, LabWorkCreateDto, LabWorkUpdateDto> {
  public abstract LabWorkCreateDto toCreate(LabWorkBatchDto dto);

  public abstract LabWorkUpdateDto toUpdate(LabWorkBatchDto model);
}
