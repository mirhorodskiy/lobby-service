package tech.pm.lobbyservice.web.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tech.pm.lobbyservice.domain.model.entity.GameDetails;
import tech.pm.lobbyservice.domain.model.exception.EmptyGameAndProviderListException;
import tech.pm.lobbyservice.domain.model.exception.GameWithThatProviderAlreadyExists;
import tech.pm.lobbyservice.domain.model.exception.WrongHashCodeException;
import tech.pm.lobbyservice.domain.service.GameDetailsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lobby")
public class GameLobbyIdController {
  private final GameDetailsService gameDetailsService;

  public GameLobbyIdController(GameDetailsService gameDetailsService) {
    this.gameDetailsService = gameDetailsService;
  }

  @GetMapping("/getAll")//get all games
  public List<GameDetails> getAllGames() throws EmptyGameAndProviderListException {
    return gameDetailsService.getAllGamesAndProviders();
  }

  //get all games by token
  @GetMapping()
  public List<GameDetails> getAllGamesByPlayer(@RequestParam String token, @RequestParam Map<String, String> allParameters)
          throws WrongHashCodeException, EmptyGameAndProviderListException {

    gameDetailsService.hashCodeVerification(allParameters);
    return gameDetailsService.getAllGamesAndProvidersByPlayer(token);
  }

  @PostMapping("/addGame")
  public GameDetails addGame(@RequestBody GameDetails gameDetails) throws GameWithThatProviderAlreadyExists {
    return gameDetailsService.addGame(gameDetails);
  }

  @GetMapping("/launch")
  public String getLaunchUrl(@RequestParam String gameTitle,
                             @RequestParam String providerTitle, @RequestParam String sessionKey) {
    String uri = "http://gameRepositoryService/getUrl" + "?gameTitle=" +
            gameTitle +
            "&providerTitle=" +
            providerTitle +
            "&sessionKey=" +
            sessionKey;
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(uri, String.class);
  }
}
