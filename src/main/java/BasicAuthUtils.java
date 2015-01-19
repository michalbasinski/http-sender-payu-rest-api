import commons.Constants;
import org.apache.commons.codec.binary.Base64;

public class BasicAuthUtils {

    public static String generateAuthorizationHeader(final String login, final String password) {
        StringBuilder builder = new StringBuilder();
        String headerToEncode = builder.append(login).append(Constants.BASIC_SEPARATOR).append(password).toString();
        return Constants.BASIC_PREFIX + new String(Base64.encodeBase64(headerToEncode.getBytes()));
    }
}
