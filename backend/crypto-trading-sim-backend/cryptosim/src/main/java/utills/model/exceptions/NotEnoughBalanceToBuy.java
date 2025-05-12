package utills.model.exceptions;

public class NotEnoughBalanceToBuy extends RuntimeException {
    public NotEnoughBalanceToBuy(String message) {
        super(message);
    }
}
