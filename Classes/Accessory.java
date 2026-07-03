package Classes;

public class Accessory extends Product {
    private final String accessoryType;
    private final String compatibility;

    public Accessory(int productId, String accessoryType, String productName, double purchaseCost,
                     int quantityInStock, double price, String compatibility) {
        super(productId, ProductCategory.ACCESSORY, productName, purchaseCost, quantityInStock, price);
        if (accessoryType == null || accessoryType.isBlank()) {
            throw new IllegalArgumentException("Accessory type cannot be blank.");
        }
        if (compatibility == null || compatibility.isBlank()) {
            throw new IllegalArgumentException("Compatibility cannot be blank.");
        }
        this.accessoryType = accessoryType.trim();
        this.compatibility = compatibility.trim();
    }

    @Override
    public String getDetails() {
        return accessoryType;
    }

    @Override
    public String getCompatibility() {
        return compatibility;
    }

    @Override
    public String toFileLine() {
        return String.join(";", String.valueOf(getProductId()), getProductCategory().getLabel(), accessoryType,
                getProductName(), String.format("%.2f", getPurchaseCost()), String.valueOf(getQuantityInStock()),
                String.format("%.2f", getPrice()), compatibility);
    }
}
