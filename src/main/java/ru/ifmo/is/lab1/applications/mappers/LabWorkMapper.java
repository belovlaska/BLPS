package ru.ifmo.is.lab1.applications.mappers;

import org.mapstruct.*;
import ru.ifmo.is.lab1.applications.Application;
import ru.ifmo.is.lab1.common.framework.CrudMapper;
import ru.ifmo.is.lab1.common.mapper.JsonNullableMapper;
import ru.ifmo.is.lab1.common.mapper.ReferenceMapper;
import ru.ifmo.is.lab1.applications.dto.LabWorkCreateDto;
import ru.ifmo.is.lab1.applications.dto.LabWorkDto;
import ru.ifmo.is.lab1.applications.dto.LabWorkUpdateDto;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabWorkMapper implements CrudMapper<Application, LabWorkDto, LabWorkCreateDto, LabWorkUpdateDto> {
  @Mapping(source = "coordinatesId", target = "monetization")
  @Mapping(source = "disciplineId", target = "discipline")
  @Mapping(source = "authorId", target = "author")
  public abstract Application map(LabWorkCreateDto dto);

  public abstract LabWorkDto map(Application model);

  public abstract Application map(LabWorkDto model);

  public abstract void update(LabWorkUpdateDto dto, @MappingTarget Application model);
}
