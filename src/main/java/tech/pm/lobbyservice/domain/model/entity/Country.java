package tech.pm.lobbyservice.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  @Column(name = "country_id")
  private Long id;

  @Column(unique = true)
  private String name;

  @ManyToMany(mappedBy = "availableCountriesSet")
  @JsonIgnore
  private List<GameDetails> gameDetailsSet;
}
