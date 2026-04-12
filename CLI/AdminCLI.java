package CLI;

import java.util.Scanner;

public class AdminCLI {
	public final static String NOT_IMPLEMENTED = "Not implemented";
	public final static String INVALID = "Invalid input";

    public static void run(Scanner consoleInput) {
    	System.out.println("ADMIN VIEW");
        

        
        while (true) {
        	printAdminMenu();
        	
        	int selection = Integer.parseInt(consoleInput.nextLine().trim());
        	
        	switch (selection) {
        		case 1:
        			System.out.println(NOT_IMPLEMENTED);
        			System.out.println();
        			break;
        		
        		case 2:
        			//The order of the input should follow the order appearing in the Stock.txt file
        			//read data one entry at a time.
        			for (int i=1;i<9;i++) {
        				System.out.println("input parameter" + i);
        				consoleInput.nextLine();
        				
        			}
        			System.out.println(NOT_IMPLEMENTED);
        			System.out.println();
        			break;
        			
        		case 0:
        			return;
        			
        		default:
        			System.out.println(INVALID);
        			System.out.println();
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