package ru.ifmo.is.lab1.applications;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "labworks_id_seq")
  @SequenceGenerator(name = "labworks_id_seq", sequenceName = "labworks_id_seq", allocationSize = 1)
  private int id;

  @NotNull
  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "monetization_id", nullable = false)
  private Monetization monetization;

  @NotBlank
  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author_id")
  private User author;

  @NotNull
  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  @ColumnTransformer(write="?::cost")
  @Column(name = "cost")
  private Cost cost;


}
