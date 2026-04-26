package CLI;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {

        Scanner consoleInput = new Scanner(System.in);

        System.out.println("WELCOME");
        
        while (true) {
            printWelcomeMenu();

            String line = consoleInput.nextLine().trim();

            int selection = Integer.parseInt(line.trim());

            switch (selection) {
            	case 0:
            		System.out.println("Goodbye");
            		System.out.println("Closing program...");
            		System.out.println();
            		return;
            		
            	case 1:
                    if (signIn()) {
                        System.out.println("Sign In successful");
                    } else {
                        System.out.println("Sign In failed");
                    }
                    break;
            		
            	case 2:
            		break;
            }
        }

        scanner.close();
    }

    private static void printWelcomeMenu() {

        // Replace placeholders with enumeration of existing users (1..n)
        System.out.println("PLEASE SELECT ONE OF THE FOLLOWING:");
        System.out.println("1) Sign In");
        System.out.println("2) Sign Up");
        System.out.println("0) Exit");
    }

    private static String readLine(Scanner consoleInput) {
        if (!consoleInput.hasNextLine()) {
            return null;
        }
        return consoleInput.nextLine();
    }

    private static boolean signIn() {
        Systen.out.println("Sign In");
        System.out.println("Please enter your username:");
        String username = readLine(consoleInput);
        System.out.println("Please enter your password:");
        String password = readLine(consoleInput);
        File userAccounts = openFile("UserAccounts.txt");

        openFile("UserAccounts.txt").forEach(line -> {
            String[] userInfo = line.split(";");
            if (userInfo[0].equals(username) && userInfo[1].equals(password)) {
                if (userInfo[2].equals("admin")) {
                    AdminCLI.run(consoleInput);
                } else {
                    CustomerCLI.run(consoleInput);
                }
            }
        });
    }

    private static boolean signUp() {
        System.out.println("Sign Up");
        System.out.println("Please enter your desired username:");
        String username = readLine(consoleInput);
        System.out.println("Please enter your house number:");
        String houseNumber = readLine(consoleInput);    
        System.out.println("Please enter your postcode:");
        String postcode = readLine(consoleInput);
        System.out.println("Please enter your city:");
        String city = readLine(consoleInput);
        System.out.println("Please enter your role:");
        String role = readLine(consoleInput);

        Integer currentUserID = getIDFromFile("UserAccounts.txt") + 1;

        File userAccounts = openFile("UserAccounts.txt");
        id = getIDFromFile("UserAccounts.txt") + 1;
        userAccounts.write(username + ";" + houseNumber + ";" + postcode + ";" + city + ";" + role);
    }

    private static Integer getIDFromFile(String filename) {
        //READ LAST LINE FROM FILE AND EXTRACT ID
        File userAccounts = openFile("UserAccounts.txt");
        String lastLine = "";
        while (userAccounts.hasNextLine()) {
            lastLine = userAccounts.nextLine();
        }
        String[] userInfo = lastLine.split(";");
        return Integer.parseInt(userInfo[0]);   
    }
}