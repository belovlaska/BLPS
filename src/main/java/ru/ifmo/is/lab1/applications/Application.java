package ru.ifmo.is.lab1.applications;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.ifmo.is.lab1.adminrequests.Status;
import ru.ifmo.is.lab1.common.framework.CrudEntity;

import ru.ifmo.is.lab1.users.User;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "applications")
public class Application extends CrudEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applications_id_seq")
  @SequenceGenerator(name = "applications_id_seq", sequenceName = "applications_id_seq", allocationSize = 1)
  private int id;

  @NotNull
  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @NotNull
  @Column(name = "monetization")
  private float monetization = 0;

  @NotBlank
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id")
  private User author;

  @NotNull
  @Column(name = "cost")
  private Integer cost;

  @NotNull
  @Column(name = "code")
  private String code;

  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  @ColumnTransformer(write="?::request_status")
  @Column(name="status", nullable=false)
  private Status status;
}
