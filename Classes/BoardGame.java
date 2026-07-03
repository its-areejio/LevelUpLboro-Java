package Classes;

public class BoardGame extends Product {
    private final String gameType;
    private final String compatibility;

    public BoardGame(int productId, String gameType, String productName, double purchaseCost,
                     int quantityInStock, double price, String compatibility) {
        super(productId, ProductCategory.BOARDGAME, productName, purchaseCost, quantityInStock, price);
        if (gameType == null || gameType.isBlank()) {
            throw new IllegalArgumentException("Board game type cannot be blank.");
        }
        if (compatibility == null || compatibility.isBlank()) {
            throw new IllegalArgumentException("Compatibility cannot be blank.");
        }
        this.gameType = gameType.trim();
        this.compatibility = compatibility.trim();
    }

    @Override
    public String getDetails() {
        return gameType;
    }

    @Override
    public String getCompatibility() {
        return compatibility;
    }

    @Override
    public String toFileLine() {
        return String.join(";", String.valueOf(getProductId()), getProductCategory().getLabel(), gameType,
                getProductName(), String.format("%.2f", getPurchaseCost()), String.valueOf(getQuantityInStock()),
                String.format("%.2f", getPrice()), compatibility);
    }
}
