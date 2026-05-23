package skateshop.service;

import skateshop.exceptions.DuplicateUsernameException;
import skateshop.model.Admin;
import skateshop.model.Customer;
import skateshop.model.User;
import skateshop.util.FileManager;
import skateshop.util.IdGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
    }

    public void load() throws IOException {
        users = FileManager.loadUsers();
    }

    public void save() throws IOException {
        FileManager.saveUsers(users);
    }

    /** Returns the logged-in user, or null if credentials don't match */
    public User login(String username, String password) {
        for (User u : users) {
            if (u.login(username, password)) return u;
        }
        return null;
    }

    public Customer registerCustomer(String username, String password,
                                     String email, String address, String phone) throws IOException, DuplicateUsernameException {
        if (findByUsername(username) != null) {
            System.out.println("  Username already taken.");
            throw new DuplicateUsernameException(username);
        }
        Customer c = new Customer(IdGenerator.nextUserId(), username, password, email, address, phone);
        users.add(c);
        save();
        return c;
    }

    public Admin registerAdmin(String username, String password,
                               String email, String address, String phone) throws IOException {
        Admin a = new Admin(IdGenerator.nextUserId(), username, password, email, address, phone);
        users.add(a);
        save();
        return a;
    }

    public User findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void printCustomers() {
        users.stream()
             .filter(u -> u instanceof Customer)
             .forEach(u -> System.out.println("  " + u));
    }
}
