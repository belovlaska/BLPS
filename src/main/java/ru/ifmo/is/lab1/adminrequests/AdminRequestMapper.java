package ru.ifmo.is.lab1.adminrequests;

import org.mapstruct.*;
import ru.ifmo.is.lab1.adminrequests.dto.AdminRequestDto;
import ru.ifmo.is.lab1.common.mapper.JsonNullableMapper;
import ru.ifmo.is.lab1.common.mapper.ReferenceMapper;

@Mapper(
  uses = { JsonNullableMapper.class, ReferenceMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  componentModel = MappingConstants.ComponentModel.SPRING,
  unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class AdminRequestMapper {
  public abstract AdminRequestDto map(AdminRequest model);
  public abstract AdminRequest map(AdminRequestDto model);
}
