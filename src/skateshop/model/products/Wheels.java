package skateshop.model.products;

public class Wheels extends Product {
    private int width;       // mm
    private String hardness; // e.g. "99A"

    public Wheels(String id, String brand, double price, int stock, int width, String hardness) {
        super(id, brand, price, stock);
        this.width = width;
        this.hardness = hardness;
    }

    public int getWidth()        { return width; }
    public String getHardness()  { return hardness; }
    public void setWidth(int width)          { this.width = width; }
    public void setHardness(String hardness) { this.hardness = hardness; }

    @Override public String getType() { return "WHEELS"; }

    @Override
    public String toFileString() {
        return String.format("WHEELS|%s|%s|%.2f|%d|%d|%s",
                getId(), getBrand(), getPrice(), getStock(), width, hardness);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("  Width:%dmm  Hardness:%s", width, hardness);
    }
}
