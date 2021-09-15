package tech.pm.lobbyservice.domain.model.exception;

public class WrongHashCodeException extends Exception{

  public WrongHashCodeException(String message) {
    super(message);
  }
}
