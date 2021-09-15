package tech.pm.lobbyservice.web.service;

import java.util.ResourceBundle;

public class SecretSaltFactory {

  private static final SecretSaltFactory INSTANCE = new SecretSaltFactory();
  private static ResourceBundle secretSaltConfig;

  private SecretSaltFactory() {
    try {
      secretSaltConfig = ResourceBundle.getBundle("secretSalt");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String readSecretSaltValue() {
    return secretSaltConfig.getString("secretSalt.value");
  }

  public static String getSecretSaltValue() {
    return INSTANCE.readSecretSaltValue();
  }


}
