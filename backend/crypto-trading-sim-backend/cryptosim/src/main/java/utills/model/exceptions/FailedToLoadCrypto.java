package utills.model.exceptions;

public class FailedToLoadCrypto extends RuntimeException {
    public FailedToLoadCrypto(String message) {
        super(message);
    }
}
