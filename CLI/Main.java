package CLI;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);

        System.out.println("WELCOME");
        
        while (true) {
            printWelcomeMenu();

            String line = scanner.nextLine().trim();

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
            scanner.close();
        }
    }

    private static void printWelcomeMenu() {
        System.out.println("PLEASE SELECT ONE OF THE FOLLOWING:");
        System.out.println("1) Sign In");
        System.out.println("2) Sign Up");
        System.out.println("0) Exit");
    }

    private static String readLine() {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }

    private static boolean signIn() {
        System.out.println("Sign In");
        System.out.println("Please enter your username:");
        String username = readLine(scanner);
        System.out.println("Please enter your password:");
        String password = readLine(scanner);

        new File("UserAccounts.txt").forEach(line -> {
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
        String username = readLine(scanner);
        System.out.println("Please enter your house number:");
        String houseNumber = readLine(scanner);    
        System.out.println("Please enter your postcode:");
        String postcode = readLine(scanner);
        System.out.println("Please enter your city:");
        String city = readLine(scanner);
        System.out.println("Please enter your role:");
        String role = readLine(scanner);

        Integer id = getIDFromFile("UserAccounts.txt") + 1;
        FileWriter userAccounts = new FileWriter("UserAccounts.txt");
        userAccounts.write(username + ";" + houseNumber + ";" + postcode + ";" + city + ";" + role);
    }

    private static Integer getIDFromFile(String filename) {
        File userAccounts = new File ("UserAccounts.txt");
        Scanner userReader = null;
		try {
			userReader = new Scanner(userAccounts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        String lastLine = "";
        while (userReader.hasNextLine()) {
            lastLine = userReader.nextLine();
        }
        String[] userInfo = lastLine.split(";");
        return Integer.parseInt(userInfo[0]);   
    }
}
