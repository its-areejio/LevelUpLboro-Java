package CLI;

import java.util.Scanner;

public class CustomerCLI {

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

    private static void readProducts() {
        // TODO Auto-generated method stub
        
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