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
@Table(name = "game_details")
public class GameDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  @Column(name = "game_details_id")
  private Long id;

  @Column(name = "provider_id")
  private String providerName;

  @Column(name = "game_id")
  private String gameName;


  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "game_country", joinColumns = @JoinColumn(name = "game_lobby_id"),
          inverseJoinColumns = @JoinColumn(name = "country_id"))
  @JsonIgnore
  private Set<Country> availableCountriesSet;


  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "game_currency", joinColumns = @JoinColumn(name = "game_lobby_id"),
          inverseJoinColumns = @JoinColumn(name = "currency_id"))
  @JsonIgnore
  private Set<Currency> availableCurrenciesSet;
}
