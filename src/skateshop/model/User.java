package skateshop.model;

import skateshop.interfaces.Exportable;

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
    
    public String toDisplayString() {
		return toString();
    	
    }
}
