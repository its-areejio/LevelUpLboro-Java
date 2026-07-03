package CLI;

import Classes.Address;
import Classes.CardPayment;
import Classes.PayPalPayment;
import Classes.PaymentMethod;
import Classes.Product;
import Classes.Receipt;
import Classes.ShoppingBasket;
import Classes.StockManager;
import java.util.List;
import java.util.Scanner;

public class CustomerCLI {
    private final List<Product> products;
    private final ShoppingBasket basket;
    private final Scanner consoleInput;

    public CustomerCLI(Scanner consoleInput) {
        this.consoleInput = consoleInput;
        this.products = StockManager.loadProducts();
        this.basket = new ShoppingBasket();
    }

    public void run() {
        System.out.println("CUSTOMER VIEW");
        while (true) {
            printCustomerMenu();
            int selection = readInt("Select an option:");
            switch (selection) {
                case 1 -> viewProducts();
                case 2 -> addProductToBasket();
                case 3 -> viewBasket();
                case 4 -> purchaseItems();
                case 5 -> cancelBasket();
                case 6 -> lookupProductById();
                case 7 -> lookupCompatibility();
                case 0 -> {
                    System.out.println("Returning to main menu.");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void printCustomerMenu() {
        System.out.println("PLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add product to shopping basket");
        System.out.println("3) View contents of shopping basket");
        System.out.println("4) Purchase items in the basket");
        System.out.println("5) Cancel shopping basket");
        System.out.println("6) Lookup product by ID");
        System.out.println("7) Search/filter based on compatibility");
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

    private void addProductToBasket() {
        int id = readInt("Enter the ProductID of the product you want to add to your shopping basket:");
        Product product = findProduct(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        int quantity = readInt("Enter the quantity you want to add:");
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }
        if (product.getQuantityInStock() < quantity) {
            System.out.println("There are only " + product.getQuantityInStock() + " units available.");
            return;
        }
        for (int i = 0; i < quantity; i++) {
            basket.addItem(product);
        }
        System.out.println(quantity + " unit(s) added to your shopping basket.");
    }

    private void viewBasket() {
        if (basket.isEmpty()) {
            System.out.println("Your shopping basket is empty.");
            return;
        }
        basket.getBasketSummary().forEach(System.out::println);
        System.out.printf("Total: £%.2f%n", basket.calculateTotal());
    }

    private void purchaseItems() {
        if (basket.isEmpty()) {
            System.out.println("Your basket is empty. Add some products before purchasing.");
            return;
        }
        viewBasket();
        String houseNumber = readLine("Enter your house number:");
        String postcode = readLine("Enter your postcode:");
        String city = readLine("Enter your city:");
        Address address;
        try {
            address = new Address(houseNumber, postcode, city);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid address: " + ex.getMessage());
            return;
        }
        String method = readLine("Would you like to pay by PayPal or Card? (Enter PayPal or Card):");
        PaymentMethod paymentMethod;
        if (method.equalsIgnoreCase("PayPal")) {
            String email = readLine("Enter your PayPal email address:");
            try {
                paymentMethod = new PayPalPayment(email);
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid PayPal payment details: " + ex.getMessage());
                return;
            }
        } else if (method.equalsIgnoreCase("Card")) {
            String cardNumber = readLine("Enter your card number:");
            String securityCode = readLine("Enter your 3- or 4-digit security code:");
            try {
                paymentMethod = new CardPayment(cardNumber, securityCode);
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid card payment details: " + ex.getMessage());
                return;
            }
        } else {
            System.out.println("Invalid payment method.");
            return;
        }

        for (var entry : basket.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getQuantityInStock() < quantity) {
                System.out.println("Unable to complete purchase because " + product.getProductName()
                        + " only has " + product.getQuantityInStock() + " units available.");
                return;
            }
        }

        basket.getItems().forEach((product, count) -> product.setQuantityInStock(product.getQuantityInStock() - count));
        StockManager.saveProducts(products);

        Receipt receipt = paymentMethod.processPayment(basket.calculateTotal(), address);
        System.out.println(receipt);
        basket.clear();
    }

    private void cancelBasket() {
        if (basket.isEmpty()) {
            System.out.println("Your shopping basket is already empty.");
            return;
        }
        basket.clear();
        System.out.println("Shopping basket cancelled.");
    }

    private void lookupProductById() {
        int id = readInt("Enter the ProductID of the product you want to lookup:");
        Product product = findProduct(id);
        if (product == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println(product);
        }
    }

    private void lookupCompatibility() {
        String search = readLine("Enter a compatibility keyword (game name, universal, etc.):");
        List<Product> matches = products.stream()
                .filter(product -> product.getCompatibility().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (matches.isEmpty()) {
            System.out.println("No products found matching that compatibility.");
            return;
        }
        matches.forEach(System.out::println);
    }

    private Product findProduct(int id) {
        return products.stream()
                .filter(product -> product.getProductId() == id)
                .findFirst()
                .orElse(null);
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

    private String readLine(String prompt) {
        System.out.println(prompt);
        return consoleInput.nextLine().trim();
    }
}