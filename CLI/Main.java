package CLI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static final Path UserAccountsData = Paths.get("Data", "UserAccounts.txt");

    public static void main(String[] args) {
    	
        Scanner consoleInput = new Scanner(System.in);

        System.out.println("WELCOME");
        
        while (true) {
            printWelcomeMenu();

            String line = consoleInput.nextLine().trim();

            int selection = Integer.parseInt(line.trim());

            switch (selection) {
            	case 0 -> {
                    System.out.println("Goodbye");
                    System.out.println("Closing program...");
                    System.out.println();
                    return;
                }
            		
            	case 1 -> {
                    String username = signIn(consoleInput);
                    if (username != null) {
                        System.out.println("Sign In successful");
                    } else {
                        System.out.println("Sign In failed");
                    }
                }
            		
            	case 2 -> {
                    if (signUp(consoleInput)) {
                        System.out.println("Sign Up successful");
                    } else {
                        System.out.println("Sign Up failed");
                    }
                }
            }
        }
    }

    private static void printWelcomeMenu() {
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

    private static String signIn(Scanner consoleInput) {
        System.out.println("Sign In");
        System.out.println("Please enter your username:");
        String username = readLine(consoleInput);
        System.out.println("Please enter your password:");
        String password = readLine(consoleInput);

        File userFile = UserAccountsData.toFile();

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
                String filePassword = userInfo[2];
                String fileRole = userInfo[6];
                if (fileUsername.equals(username) && filePassword.equals(password)) {
                    if (fileRole.equalsIgnoreCase("admin")) {
                        AdminCLI.run(consoleInput);
                    } else {
                        CustomerCLI.run(username, consoleInput);
                    }
                    return userInfo[1];
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User accounts file not found.");
            return null;
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    private static boolean signUp(Scanner consoleInput) {
        System.out.println("Sign Up");
        System.out.println("Please enter your desired username:");
        String username = readLine(consoleInput);
        System.out.println("Please enter your desired password:");
        String password = readLine(consoleInput);
        System.out.println("Please enter your house number:");
        String houseNumber = readLine(consoleInput);
        System.out.println("Please enter your postcode:");
        String postcode = readLine(consoleInput);
        System.out.println("Please enter your city:");
        String city = readLine(consoleInput);
        System.out.println("Please enter your role:");
        String role = readLine(consoleInput);

        int id = getIDFromFile() + 1;
        String newUser = id + ";" + username + ";" + password + ";" + houseNumber + ";" + postcode + ";" + city + ";" + role + System.lineSeparator();

        try (FileWriter userWriter = new FileWriter("../Data/UserAccounts.txt", true)) {
            userWriter.write(newUser);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static Integer getIDFromFile() {
        File userAccounts = new File("../Data/UserAccounts.txt");
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
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
