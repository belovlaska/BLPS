package ru.ifmo.is.lab1.common.framework;

import org.mapstruct.*;
import ru.ifmo.is.lab1.common.framework.dto.AuditableDto;

public interface CrudMapper<T extends CrudEntity, TDto extends AuditableDto, TCreateDto, TUpdateDto> {
  T map(TCreateDto dto);
  TDto map(T model);
  T map(TDto dto);
  void update(TUpdateDto dto, @MappingTarget T model);
}
