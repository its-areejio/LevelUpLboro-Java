package CLI;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class CustomerCLI {
    private static final Path StockData = Paths.get("Data", "Stock.txt");

    public static void run(Scanner consoleInput) {

        System.out.println("CUSTOMER VIEW");
        printCustomerMenu();

        while (true) {
            printCustomerMenu();

            int selection = Integer.parseInt(consoleInput.nextLine().trim());

            switch (selection) {
                case 1 -> {
                    readProducts();
                }

                case 2 -> {
                    addProduct(consoleInput);
                }

                case 3 -> {
                    viewBasket();
                }

                case 4 -> {
                    purchaseItems();
                }

                case 5 -> {
                    cancelBasket();
                }

                case 6 -> {
                    lookupProduct();
                }

                case 7 -> {
                    searchFilter();
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

    @SuppressWarnings("UnnecessaryTemporaryOnConversionFromString")
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
                products[count] = line;
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Stock data file not found.");
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

    private static void addProduct(Scanner consoleInput) {
        // TODO Auto-generated method stub
        
    }

    private static void viewBasket() {
        // TODO Auto-generated method stub
        
    }

    private static void purchaseItems() {
        // TODO Auto-generated method stub
        
    }

    private static void cancelBasket() {
        // TODO Auto-generated method stub
        
    }   

    private static void lookupProduct() {
        // TODO Auto-generated method stub
        
    }   

    private static void searchFilter() {
        // TODO Auto-generated method stub
        
    }
}