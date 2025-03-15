package ru.ifmo.is.lab1.events;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "events")
public class Event implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_id_seq")
  @SequenceGenerator(name = "events_id_seq", sequenceName = "events_id_seq", allocationSize = 1)
  private int id;

  @NotNull
  @Column(name = "resource_id", nullable = false)
  private int resourceId;

  @Convert(converter = ResourceTypeConverter.class)
  @ColumnTransformer(write = "?::resource_type")
  @Column(name = "resource_type", nullable = false)
  private ResourceType resourceType;

  @Enumerated(EnumType.STRING)
  @ColumnTransformer(write = "?::event_type")
  @Column(name = "type", nullable = false)
  private EventType type;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "created_by")
  private User createdBy;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false)
  @Convert(converter = ZonedDateTimeConverter.class)
  private ZonedDateTime createdAt;

  @PrePersist
  private void prePersist() {
    if (createdAt == null) {
      createdAt = ZonedDateTime.now();
    }
  }
}
