package tech.pm.lobbyservice.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "game_lobby_ids")
public class GameLobbyId {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @Column(name = "provider_id",unique = true)
  private String providerName;

  @Column(name = "game_id",unique = true)
  private String gameName;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameLobbyId")
  private Set<Country> availableCountries;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameLobbyId")
  private Set<Currency> availableCurrencies;
}
