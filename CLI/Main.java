package CLI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);

        System.out.println("WELCOME");
        
        while (true) {
            printWelcomeMenu();

            String line = scanner.nextLine().trim();

            int selection = Integer.parseInt(line.trim());

            switch (selection) {
            	case 0 -> {
                    System.out.println("Goodbye");
                    System.out.println("Closing program...");
                    System.out.println();
                    return;
                }
            		
            	case 1 -> {
                    if (signIn(scanner)) {
                        System.out.println("Sign In successful");
                    } else {
                        System.out.println("Sign In failed");
                    }
                }
            		
            	case 2 -> {
                    if (signUp(scanner)) {
                        System.out.println("Sign Up successful");
                    } else {
                        System.out.println("Sign Up failed");
                    }
                }
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

    private static String readLine(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }

    private static boolean signIn(Scanner scanner) {
        System.out.println("Sign In");
        System.out.println("Please enter your username:");
        String username = readLine(scanner);
        System.out.println("Please enter your password:");
        String password = readLine(scanner);

        File userFile = new File("UserAccounts.txt");
        if (!userFile.exists()) {
            return false;
        }

        try (Scanner fileScanner = new Scanner(userFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] userInfo = line.split(";");
                if (userInfo.length >= 3 && userInfo[1].equals(username) && userInfo[2].equals(password)) {
                    if (userInfo[3].equals("admin")) {
                        AdminCLI.run(scanner);
                    } else {
                        CustomerCLI.run(scanner);
                    }
                    return true;
                }
            }
        } 
        catch (FileNotFoundException e) {
            return false;
        }
        return false;
    }

    private static boolean signUp(Scanner scanner) {
        System.out.println("Sign Up");
        System.out.println("Please enter your desired username:");
        String username = readLine(scanner);
        System.out.println("Please enter your desired password:");
        String password = readLine(scanner);
        System.out.println("Please enter your house number:");
        String houseNumber = readLine(scanner);
        System.out.println("Please enter your postcode:");
        String postcode = readLine(scanner);
        System.out.println("Please enter your city:");
        String city = readLine(scanner);
        System.out.println("Please enter your role:");
        String role = readLine(scanner);

        int id = getIDFromFile() + 1;

        try (FileWriter userWriter = new FileWriter("UserAccounts.txt", true)) {
            userWriter.write(id + ";" + username + ";" + password + ";" + houseNumber + ";" + postcode + ";" + city + ";" + role + System.lineSeparator());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static Integer getIDFromFile() {
        File userAccounts = new File("UserAccounts.txt");
        if (!userAccounts.exists()) {
            return 0;
        }
        try (Scanner userReader = new Scanner(userAccounts)) {
            String lastLine = "";
            while (userReader.hasNextLine()) {
                lastLine = userReader.nextLine();
            }
            if (lastLine.trim().isEmpty()) {
                return 0;
            }
            String[] userInfo = lastLine.split(";");
            if (userInfo.length > 0) {
                return Integer.valueOf(userInfo[0]);
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
}
