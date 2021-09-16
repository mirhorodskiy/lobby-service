package tech.pm.lobbyservice.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Currency {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  @Column(name = "currency_id")
  private Long id;

  @Column(unique = true)
  private String title;

  @ManyToMany(mappedBy = "availableCurrenciesSet")
  @JsonIgnore
  private Set<GameDetails> gameDetailsSet;
}
