import commons.Constants;
import org.apache.commons.codec.binary.Base64;

public class BasicAuthUtils {

    public static String generateAuthorizationHeader(final String login, final String password) {
        byte[] base64 = Base64.encodeBase64((login + Constants.BASIC_SEPARATOR + password).getBytes());
        return Constants.BASIC_PREFIX + new String(base64);
    }
}
