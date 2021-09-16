package tech.pm.lobbyservice.domain.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pm.lobbyservice.domain.dto.PlayerInfoDto;
import tech.pm.lobbyservice.domain.model.entity.Country;
import tech.pm.lobbyservice.domain.model.entity.GameDetails;
import tech.pm.lobbyservice.domain.model.exception.*;
import tech.pm.lobbyservice.domain.repository.CountryRepository;
import tech.pm.lobbyservice.domain.repository.CurrencyRepository;
import tech.pm.lobbyservice.domain.repository.GameDetailsRepository;
import tech.pm.lobbyservice.domain.util.HashCodeUtil;
import tech.pm.lobbyservice.domain.util.TokenDecoderUtil;

import java.util.List;
import java.util.Map;

@Service
public class GameDetailsService {

  GameDetailsRepository gameDetailsRepository;
  CurrencyRepository currencyRepository;
  CountryRepository countryRepository;
  private static final Logger LOG = Logger.getLogger(HashCodeUtil.class);

  @Autowired
  public GameDetailsService(GameDetailsRepository gameDetailsRepository,
                            CountryRepository countryRepository,
                            CurrencyRepository currencyRepository) {
    this.gameDetailsRepository = gameDetailsRepository;
    this.countryRepository = countryRepository;
    this.currencyRepository = currencyRepository;
  }

  public List<GameDetails> getAllGamesAndProviders() throws EmptyGameAndProviderListException {
    if (gameDetailsRepository.findAll().isEmpty()) {
      throw new EmptyGameAndProviderListException("Games list is empty!");
    }
    return gameDetailsRepository.findAll();
  }

  public GameDetails addGame(GameDetails gameDetails) throws GameWithThatProviderAlreadyExists {
    if (gameDetailsRepository.existsByProviderNameAndGameName(gameDetails.getProviderName(), gameDetails.getGameName()))
      throw new GameWithThatProviderAlreadyExists("Game with that provider is already exists!");
    return gameDetailsRepository.save(gameDetails);
  }

  public List<GameDetails> getAllGamesAndProvidersByPlayer(String token) throws EmptyGameAndProviderListException {
    PlayerInfoDto player = TokenDecoderUtil.getPlayerInfoDto(token);
    System.out.println("currency = " + player.getCurrency() +
            "country = " + player.getCountry());
    if (gameDetailsRepository.findGameDetailsByCurrencyAndCountry(player.getCountry(), player.getCurrency()).isEmpty())
      throw new EmptyGameAndProviderListException("There are no available games for this player!");
    return gameDetailsRepository.findGameDetailsByCurrencyAndCountry(player.getCountry(), player.getCurrency());
  }

//  public void hashCodeVerification(Map<String, String> parameters) throws WrongHashCodeException {
//    String hash = parameters.get("hash");
//    parameters.remove("hash");
//    if (!HashCodeUtil.getHashSign(parameters).equals(hash)) {
//      LOG.warn("Hashcode verification failed!");
//      throw new WrongHashCodeException("Mismatched hashcode");
//    }
//    LOG.info("Hashcode verification passed!");
//  }

  private GameDetails getGameDetailsByProviderNameAndGameName(String game_id, String provider_id)
          throws WrongGameOrProviderNameException {
    if (!gameDetailsRepository.existsByProviderNameAndGameName(game_id, provider_id)) {
      throw new WrongGameOrProviderNameException("Game with that Name or Provider is not exists!");
    }
    return gameDetailsRepository.getGameDetailsByProviderNameAndGameName(provider_id, game_id);
  }

  public void checkIfGameAvailableForCurrentPlayer(String game_id, String provider_id, String sessionToken)
          throws WrongLaunchUrlException, WrongGameOrProviderNameException {
    LOG.info("Checking started");
    GameDetails tmpGameDetails = getGameDetailsByProviderNameAndGameName(provider_id, game_id);
    PlayerInfoDto player = TokenDecoderUtil.getPlayerInfoDto(sessionToken);
    if (!tmpGameDetails.getAvailableCurrenciesSet().contains(currencyRepository.findByTitle(player.getCurrency())) ||
            !tmpGameDetails.getAvailableCountriesSet().contains(countryRepository.findByName(player.getCountry()))) {
      throw new WrongLaunchUrlException("Wrong launch url or game is unavailable!");
    }
  }

  public void addAvailableCountriesToGame(String game_id, String provider_id, List<String> countries)
          throws WrongGameOrProviderNameException, WrongCountryNameException {
    if (!gameDetailsRepository.existsByProviderNameAndGameName(provider_id, game_id)) {
      throw new WrongGameOrProviderNameException("Game with that Name or Provider is not exists!");
    }

    GameDetails gameDetails = gameDetailsRepository.getGameDetailsByProviderNameAndGameName(provider_id, game_id);

    for (String country : countries) {
      if (!countryRepository.existsByName(country)) {
        throw new WrongCountryNameException("Wrong country name: " + country);
      }
      gameDetails.getAvailableCountriesSet().add(countryRepository.findByName(country));
    }
    gameDetailsRepository.save(gameDetails);

    for (String country : countries) {
      Country tmpCountry = countryRepository.findByName(country);
      tmpCountry.getGameDetailsSet().add(gameDetails);
      countryRepository.save(tmpCountry);
    }
    LOG.info("Countries assigned!");
  }

}
