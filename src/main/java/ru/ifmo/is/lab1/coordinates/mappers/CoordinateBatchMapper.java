package ru.ifmo.is.lab1.coordinates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.ifmo.is.lab1.batchoperations.contract.BatchMapper;
import ru.ifmo.is.lab1.coordinates.dto.CoordinateBatchDto;
import ru.ifmo.is.lab1.common.mapper.JsonNullableMapper;
import ru.ifmo.is.lab1.common.mapper.ReferenceMapper;
import ru.ifmo.is.lab1.coordinates.dto.CoordinateCreateDto;
import ru.ifmo.is.lab1.coordinates.dto.CoordinateUpdateDto;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CoordinateBatchMapper implements BatchMapper<CoordinateBatchDto, CoordinateCreateDto, CoordinateUpdateDto> {
  public abstract CoordinateCreateDto toCreate(CoordinateBatchDto dto);
  public abstract CoordinateUpdateDto toUpdate(CoordinateBatchDto model);
}
