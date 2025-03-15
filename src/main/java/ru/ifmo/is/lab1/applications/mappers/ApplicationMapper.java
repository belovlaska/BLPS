package ru.ifmo.is.lab1.applications.mappers;

import org.mapstruct.*;
import ru.ifmo.is.lab1.applications.Application;
import ru.ifmo.is.lab1.applications.dto.ApplicationCreateDto;
import ru.ifmo.is.lab1.applications.dto.ApplicationDto;
import ru.ifmo.is.lab1.common.framework.CrudMapper;
import ru.ifmo.is.lab1.common.mapper.JsonNullableMapper;
import ru.ifmo.is.lab1.common.mapper.ReferenceMapper;
import ru.ifmo.is.lab1.applications.dto.ApplicationUpdateDto;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ApplicationMapper implements CrudMapper<Application, ApplicationDto, ApplicationCreateDto, ApplicationUpdateDto> {
  public abstract Application map(ApplicationCreateDto dto);

  public abstract ApplicationDto map(Application model);

  public abstract Application map(ApplicationDto model);

  public abstract void update(ApplicationUpdateDto dto, @MappingTarget Application model);
}
