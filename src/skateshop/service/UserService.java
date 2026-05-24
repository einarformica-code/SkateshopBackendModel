package skateshop.service;

import skateshop.exceptions.DuplicateUsernameException;
import skateshop.exceptions.ProductNotFoundException;
import skateshop.interfaces.Searchable;
import skateshop.model.Admin;
import skateshop.model.Customer;
import skateshop.model.User;
import skateshop.util.FileManager;
import skateshop.util.IdGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserService implements Searchable <User> {
	private HashMap<String, User> users = new HashMap<>();

    public UserService() {
        this.users = new HashMap<>();
    }

    public void load() throws IOException {
        users = new HashMap<>();
        
    }

    public void save() throws IOException {
    	FileManager.saveUsers(new ArrayList<>(users.values()));

    }

    /** Returns the logged-in user, or null if credentials don't match */
    public User login(String username, String password) {
    	User u = users.get(username);
    	if (u != null && u.login(username, password)) return u;
    	return null;
    }

    public Customer registerCustomer(String username, String password,
                                     String email, String address, String phone) throws IOException, DuplicateUsernameException {
        if (findByUsername(username) != null) {
            System.out.println("  Username already taken.");
            throw new DuplicateUsernameException(username);
        }
        Customer c = new Customer(IdGenerator.nextUserId(), username, password, email, address, phone);
        users.put(c.getUsername(), c);
        save();
        return c;
    }

    public Admin registerAdmin(String username, String password,
                               String email, String address, String phone) throws IOException {
        Admin a = new Admin(IdGenerator.nextUserId(), username, password, email, address, phone);
        users.put(a.getUsername(), a);
        save();
        return a;
    }

    public User findByUsername(String username) {
    	return users.get(username);
    }

    public void printCustomers() {
    	users.values().stream()
        .filter(u -> u instanceof Customer)
        .forEach(u -> System.out.println("  " + u));
    }

	@Override
	public User searchById(String id) throws ProductNotFoundException {
		User user = users.get(id);
	
		if(user==null) {throw new ProductNotFoundException("User with id: " + id + " not found");}
		
		return user;
	
	}

	@Override
	public List<User> searchByName(String nombre) {
		List<User> result = new ArrayList<User>();
		
		for (User u : users.values()) {
			if(u.getUsername().equalsIgnoreCase(nombre)) {
				result.add(u);
			}
		}
		return result;
		
		
	}
}
