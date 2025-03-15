package ru.ifmo.is.lab1.common.framework;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.ifmo.is.lab1.common.utils.datetime.ZonedDateTimeConverter;
import ru.ifmo.is.lab1.users.User;

import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "created_by", nullable = false)
  private User createdBy;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="created_at", nullable=false)
  @Convert(converter = ZonedDateTimeConverter.class)
  private ZonedDateTime createdAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "updated_by")
  private User updatedBy;

  @Column(name="updated_at")
  @Convert(converter = ZonedDateTimeConverter.class)
  private ZonedDateTime updatedAt;

  @PrePersist
  private void prePersist() {
    if (createdAt == null) {
      createdAt = ZonedDateTime.now();
    }
  }

  @PreUpdate
  private void preUpdate() {
    updatedAt = ZonedDateTime.now();
  }
}
