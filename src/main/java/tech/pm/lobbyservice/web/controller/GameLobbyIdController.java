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
  public List<GameDetails> getAllGamesByPlayer(@RequestParam String sessionToken)
          throws WrongHashCodeException, EmptyGameAndProviderListException {

   // gameDetailsService.hashCodeVerification(allParameters);
    return gameDetailsService.getAllGamesAndProvidersByPlayer(sessionToken);
  }

  @PostMapping("/addGame")
  public GameDetails addGame(@RequestBody GameDetails gameDetails) throws GameWithThatProviderAlreadyExists {
    return gameDetailsService.addGame(gameDetails);
  }

  @GetMapping("/launch")
  public String getLaunchUrl(@RequestParam String game_id,
                             @RequestParam String provider_id, @RequestParam String sessionToken) {
    String uri = "http://localhost:8081/launch" + "?game_id=" +
            game_id +
            "&provider_id=" +
            provider_id +
            "&sessionToken=" +
            sessionToken;
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(uri, String.class);
  }
}
