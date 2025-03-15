package ru.ifmo.is.lab1.monetization;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.ifmo.is.lab1.common.framework.CrudEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "monetizations")
public class Monetization extends CrudEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monetizations_id_seq")
  @SequenceGenerator(name = "monetizations_id_seq", sequenceName = "monetizations_id_seq", allocationSize = 1)
  private int id;

  @Min(0)
  @Column(name = "sum")
  private float sum;

}
