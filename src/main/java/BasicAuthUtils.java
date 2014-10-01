import org.apache.commons.codec.binary.Base64;

public class BasicAuthUtils {
    private static final String BASIC_SEPARATOR = ":";
    private static final String BASIC_PREFIX = "Basic ";

    public static String generateAuthorizationHeader(String login, String password) {
        byte[] base64 = Base64.encodeBase64((login + BASIC_SEPARATOR + password).getBytes());
        return BASIC_PREFIX + new String(base64);
    }
}
