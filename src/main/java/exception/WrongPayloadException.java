package exception;

public class WrongPayloadException extends Exception {
    public WrongPayloadException() {
        super();
    }

    public WrongPayloadException(String message) {
        super(message);
    }

    public WrongPayloadException(Exception e) {
        super(e);
    }
}
