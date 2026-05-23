package skateshop.exceptions;

public class DuplicateUsernameException extends Exception {
    public DuplicateUsernameException(String username) {
        super("El nombre de usuario ya existe: " + username);
    }
}
