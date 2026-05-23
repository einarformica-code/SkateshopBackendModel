package skateshop.model.products;

public class Bearings extends Product {

    public Bearings(String id, String brand, double price, int stock) {
        super(id, brand, price, stock);
    }

    @Override public String getType() { return "BEARINGS"; }

    @Override
    public String toFileString() {
        return String.format("BEARINGS|%s|%s|%.2f|%d",
                getId(), getBrand(), getPrice(), getStock());
    }
}
