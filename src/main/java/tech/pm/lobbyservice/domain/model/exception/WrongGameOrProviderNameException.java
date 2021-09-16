package tech.pm.lobbyservice.domain.model.exception;

public class WrongGameOrProviderNameException extends Exception{

  public WrongGameOrProviderNameException(String message) {
    super(message);
  }

}
