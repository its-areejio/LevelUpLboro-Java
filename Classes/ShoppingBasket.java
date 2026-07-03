package Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingBasket {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (product.getQuantityInStock() <= 0) {
            throw new IllegalArgumentException("Product is out of stock.");
        }
        items.merge(product, 1, Integer::sum);
    }

    public void removeItem(Product product) {
        if (product == null) {
            return;
        }
        Integer count = items.get(product);
        if (count == null) {
            return;
        }
        if (count <= 1) {
            items.remove(product);
        } else {
            items.put(product, count - 1);
        }
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getQuantity(Product product) {
        return items.getOrDefault(product, 0);
    }

    public double calculateTotal() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public List<String> getBasketSummary() {
        List<String> summary = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            summary.add(String.format("%d x %s @ £%.2f = £%.2f",
                    entry.getValue(), entry.getKey().getProductName(), entry.getKey().getPrice(),
                    entry.getKey().getPrice() * entry.getValue()));
        }
        return Collections.unmodifiableList(summary);
    }

    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }
}
