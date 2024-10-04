package com.techelevator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class ConsoleServices {
    private static final String[] MAIN_MENU_OPTIONS = {"Display Vending Machine Items", "Purchase", "Exit"};
    private static final String[] PURCHASE_MENU_OPTIONS = {"Feed Money", "Select Product", "Finish Transaction"};
    private static Map<String,String> categoryMessage=new HashMap<>();

    private static void printMainMenu() {
        System.out.println("Welcome to the Vendo-Matic 800! Please select an option from the menu below:");
        int optionNumber = 1;
        for (String option : MAIN_MENU_OPTIONS) {
            System.out.println("\t(" + optionNumber + ") " + option);
            optionNumber++;
        }
    }

    private static void  printPurchaseMenu(BigDecimal currentBalance) {
        int optionNumber = 1;
        System.out.println("Current Money Provided: $" + currentBalance);
        for (String option : PURCHASE_MENU_OPTIONS) {
            System.out.println("\t(" + optionNumber + ") " + option);
            optionNumber++;
        }
    }

    public static void printMenu(String activeMenu, BigDecimal currentBalance) {
        if (activeMenu.equals("main")) {
            printMainMenu();
        } else if (activeMenu.equals("purchase")) {
            printPurchaseMenu(currentBalance);
        } else {
            System.out.println("Invalid active menu, returning to Main Menu.");
            printMenu("main", currentBalance);
        }
    }

    public static int inputMenuOption(String activeMenu, BigDecimal currentBalance) {
        Scanner userInput = new Scanner(System.in);
        boolean isInputValid = false;
        int menuOption = 1;

        while(!isInputValid) {
            ConsoleServices.printMenu(activeMenu, currentBalance);
            String menuOptionStr = userInput.nextLine();

            if (menuOptionStr.matches("\\d+")) {
                menuOption = Integer.parseInt(menuOptionStr);
            } else {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            if (activeMenu.equals("main")) {
                if (isValidMenuOption(1, 4, menuOption)) {
                    isInputValid = true;
                } else {
                    System.out.println("Invalid input. Please enter a number for one of the provided options.");
                }
            } else if (activeMenu.equals("purchase")) {
                if (isValidMenuOption(1, 3, menuOption)) {
                    isInputValid = true;
                } else {
                    System.out.println("Invalid input. Please enter a number for one of the provided options.");
                }
            }
        }

        return menuOption;

    }
    private static boolean isValidMenuOption(int min, int max, int choice) {
        return choice >= min && choice <= max;
    }

    public static void displayMessage(String category) {
        switch (category.toLowerCase()) {
            case "chip":
                System.out.println("Crunch,Crunch, Yum!");
                break;
            case "drink":
                System.out.println("Glug,Glug, Yum!");
                break;
            case "gum":
                System.out.println("Chew Chew, Yum!");
                break;
            case "candy":
                System.out.println("Munch,Munch, Yum!");
                break;
        }
    }
}
