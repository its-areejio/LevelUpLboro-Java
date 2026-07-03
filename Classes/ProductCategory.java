package Classes;

public enum ProductCategory {
    BOARDGAME("board game"),
    ACCESSORY("accessory");

    private final String label;

    ProductCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ProductCategory fromLabel(String label) {
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Category cannot be blank.");
        }
        String normalized = label.trim().toLowerCase();
        if (normalized.equals("board game") || normalized.equals("boardgame") || normalized.equals("board-game")) {
            return BOARDGAME;
        }
        if (normalized.equals("accessory")) {
            return ACCESSORY;
        }
        throw new IllegalArgumentException("Unsupported category: " + label);
    }
}
