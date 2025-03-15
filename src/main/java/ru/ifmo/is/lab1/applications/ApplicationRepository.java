package ru.ifmo.is.lab1.applications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.ifmo.is.lab1.adminrequests.AdminRequest;
import ru.ifmo.is.lab1.adminrequests.Status;
import ru.ifmo.is.lab1.common.framework.CrudRepository;
import ru.ifmo.is.lab1.users.User;

import java.util.Optional;


public interface ApplicationRepository extends CrudRepository<Application> {
  @Query("select count(d) from Application d where d.name = ?1")
  long countByName(String name);

  Page<Application> findAllByOrderByCreatedAtDesc(Pageable pageable);
  Page<Application> findAllByAuthorOrderByCreatedAtDesc(User author, Pageable pageable);
  Page<Application> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);
  Optional<Application> findByStatusAndAuthorOrderByCreatedAtDesc(Status status, User author);
}
