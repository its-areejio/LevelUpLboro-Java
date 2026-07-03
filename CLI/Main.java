package CLI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner consoleInput = new Scanner(System.in);
        System.out.println("WELCOME TO LEVELUP BOARD GAME SHOP");

        while (true) {
            printMainMenu();
            int selection = readInt(consoleInput, "Select an option:");

            switch (selection) {
                case 1 -> new AdminCLI(consoleInput).run();
                case 2 -> new CustomerCLI(consoleInput).run();
                case 0 -> {
                    System.out.println("Goodbye");
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("PLEASE SELECT ONE OF THE FOLLOWING:");
        System.out.println("1) Admin mode");
        System.out.println("2) Customer mode");
        System.out.println("0) Exit");
    }

    private static int readInt(Scanner consoleInput, String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = consoleInput.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}
