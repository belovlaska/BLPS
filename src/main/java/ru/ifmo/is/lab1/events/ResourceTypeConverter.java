package ru.ifmo.is.lab1.events;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ResourceTypeConverter implements AttributeConverter<ResourceType, String> {

  @Override
  public String convertToDatabaseColumn(ResourceType attribute) {
    return attribute == null ? null : attribute.resource;
  }

  @Override
  public ResourceType convertToEntityAttribute(String dbData) {
    return dbData == null ? null : ResourceType.valueOfResource(dbData);
  }
}
