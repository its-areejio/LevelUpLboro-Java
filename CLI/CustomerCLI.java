package CLI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.TreeMap;

public class CustomerCLI {
    private static final Path UserAccountsData = Paths.get("Data", "UserAccounts.txt");
    private static final Path StockData = Paths.get("Data", "Stock.txt");

    public static void run(String username, Scanner consoleInput) {

        System.out.println("CUSTOMER VIEW");
        Dictionary<Integer, Float> basket = new Hashtable<>();

        while (true) {
            printCustomerMenu();

            int selection = Integer.parseInt(consoleInput.nextLine().trim());

            switch (selection) {
                case 1 -> {
                    readProducts();
                }

                case 2 -> {
                    basket = addProduct(consoleInput, basket);
                }

                case 3 -> {
                    viewBasket(basket);
                }

                case 4 -> {
                    purchaseItems(consoleInput, basket, username);
                    basket = cancelBasket(basket);
                }

                case 5 -> {
                    basket = cancelBasket(basket);
                }

                case 6 -> {
                    lookupID(consoleInput);
                }

                case 7 -> {
                    lookupCompatibility(consoleInput);
                }

                case 0 -> {
                    return;
                }

                default -> {
                    System.out.println("Invalid input");
                    System.out.println();
                }
            }
        }
    }
    
    private static void printCustomerMenu() {
        System.out.println("PLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add product to shopping basket");
        System.out.println("3) View contents of shopping basket");
        System.out.println("4) Purchase items in the basket");
        System.out.println("5) Cancel shopping basket");
        System.out.println("6) Lookup with product ID");
        System.out.println("7) Search/filter based on compatilbility");
        System.out.println("0) Log out");
    }

    private static void readProducts() {
        File stockFile = StockData.toFile();
        HashMap<Float, Integer> productInfo = new HashMap<>();
        String[] products = new String[0];
        Integer count = 0;
        try (Scanner stockScanner = new Scanner(stockFile)) {
            while (stockScanner.hasNextLine()) {
                String line = stockScanner.nextLine();
                String splitLine[] = line.split(";");
                productInfo.put(Float.parseFloat(splitLine[4]), Integer.parseInt(splitLine[0]));
                products = java.util.Arrays.copyOf(products, count + 1);
                StringBuilder product = new StringBuilder();
                for (int i = 0; i < splitLine.length; i++) {
                    if (i != 6) {
                        if (product.length() > 0) product.append(";");
                        product.append(splitLine[i]);
                    }
                }
                products[count] = product.toString();
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        TreeMap<Float, Integer> stockData = new TreeMap<>();
        stockData.putAll(productInfo);
        for (Float productID : stockData.keySet()) {
            Integer currentProductID = stockData.get(productID);
            for (String product : products) {
                String splitLine[] = product.split(";");
                if (Integer.parseInt(splitLine[0]) == currentProductID) {
                    System.out.println(product);
                }
            }
        }
    }

    private static Dictionary<Integer, Float> addProduct(Scanner consoleInput, Dictionary<Integer, Float> basket) {
        System.out.println("Enter the ProductID of the product you want to add to your shopping basket:");
        String productID = consoleInput.nextLine().trim();
        File stockFile = StockData.toFile();
        try (Scanner stockScanner = new Scanner(stockFile)) {
            while (stockScanner.hasNextLine()) {
                String line = stockScanner.nextLine();
                String splitLine[] = line.split(";");
                if (splitLine[0].equals(productID) && Integer.parseInt(splitLine[5]) > 0) {
                    basket.put(Integer.parseInt(splitLine[0]), Float.parseFloat(splitLine[4]));
                    System.out.println("Product added to shopping basket.");
                    return basket;
                }
                else if (splitLine[0].equals(productID) && Integer.parseInt(splitLine[5]) == 0) {
                    System.out.println("Product is out of stock, try again later.");
                    return basket;
            }
            }
            System.out.println("Product not found.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return basket;
    }

    private static void viewBasket(Dictionary<Integer, Float> basket) {
        System.out.println(basket);
        return;
    }

    private static void purchaseItems(Scanner consoleInput, Dictionary<Integer, Float> basket, String username) {
        System.out.println("Would you like to pay by PayPal or card? (Enter 'PayPal' or 'Card')");
        String paymentMethod = consoleInput.nextLine().trim();

        String billingAddress = getBillingAddress(username, UserAccountsData.toFile());
        Float price = 0.0f;
        for (java.util.Enumeration<Integer> keys = basket.keys(); keys.hasMoreElements();) {
            Integer key = keys.nextElement();
            price += basket.get(key);
            File stockFile = StockData.toFile();
            StringBuilder newStockData = new StringBuilder();
            try (Scanner stockScanner = new Scanner(stockFile)) {
                while (stockScanner.hasNextLine()) {
                    String line = stockScanner.nextLine();
                    String splitLine[] = line.split(";");
                    if (Integer.parseInt(splitLine[0]) == key) {
                        int newStock = Integer.parseInt(splitLine[5]) - 1;
                        if (newStock > 0) {
                            splitLine[5] = String.valueOf(newStock);
                            line = String.join(";", splitLine);
                            newStockData.append(line).append(System.lineSeparator());
                        }
                        // else remove, don't append
                    } else {
                        System.out.println("This item is not in stock. Please try again later.");
                        newStockData.append(line).append(System.lineSeparator());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            try (FileWriter writer = new FileWriter(stockFile)) {
                writer.write(newStockData.toString());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } 

        if (paymentMethod.equalsIgnoreCase("PayPal")) {
            System.out.println("Enter your email address");
            String email = consoleInput.nextLine().trim();    
            System.out.println(String.valueOf(price) + " paid via PayPal using " + email + " on " + java.time.LocalDate.now() + ". Billing address:" + billingAddress);
            return;
        } else if (paymentMethod.equalsIgnoreCase("Card")) {
            System.out.println("Enter your card number:");
            String cardNumber = consoleInput.nextLine().trim();
            System.out.println("Enter your 3-digit security code:");
            String securityCode = consoleInput.nextLine().trim();   
            System.out.println(String.valueOf(price) + " paid via Credit Card " + cardNumber + " on " + java.time.LocalDate.now() + ". Billing address:" + billingAddress);
            return;
        } else {
            System.out.println("Invalid payment method.");
            return;
        }
    }

    private static String getBillingAddress(String username, File userFile) {
        try (Scanner fileScanner = new Scanner(userFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] userInfo = line.split(";");
                if (userInfo.length < 7) {
                    continue;
                }
                String fileUsername = userInfo[1];
                if (fileUsername.equals(username)) {
                    return userInfo[3] + " " + userInfo[4] + " " + userInfo[5];
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User accounts file not found.");
            return null;
        }
        System.out.println("User not found.");
        return null;
    }

    private static Dictionary<Integer, Float> cancelBasket( Dictionary<Integer, Float> basket) {
        System.out.println("Cancelling shopping basket...");
        basket = new Hashtable<>();
        return basket;
    }   

    private static void lookupID(Scanner consoleInput) {
        System.out.println("Enter the ProductID of the product you want to lookup:");
        String productID = consoleInput.nextLine().trim();
        File stockFile = StockData.toFile();
        try (Scanner stockScanner = new Scanner(stockFile)) {
            while (stockScanner.hasNextLine()) {
                String line = stockScanner.nextLine();
                String splitLine[] = line.split(";");
                if (splitLine[0].equals(productID)) {
                    System.out.println(line);
                    return;
                }
            }
            System.out.println("Product not found.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }   

    private static void lookupCompatibility(Scanner consoleInput) {
        System.out.println("What game's accessories are you looking for? (Enter the name of the game)");
        String game = consoleInput.nextLine().trim();
        File stockFile = StockData.toFile();
        try (Scanner stockScanner = new Scanner(stockFile)) {
            while (stockScanner.hasNextLine()) {
                String line = stockScanner.nextLine();
                String splitLine[] = line.split(";");
                if (splitLine[1].equalsIgnoreCase("accessory") && splitLine[6].equalsIgnoreCase(game)) {
                    System.out.println(line);
                    return;
                }
            }
            System.out.println("There are no products compatible with that game.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}