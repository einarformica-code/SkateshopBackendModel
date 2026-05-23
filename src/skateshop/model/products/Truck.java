package skateshop.model.products;

public class Truck extends Product {
    private double width;

    public Truck(String id, String brand, double price, int stock, double width) {
        super(id, brand, price, stock);
        this.width = width;
    }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    @Override public String getType() { return "TRUCK"; }

    @Override
    public String toFileString() {
        return String.format("TRUCK|%s|%s|%.2f|%d|%.2f",
                getId(), getBrand(), getPrice(), getStock(), width);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("  Width:%.2f\"", width);
    }
}
