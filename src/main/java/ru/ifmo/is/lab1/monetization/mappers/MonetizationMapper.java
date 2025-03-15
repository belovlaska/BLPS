package ru.ifmo.is.lab1.monetization.mappers;

import org.mapstruct.*;
import ru.ifmo.is.lab1.common.framework.CrudMapper;
import ru.ifmo.is.lab1.common.mapper.*;
import ru.ifmo.is.lab1.monetization.Monetization;
import ru.ifmo.is.lab1.monetization.dto.*;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class MonetizationMapper implements CrudMapper<Monetization, MonetizationDto, MonetizationCreateDto, MonetizationUpdateDto> {
  public abstract Monetization map(MonetizationCreateDto dto);

  public abstract MonetizationDto map(Monetization model);

  public abstract Monetization map(MonetizationDto model);

  public abstract void update(MonetizationUpdateDto dto, @MappingTarget Monetization model);
}
