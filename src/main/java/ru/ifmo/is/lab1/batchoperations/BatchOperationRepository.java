package ru.ifmo.is.lab1.batchoperations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.ifmo.is.lab1.adminrequests.Status;
import ru.ifmo.is.lab1.users.User;

import java.util.Optional;

public interface BatchOperationRepository
  extends JpaRepository<BatchOperation, Integer>,
  JpaSpecificationExecutor<BatchOperation> {

  Page<BatchOperation> findAllByOrderByCreatedAtDesc(Pageable pageable);
  Page<BatchOperation> findAllByCreatedByOrderByCreatedAtDesc(User user, Pageable pageable);
  Page<BatchOperation> findAllByStatusOrderByCreatedAtDesc(Status status, Pageable pageable);
  Optional<BatchOperation> findByStatusAndCreatedByOrderByCreatedAtDesc(Status status, User user);
}
