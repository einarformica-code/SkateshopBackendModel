package skateshop.exceptions;
/**
 * Exception trown when id of requested product is not contained in the product catalogue.
 * <p>
 * It is used in methods where an existing id is expected to be registered.
 */
public class ProductNotFoundException extends Exception {
public ProductNotFoundException(String id) {

	super("Producto no encontrado: " +id);
	
}
}
