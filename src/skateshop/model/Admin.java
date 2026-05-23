package skateshop.model;

public class Admin extends User {
    private String email;
    private String address;
    private String phone;

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

    @Override
    public boolean login(String username, String password) {
        return getUsername().equals(username) && getUserPassword().equals(password);
    }

    @Override public String getRole() { return "ADMIN"; }

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
