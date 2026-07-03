package Classes;

public abstract class Product {
    private final int productId;
    private final ProductCategory productCategory;
    private final String productName;
    private final double purchaseCost;
    private int quantityInStock;
    private final double price;

    protected Product(int productId, ProductCategory productCategory, String productName, double purchaseCost,
                      int quantityInStock, double price) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be positive.");
        }
        if (productCategory == null) {
            throw new IllegalArgumentException("Product category cannot be null.");
        }
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank.");
        }
        if (purchaseCost < 0) {
            throw new IllegalArgumentException("Purchase cost cannot be negative.");
        }
        if (quantityInStock < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.productId = productId;
        this.productCategory = productCategory;
        this.productName = productName.trim();
        this.purchaseCost = purchaseCost;
        this.quantityInStock = quantityInStock;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        if (quantityInStock < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
        this.quantityInStock = quantityInStock;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getDetails();

    public abstract String getCompatibility();

    public abstract String toFileLine();

    @Override
    public String toString() {
        return String.format("[%d] %s | Category: %s | Price: £%.2f | Stock: %d | Compatible: %s",
                productId, productName, productCategory.getLabel(), price, quantityInStock, getCompatibility());
    }
}
