package tech.pm.lobbyservice.domain.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pm.lobbyservice.domain.dto.PlayerInfoDto;
import tech.pm.lobbyservice.domain.model.entity.GameDetails;
import tech.pm.lobbyservice.domain.model.exception.EmptyGameAndProviderListException;
import tech.pm.lobbyservice.domain.model.exception.GameWithThatProviderAlreadyExists;
import tech.pm.lobbyservice.domain.model.exception.WrongHashCodeException;
import tech.pm.lobbyservice.domain.repository.GameDetailsRepository;
import tech.pm.lobbyservice.domain.util.HashCodeUtil;
import tech.pm.lobbyservice.domain.util.TokenDecoderUtil;

import java.util.List;
import java.util.Map;

@Service
public class GameDetailsService {

  GameDetailsRepository gameDetailsRepository;
  private static final Logger LOG = Logger.getLogger(HashCodeUtil.class);

  @Autowired
  public GameDetailsService(GameDetailsRepository gameDetailsRepository) {
    this.gameDetailsRepository = gameDetailsRepository;
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

  public void hashCodeVerification(Map<String, String> parameters) throws WrongHashCodeException {
    String hash = parameters.get("hash");
    parameters.remove("hash");
    if (!HashCodeUtil.getHashSign(parameters).equals(hash)) {
      LOG.warn("Hashcode verification failed!");
      throw new WrongHashCodeException("Mismatched hashcode");
    }
    LOG.info("Hashcode verification passed!");
  }
}
