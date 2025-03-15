package ru.ifmo.is.lab1.applications.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.ifmo.is.lab1.applications.dto.ApplicationBatchDto;
import ru.ifmo.is.lab1.applications.dto.ApplicationUpdateDto;
import ru.ifmo.is.lab1.batchoperations.contract.BatchMapper;
import ru.ifmo.is.lab1.common.mapper.JsonNullableMapper;
import ru.ifmo.is.lab1.common.mapper.ReferenceMapper;
import ru.ifmo.is.lab1.applications.dto.ApplicationCreateDto;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabWorkBatchMapper implements BatchMapper<ApplicationBatchDto, ApplicationCreateDto, ApplicationUpdateDto> {
  public abstract ApplicationCreateDto toCreate(ApplicationBatchDto dto);

  public abstract ApplicationUpdateDto toUpdate(ApplicationBatchDto model);
}
