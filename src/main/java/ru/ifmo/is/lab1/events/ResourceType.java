package ru.ifmo.is.lab1.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public enum ResourceType {
  @JsonProperty("admin-requests")
  ADMIN_REQUESTS("admin-requests"),
  @JsonProperty("batch-operations")
  BATCH_OPERATIONS("batch-operations"),
  @JsonProperty("monetization")
  COORDINATES("monetization"),
  @JsonProperty("applications")
  LABWORKS("applications"),
  @JsonProperty("disciplines")
  DISCIPLINES("disciplines"),
  @JsonProperty("locations")
  LOCATIONS("locations"),
  @JsonProperty("people")
  PEOPLE("people");

  private static final Map<String, ResourceType> resources = new HashMap<>();

  static {
    for (ResourceType e: values()) {
      resources.put(e.resource, e);
    }
  }

  public final String resource;

  ResourceType(String resource) {
    this.resource = resource;
  }

  public static ResourceType valueOfResource(String label) {
    return resources.get(label);
  }
}
