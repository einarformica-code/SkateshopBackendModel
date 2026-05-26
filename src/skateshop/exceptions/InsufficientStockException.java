package skateshop.exceptions;
/**
 * Exception designed to be thrown when the requested quantity is larger than
 *  the stock of the product that was found in the Catalog. 
 *  <p>
 *  Used in methods related to adding an item as a customer or manually modifying Stock as an administrator.
 *  @author Einar Formica
	@version 1.0
 */
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String id, int available, int requested) {
    	super("Insufficient stock for " + id + ". Available: " + available + ", requested: " + requested);
    }
}