package ru.ifmo.is.lab1.monetization.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.ifmo.is.lab1.batchoperations.contract.BatchMapper;
import ru.ifmo.is.lab1.monetization.dto.MonetizationBatchDto;
import ru.ifmo.is.lab1.common.mapper.JsonNullableMapper;
import ru.ifmo.is.lab1.common.mapper.ReferenceMapper;
import ru.ifmo.is.lab1.monetization.dto.MonetizationCreateDto;
import ru.ifmo.is.lab1.monetization.dto.MonetizationUpdateDto;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class MonetizationBatchMapper implements BatchMapper<MonetizationBatchDto, MonetizationCreateDto, MonetizationUpdateDto> {
  public abstract MonetizationCreateDto toCreate(MonetizationBatchDto dto);
  public abstract MonetizationUpdateDto toUpdate(MonetizationBatchDto model);
}
