package CLI;

import Classes.Accessory;
import Classes.BoardGame;
import Classes.Product;
import Classes.ProductCategory;
import Classes.StockManager;
import java.util.List;
import java.util.Scanner;

public class AdminCLI {
    private final List<Product> products;
    private final Scanner consoleInput;

    public AdminCLI(Scanner consoleInput) {
        this.consoleInput = consoleInput;
        this.products = StockManager.loadProducts();
    }

    public void run() {
        System.out.println("ADMIN VIEW");

        while (true) {
            printAdminMenu();
            int selection = readInt("Select an option:");

            switch (selection) {
                case 1 -> viewProducts();
                case 2 -> addNewProduct();
                case 0 -> {
                    System.out.println("Returning to main menu.");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void printAdminMenu() {
        System.out.println("PLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add new product");
        System.out.println("0) Log out");
    }

    private void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products are currently available.");
            return;
        }
        products.stream()
                .sorted((a, b) -> Integer.compare(a.getProductId(), b.getProductId()))
                .forEach(System.out::println);
    }

    private void addNewProduct() {
        try {
            int id = readInt("Enter ProductID of the new product:");
            if (StockManager.findProductById(products, id).isPresent()) {
                System.out.println("A product with that ID already exists.");
                return;
            }
            String categoryLabel = readLine("Enter category of the new product (board game / accessory):");
            ProductCategory category = ProductCategory.fromLabel(categoryLabel);
            String detail = readLine(category == ProductCategory.BOARDGAME ? "Enter the board game type:" : "Enter the accessory type:");
            String name = readLine("Enter name of the new product:");
            double purchaseCost = readDouble("Enter purchase cost of the new product:");
            int stock = readInt("Enter stock quantity of the new product:");
            double price = readDouble("Enter sale price of the new product:");
            String compatibility = readLine("Enter compatibility value of the new product:");

            Product newProduct = category == ProductCategory.BOARDGAME
                    ? new BoardGame(id, detail, name, purchaseCost, stock, price, compatibility)
                    : new Accessory(id, detail, name, purchaseCost, stock, price, compatibility);

            if (StockManager.addProduct(newProduct, products)) {
                System.out.println("New product added successfully.");
            } else {
                System.out.println("Failed to add product. The ID may already be in use.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Unable to add product: " + ex.getMessage());
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = consoleInput.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = consoleInput.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.println(prompt);
        return consoleInput.nextLine().trim();
    }   
}
