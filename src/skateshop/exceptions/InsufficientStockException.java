package skateshop.exceptions;

public class InsufficientStockException extends Exception {
    public InsufficientStockException(String id, int available, int requested) {
        super("Insufficient stock for " + id + ". Availible: " + available + ", requested: " + requested);
    }
}