package tech.pm.lobbyservice.domain.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import tech.pm.lobbyservice.domain.dto.PlayerInfoDto;

import java.util.HashMap;
import java.util.Map;

public class TokenDecoderUtil {

  private static Map<String, String> tokenDecode (String token) {
    Map<String, Claim> map = JWT.decode(token).getClaims();
    Map<String, String> params = new HashMap<>();
    for (String key : map.keySet()) {
      params.put(key, map.get(key).asString());
    }
    return params;
  }

  public static PlayerInfoDto getPlayerInfoDto(String token) {
    Map<String, String> params = tokenDecode(token);
    return new PlayerInfoDto(params.get("country"), params.get("currency"));
  }
}
