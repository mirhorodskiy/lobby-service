package tech.pm.lobbyservice.domain.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import tech.pm.lobbyservice.web.service.SecretSaltFactory;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HashCodeUtil {

  private static final Logger LOG = Logger.getLogger(HashCodeUtil.class);

  public static String getHashSign(String game_id, String provider_id, String sessionToken) {
    LOG.info("Calculating hash");
    return hashCodeMD5(game_id + provider_id + sessionToken + SecretSaltFactory.getSecretSaltValue());
  }


  private static String hashCodeMD5(String str) {
    return DigestUtils.md5Hex(str);
  }

}
