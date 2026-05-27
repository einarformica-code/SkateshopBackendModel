package skateshop.model;
/**
 * 
* Admin represents the Administrator type of User, with full system privileges.
*/
public class Admin extends User {
    private String email;
    private String address;
    private String phone;
    
    /**
     * Constructs an Admin with all required data.
     * @param userId       unique identifier
     * @param username     login name
     * @param userPassword password
     * @param email        contact email
     * @param address      physical address
     * @param phone        contact phone
     */
    public Admin(String userId, String username, String userPassword,
                 String email, String address, String phone) {
        super(userId, username, userPassword);
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getEmail()   { return email; }
    public String getAddress() { return address; }
    public String getPhone()   { return phone; }

    public void setEmail(String email)     { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone)     { this.phone = phone; }
    
    
    /**
     * Validates login credentials.
     * @param username the entered username
     * @param password the entered password
     * @return true if both match stored values
     */
    @Override
    public boolean login(String username, String password) {
        return getUsername().equals(username) && getUserPassword().equals(password);
    }
    
    /** Returns the role string "ADMIN". */

    @Override public String getRole() { return "ADMIN"; }
    
    /**
     * Serializes the admin line for file storage.
     * @return format: ADMIN|userId|username|password|email|address|phone
     */
    @Override
    public String toFileString() {
        return String.format("ADMIN|%s|%s|%s|%s|%s|%s",
                getUserId(), getUsername(), getUserPassword(), email, address, phone);
    }

    @Override
    public String toString() {
        return String.format("Admin[%s]  Name:%-15s  Email:%s",
                getUserId(), getUsername(), email);
    }
}
