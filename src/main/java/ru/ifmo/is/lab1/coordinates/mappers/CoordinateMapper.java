package ru.ifmo.is.lab1.coordinates.mappers;

import org.mapstruct.*;
import ru.ifmo.is.lab1.common.framework.CrudMapper;
import ru.ifmo.is.lab1.common.mapper.*;
import ru.ifmo.is.lab1.coordinates.Coordinate;
import ru.ifmo.is.lab1.coordinates.dto.*;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CoordinateMapper implements CrudMapper<Coordinate, CoordinateDto, CoordinateCreateDto, CoordinateUpdateDto> {
  public abstract Coordinate map(CoordinateCreateDto dto);

  public abstract CoordinateDto map(Coordinate model);

  public abstract Coordinate map(CoordinateDto model);

  public abstract void update(CoordinateUpdateDto dto, @MappingTarget Coordinate model);
}
