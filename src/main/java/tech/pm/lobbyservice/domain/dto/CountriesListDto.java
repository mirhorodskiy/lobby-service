package tech.pm.lobbyservice.domain.dto;

import lombok.Value;

import java.util.List;

@Value
public class CountriesListDto {
  List<String> countries;
}
