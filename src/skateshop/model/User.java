package skateshop.model;

import skateshop.interfaces.Exportable;
/**
 * Base abstract class to represent an User extended by Admin or Customer.
 * 
 * <p>
 * 	It requires an userId (that will be generated either in registerAdmin() or doRegister()
 * depending on the role, username and password.
 * Implementing Exportable signs the contract stating that it can be displayed as a String and introduced
 * into a text file. Any user must be able to login.
 */
public abstract class User implements Exportable{
    private String userId;
    private String username;
    private String userPassword;

    public User(String userId, String username, String userPassword) {
        this.userId = userId;
        this.username = username;
        this.userPassword = userPassword;
    }
    //Copy constructor
    public User(User other) {
        this.userId       = other.userId;
        this.username     = other.username;
        this.userPassword = other.userPassword;
    }
    
    public String getUserId()       { return userId; }
    public String getUsername()     { return username; }
    public String getUserPassword() { return userPassword; }

    public void setUserId(String userId)             { this.userId = userId; }
    public void setUsername(String username)         { this.username = username; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public abstract boolean login(String username, String password);

    public abstract String getRole();

    /** Serialize to pipe-separated line for TXT storage */
    public abstract String toFileString();
    
    /**
     * Calls the already existing toString function. Represents that User able to be expressed as a String.
     */
    public String toDisplayString() {
		return toString();
    	
    }
}
