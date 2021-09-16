package tech.pm.lobbyservice.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tech.pm.lobbyservice.domain.model.entity.GameDetails;

import java.util.List;

public interface GameDetailsRepository extends CrudRepository<GameDetails, Long> {

  List<GameDetails> findAll();

  Boolean existsByProviderNameAndGameName(String providerName, String gameName);

  GameDetails getGameDetailsByProviderNameAndGameName(String providerName, String gameName);


  @Query("select distinct g from GameDetails g " +
          "join g.availableCurrenciesSet currency join g.availableCountriesSet country " +
          "where currency.title = ?2 and country.name = ?1")
  List<GameDetails> findGameDetailsByCurrencyAndCountry(String country, String currency);

}
