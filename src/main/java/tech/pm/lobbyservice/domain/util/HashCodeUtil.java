package tech.pm.lobbyservice.domain.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import tech.pm.lobbyservice.web.service.SecretSaltFactory;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HashCodeUtil {

  private static final Logger LOG = Logger.getLogger(HashCodeUtil.class);

  public static String getHashSign(Map<String, String> properties) {
    LOG.info("Calculating hash");
    StringBuilder builder = new StringBuilder();
    List<String> keys = properties.keySet().stream()
            .filter((s) -> !s.equals("hash"))
            .sorted()
            .collect(Collectors.toList());
    for (String key : keys) {
      builder.append(properties.get(key));
    }
    builder.append(SecretSaltFactory.getSecretSaltValue());

    return hashCodeMD5(builder.toString());
  }


  private static String hashCodeMD5(String str) {
    return DigestUtils.md5Hex(str);
  }

}
