package CLI;

import java.util.Scanner;

public class CustomerCLI {

    public static void run(Scanner consoleInput) {

        System.out.println("CUSTOMER VIEW");
        printCustomerMenu();
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
}