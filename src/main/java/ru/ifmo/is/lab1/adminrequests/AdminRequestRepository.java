package ru.ifmo.is.lab1.adminrequests;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.ifmo.is.lab1.applications.Status;
import ru.ifmo.is.lab1.users.User;

import java.util.Optional;

public interface AdminRequestRepository
  extends JpaRepository<AdminRequest, Integer>,
          JpaSpecificationExecutor<AdminRequest> {

  Page<AdminRequest> findAllByOrderByCreatedAtDesc(Pageable pageable);
  Page<AdminRequest> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
  Page<AdminRequest> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);
  Optional<AdminRequest> findByStatusAndUserOrderByCreatedAtDesc(Status status, User user);
}
