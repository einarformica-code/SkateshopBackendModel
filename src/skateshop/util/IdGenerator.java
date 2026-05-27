package skateshop.util;

import skateshop.model.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class used for identificator generation.
 *
 * It guarantees exclusive IDs for orders, sales, and users across restarts
 * by reading the highest existing ID from the persisted data files and
 * resuming from max + 1.
 */
public class IdGenerator {

    /**
     * Counter for order IDs.
     * Default start is 1000; overridden by {@link #initialize} on load.
     */
    private static final AtomicInteger orderCount = new AtomicInteger(1000);

    /**
     * Counter for sale IDs.
     * Default start is 500; overridden by {@link #initialize} on load.
     */
    private static final AtomicInteger saleCount  = new AtomicInteger(500);

    /**
     * Counter for user IDs.
     * Default start is 1; overridden by {@link #initialize} on load.
     */
    private static final AtomicInteger userCount  = new AtomicInteger(1);

    /**
     * Initialises the counters from already-persisted data so that IDs
     * generated after a restart never collide with existing records.
     *
     * <p>Call this once during application startup, <em>after</em> the data
     * files have been loaded but <em>before</em> any new entity is created.</p>
     *
     * @param users        list of users loaded from users.txt
     * @param orderRecords raw order lines loaded from orders.txt
     *                     (each element is a String[] with orderId at index 1)
     * @param saleRecords  raw sale lines loaded from sales.txt
     *                     (each element is a String[] with saleId at index 1)
     */
    public static void initialize(List<User> users,
                                  List<String[]> orderRecords,
                                  List<String[]> saleRecords) {
        // ── Users: format USR-001 ────────────────────────────────────────
        int maxUser = users.stream()
                .map(User::getUserId)            // "USR-001"
                .map(id -> id.replace("USR-", ""))
                .mapToInt(s -> {
                    try { return Integer.parseInt(s); }
                    catch (NumberFormatException e) { return 0; }
                })
                .max()
                .orElse(0);
        userCount.set(maxUser + 1);

        // ── Orders: format ORD-1000, column index 1 ─────────────────────
        int maxOrder = orderRecords.stream()
                .filter(t -> t.length > 1)
                .map(t -> t[1].replace("ORD-", ""))
                .mapToInt(s -> {
                    try { return Integer.parseInt(s); }
                    catch (NumberFormatException e) { return 0; }
                })
                .max()
                .orElse(999);   // default keeps the gap above 1000
        orderCount.set(maxOrder + 1);

        // ── Sales: format SAL-500, column index 1 ───────────────────────
        int maxSale = saleRecords.stream()
                .filter(t -> t.length > 1)
                .map(t -> t[1].replace("SAL-", ""))
                .mapToInt(s -> {
                    try { return Integer.parseInt(s); }
                    catch (NumberFormatException e) { return 0; }
                })
                .max()
                .orElse(499);   // default keeps the gap above 500
        saleCount.set(maxSale + 1);
    }

    /**
     * Generates a new order ID.
     * @return ID with format {@code "ORD-XXXX"}
     */
    public static String nextOrderId() { return "ORD-" + orderCount.getAndIncrement(); }

    /**
     * Generates a new sale ID.
     * @return ID with format {@code "SAL-XXXX"}
     */
    public static String nextSaleId()  { return "SAL-" + saleCount.getAndIncrement(); }

    /**
     * Generates a new user ID.
     * @return ID with format {@code "USR-XXX"} (zero-padded to at least 3 digits)
     */
    public static String nextUserId()  {
        return "USR-" + String.format("%03d", userCount.getAndIncrement());
    }
}