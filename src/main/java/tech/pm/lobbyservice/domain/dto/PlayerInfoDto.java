package tech.pm.lobbyservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PlayerInfoDto {

  String country;
  String currency;

}
