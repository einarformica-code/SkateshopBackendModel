package skateshop.exceptions;
/**
 * Exception thrown in registerUser() - UserService 
 * when Username already exists in the users HashMap collection.
 *
	@author Einar Formica
	@version 1.0 */
public class DuplicateUsernameException extends Exception {
    public DuplicateUsernameException(String username) {
        super("El nombre de usuario ya existe: " + username);
    }
}
