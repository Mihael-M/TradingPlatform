package utills.model.exceptions;

public class NotEnoughHoldings extends RuntimeException {
    public NotEnoughHoldings(String message) {
        super(message);
    }
}
