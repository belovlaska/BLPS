package ru.ifmo.is.lab1.batchoperations;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.ifmo.is.lab1.common.entity.BaseEntity;
import ru.ifmo.is.lab1.common.utils.datetime.ZonedDateTimeConverter;
import ru.ifmo.is.lab1.users.User;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "batch_operations")
public class BatchOperation implements BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_requests_id_seq")
  @SequenceGenerator(name = "admin_requests_id_seq", sequenceName = "admin_requests_id_seq", allocationSize = 1)
  @Column(name="id", nullable=false, unique=true)
  private int id;

  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  @ColumnTransformer(write="?::operation_status")
  @Column(name="status", nullable=false)
  private Status status;

  @NotNull
  @Column(name = "added_count", nullable = false)
  private Integer addedCount;

  @NotNull
  @Column(name = "updated_count", nullable = false)
  private Integer updatedCount;

  @NotNull
  @Column(name = "deleted_count", nullable = false)
  private Integer deletedCount;

  @Column(name = "error_message")
  private String errorMessage;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "created_by", nullable = false)
  private User createdBy;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="created_at", nullable=false)
  @Convert(converter = ZonedDateTimeConverter.class)
  private ZonedDateTime createdAt;

  public void incAddedCount() {
    addedCount++;
  }

  public void incUpdatedCount() {
    updatedCount++;
  }

  public void incDeletedCount() {
    deletedCount++;
  }
}
