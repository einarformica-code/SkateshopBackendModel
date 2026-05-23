package skateshop.exceptions;

public class ProductNotFoundException extends Exception {
public ProductNotFoundException(String id) {

	super("Producto no encontrado: " +id);
	
}
}
