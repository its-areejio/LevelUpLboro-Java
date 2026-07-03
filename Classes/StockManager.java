package Classes;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StockManager {
    private static final Path STOCK_DATA = Paths.get("Data", "Stock.txt");

    public static List<Product> loadProducts() {
        if (!Files.exists(STOCK_DATA)) {
            return Collections.emptyList();
        }
        List<Product> products = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(STOCK_DATA)) {
                if (line == null || line.isBlank()) {
                    continue;
                }
                try {
                    products.add(parseProduct(line));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Skipping invalid stock line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read stock data: " + e.getMessage());
        }
        return products;
    }

    private static Product parseProduct(String line) {
        String[] parts = line.split(";");
        if (parts.length != 8) {
            throw new IllegalArgumentException("Stock line must have 8 fields.");
        }
        int productId = Integer.parseInt(parts[0].trim());
        ProductCategory category = ProductCategory.fromLabel(parts[1].trim());
        String detail = parts[2].trim();
        String name = parts[3].trim();
        double purchaseCost = Double.parseDouble(parts[4].trim());
        int quantity = Integer.parseInt(parts[5].trim());
        double price = Double.parseDouble(parts[6].trim());
        String compatibility = parts[7].trim();
        if (category == ProductCategory.BOARDGAME) {
            return new BoardGame(productId, detail, name, purchaseCost, quantity, price, compatibility);
        }
        return new Accessory(productId, detail, name, purchaseCost, quantity, price, compatibility);
    }

    public static Optional<Product> findProductById(List<Product> products, int id) {
        return products.stream().filter(product -> product.getProductId() == id).findFirst();
    }

    public static boolean addProduct(Product newProduct, List<Product> products) {
        if (findProductById(products, newProduct.getProductId()).isPresent()) {
            return false;
        }
        products.add(newProduct);
        saveProducts(products);
        return true;
    }

    public static void saveProducts(List<Product> products) {
        try (BufferedWriter writer = Files.newBufferedWriter(STOCK_DATA)) {
            for (Product product : products) {
                writer.write(product.toFileLine());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Unable to save stock data: " + e.getMessage());
        }
    }
}
