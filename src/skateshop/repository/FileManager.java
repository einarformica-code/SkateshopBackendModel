package skateshop.repository;

import skateshop.model.*;
import skateshop.model.products.*;

import java.io.*;
import java.util.*;

/**
 * Handles all reading and writing of .txt data files.
 * All files live in the  data/  folder relative to the working directory.
 */
public class FileManager {

    // ── File paths ──────────────────────────────────────────────
    public static final String DATA_DIR      = "data/";
    public static final String CATALOG_FILE  = DATA_DIR + "catalog.txt";
    public static final String USERS_FILE    = DATA_DIR + "users.txt";
    public static final String ORDERS_FILE   = DATA_DIR + "orders.txt";
    public static final String SALES_FILE    = DATA_DIR + "sales.txt";
    public static final String STOCK_FILE    = DATA_DIR + "stock_movements.txt";

    static {
        new File(DATA_DIR).mkdirs();
    }

    //────────────────────────── CATALOG ─────────────────────────
    
    /** Saves the list of products to catalog.txt. */
    
    
    public static void saveCatalog(List<Product> products) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CATALOG_FILE))) {
            for (Product p : products) pw.println(p.toFileString());
        }
    }
    /** Loads products from catalog.txt; returns an empty list if file does not exist. */

    public static List<Product> loadCatalog() throws IOException {
        List<Product> list = new ArrayList<>();
        File f = new File(CATALOG_FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                Product p = parseProduct(line);
                if (p != null) list.add(p);
            }
        }
        return list;
    }
    
    /** Parses a product line from the catalog file. */
    private static Product parseProduct(String line) {
        String[] t = line.split("\\|");
        try {
            String type  = t[0];
            String id    = t[1];
            String brand = t[2];
            double price = Double.parseDouble(t[3]);
            int stock    = Integer.parseInt(t[4]);
            switch (type) {
                case "TRUCK":    return new Truck(id, brand, price, stock, Double.parseDouble(t[5]));
                case "BOARD":    return new Board(id, brand, price, stock, Double.parseDouble(t[5]));
                case "WHEELS":   return new Wheels(id, brand, price, stock, Integer.parseInt(t[5]), t[6]);
                case "BEARINGS": return new Bearings(id, brand, price, stock);
                default: System.err.println("Unknown product type: " + type); return null;
            }
        } catch (Exception e) {
            System.err.println("Error parsing product line: " + line);
            return null;
        }
    }

    // ────────────────────────── USERS ───────────────────────────
    /** Saves all users to users.txt. */

    public static void saveUsers(List<User> users) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) pw.println(u.toFileString());
        }
    }
    
    
    /** Loads users from users.txt; returns empty list if file does not exist. */
    public static List<User> loadUsers() throws IOException {
        List<User> list = new ArrayList<>();
        File f = new File(USERS_FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] t = line.split("\\|");
                try {
                    if ("CUSTOMER".equals(t[0])) {
                        list.add(new Customer(t[1], t[2], t[3], t[4], t[5], t[6]));
                    } else if ("ADMIN".equals(t[0])) {
                        list.add(new Admin(t[1], t[2], t[3], t[4], t[5], t[6]));
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing user: " + line);
                }
            }
        }
        return list;
    }
    
    

    // ────────────────────────── ORDERS ──────────────────────────

    /**
     * Orders are stored as simple summary lines (items not re-parsed on load
     * to avoid dependency on a partially-loaded catalog).
     * Format: ORDER|orderId|customerId|status|date|total
     */
    public static void appendOrder(Order order) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ORDERS_FILE, true))) {
            pw.println(String.format("ORDER|%s|%s|%s|%s|%.2f",
                    order.getOrderId(),
                    order.getCustomer().getUserId(),
                    order.getStatus(),
                    order.getDate(),
                    order.getTotal()));
        }
    }
    
    /** Loads all order records as arrays of strings (raw lines). */
    public static List<String[]> loadOrderRecords() throws IOException {
        List<String[]> list = new ArrayList<>();
        File f = new File(ORDERS_FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) list.add(line.split("\\|"));
            }
        }
        return list;
    }

    // ────────────────────────── SALES ───────────────────────────
    /** Appends a sale record to sales.txt. */
    public static void appendSale(Sale sale) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SALES_FILE, true))) {
            pw.println(sale.toFileString());
        }
    }

    /** Loads all sale records as raw string arrays. */
    public static List<String[]> loadSaleRecords() throws IOException {
        List<String[]> list = new ArrayList<>();
        File f = new File(SALES_FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) list.add(line.split("\\|"));
            }
        }
        return list;
    }

    // ────────────────────── STOCK MOVEMENTS ─────────────────────
    /** Appends a stock movement line to stock_movements.txt. */
    public static void appendStockMovement(StockMovement sm) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(STOCK_FILE, true))) {
            pw.println(sm.toFileString());
        }
    }
    /** Loads all stock movement lines as raw strings. */
    public static List<String> loadStockMovementLines() throws IOException {
        List<String> list = new ArrayList<>();
        File f = new File(STOCK_FILE);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) list.add(line.trim());
            }
        }
        return list;
    }
}
