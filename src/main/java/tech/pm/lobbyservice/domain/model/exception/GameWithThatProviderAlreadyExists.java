package tech.pm.lobbyservice.domain.model.exception;

public class GameWithThatProviderAlreadyExists extends Exception{

  public GameWithThatProviderAlreadyExists(String message) {
    super(message);
  }
}
