package CLI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class AdminCLI {
    private static final Path StockData = Paths.get("Data", "Stock.txt");

    public static void run(Scanner consoleInput) {
    	System.out.println("ADMIN VIEW");
        
        while (true) {
        	printAdminMenu();
        	
        	int selection = Integer.parseInt(consoleInput.nextLine().trim());
        	
        	switch (selection) {
        		case 1 -> {
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
        		
        		case 2 -> {
                    System.out.println("Enter ProductID of the new product:");
                    Integer newProductID = Integer.parseInt(consoleInput.nextLine().trim());
                    System.out.println("Enter Categoryof the new product:");
                    String newProductCategory = consoleInput.nextLine().trim();
                    System.out.println("Enter Name of the new product:");
                    String newProductName = consoleInput.nextLine().trim();
                    System.out.println("Enter Description of the new product:");
                    String newProductDescription = consoleInput.nextLine().trim();
                    System.out.println("Enter Compatibility of the new product:");
                    String newProductCompatibility = consoleInput.nextLine().trim();
                    System.out.println("Enter Price of the new product:");
                    Float newProductPrice = Float.parseFloat(consoleInput.nextLine().trim());
                    System.out.println("Enter Stock of the new product:");
                    Integer newProductStock = Integer.parseInt(consoleInput.nextLine().trim());

                    String newProduct = newProductID + ";" + newProductCategory + ";" + newProductName + ";" + newProductDescription + ";" + newProductCompatibility + ";" + newProductPrice + ";" + newProductStock+ System.lineSeparator(); 
                    try (FileWriter stockFileWriter = new FileWriter(StockData.toFile(), true)) {
                        stockFileWriter.write(newProduct);
                        System.out.println("New product added successfully.");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }  
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
    
    
    private static void printAdminMenu() {

        System.out.println("PLEASE SELECT ACTION BY INPUTTING THE CORRESPONDING NUMBER (or 0 for logout)");
        System.out.println("1) View all products");
        System.out.println("2) Add new product");
        System.out.println("0) Log out");
    }
    
    
    
}