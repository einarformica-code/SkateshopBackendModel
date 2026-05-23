package skateshop.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger orderCount = new AtomicInteger(1000);
    private static final AtomicInteger saleCount  = new AtomicInteger(500);
    private static final AtomicInteger userCount  = new AtomicInteger(1);

    public static String nextOrderId() { return "ORD-" + orderCount.getAndIncrement(); }
    public static String nextSaleId()  { return "SAL-" + saleCount.getAndIncrement(); }
    public static String nextUserId()  { return "USR-" + String.format("%03d", userCount.getAndIncrement()); }
}
