package ru.ifmo.is.lab1.applications;

import org.springframework.data.jpa.repository.Query;
import ru.ifmo.is.lab1.common.framework.CrudRepository;


public interface LabWorkRepository extends CrudRepository<Application> {
  @Query("select count(d) from Application d where d.name = ?1")
  long countByName(String name);
}
