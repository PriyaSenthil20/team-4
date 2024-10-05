package com.techelevator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleServices {
    private static final String[] MAIN_MENU_OPTIONS = {"Display Vending Machine Items", "Purchase", "Exit"};
    private static final String[] PURCHASE_MENU_OPTIONS = {"Feed Money", "Select Product", "Finish Transaction"};

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

    public static void printMenu(VendingMachine vm) {
        String activeMenu = vm.getActiveMenu();
        BigDecimal currentBalance = vm.getCurrentBalance();
        if (activeMenu.equals("main")) {
            printMainMenu();
        } else if (activeMenu.equals("purchase")) {
            printPurchaseMenu(currentBalance);
        } else {
            System.out.println("Invalid active menu, returning to Main Menu.");
            vm.setActiveMenu("main");
            printMenu(vm);
        }
    }

    public static int inputMenuOption(VendingMachine vm) {
        Scanner userInput = new Scanner(System.in);
        boolean isInputValid = false;
        String activeMenu = vm.getActiveMenu();
        int menuOption = 1;

        while(!isInputValid) {
            ConsoleServices.printMenu(vm);
            String menuOptionAsString = userInput.nextLine();

            if (isValidWholeNumber(menuOptionAsString)) {
                menuOption = Integer.parseInt(menuOptionAsString);
                switch (activeMenu) {
                    case "main":
                        if (isValidMenuOption(1, 4, menuOption)) {
                            isInputValid = true;
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a number corresponding to one of the provided options.");
                            break;
                        }
                    case "purchase":
                        if (isValidMenuOption(1, 3, menuOption)) {
                            isInputValid = true;
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a number corresponding to one of the provided options.");
                            break;
                        }
                }
            } else {
                System.out.println("Invalid input. Please enter a number corresponding to one of the provided options.");
            }
        }

        return menuOption;

    }

    public static void inputMoney(VendingMachine vm) {
        Scanner userInput = new Scanner(System.in);

        boolean isFeedingMoney = true;
        boolean isValidInput;

        while(isFeedingMoney) {
            isValidInput = false;

            while(!isValidInput) {
                System.out.println("Please enter the number of dollars you would like to add as a whole number:");
                String dollarAmountAsString = userInput.nextLine();
                if (isValidNumber(dollarAmountAsString)) {
                    if (isValidWholeNumber(dollarAmountAsString)) {
                        BigDecimal dollarAmount = new BigDecimal(dollarAmountAsString);
                        dollarAmount = dollarAmount.setScale(2);
                        vm.setCurrentBalance(vm.getCurrentBalance().add(dollarAmount));
                        vm.writeLogEntry("FEED MONEY: $" + dollarAmount + " $" + vm.getCurrentBalance());
                        isValidInput = true;
                    } else {
                        System.out.println("I'm sorry, the Vendo-Matic 800 only accepts whole dollars. Please enter a whole number.");
                    }
                }
            }

            System.out.println("Thank you! Please press (1) to continue feeding money or (2) to return to the Purchase menu.");
            String continueOption = userInput.nextLine();
            while(isFeedingMoney) {
                if (continueOption.equals("1")) {
                    break;
                } else if (continueOption.equals("2")) {
                    isFeedingMoney = false;
                } else {
                    System.out.println("Invalid input. Please press (1) to continue feeding money or (2) to return to the Purchase menu.");
                    continueOption = userInput.nextLine();
                }
            }
        }
    }

    public static void inputProductSelection(VendingMachine vm){

        Scanner userInput=new Scanner(System.in);
        System.out.println("Select a Product ID: (ex: A1)");
        String productID = userInput.nextLine().toUpperCase().trim();

        while(!vm.isValidProductId(productID)){
            System.out.println("Invalid input. Please Enter a valid Product ID:");
            productID=userInput.nextLine().toUpperCase().trim();
        }

        System.out.println("How many you would like? (Please enter as a whole number)");
        String quantityInput=userInput.nextLine().trim();
        while (!isValidWholeNumber(quantityInput) || !isValidQuantity(Integer.parseInt(quantityInput))) {
            System.out.println("Invalid input. Please enter valid quantity: (Whole number)");
            quantityInput=userInput.nextLine().trim();
        }

        int quantity=Integer.parseInt(quantityInput);
        vm.selectProduct(productID,quantity);
    }

    public static void displayMessage(String category) {
        switch (category.toLowerCase()) {
            case "chip":
                System.out.println("Crunch, Crunch, Yum!");
                break;
            case "drink":
                System.out.println("Glug, Glug, Yum!");
                break;
            case "gum":
                System.out.println("Chew, Chew, Yum!");
                break;
            case "candy":
                System.out.println("Munch, Munch, Yum!");
                break;
        }
    }

    private static boolean isValidNumber(String input) {
        boolean isValidNumber = false;
        try {
            BigDecimal decimal = new BigDecimal(input);
            isValidNumber = true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number!");
        }

        return isValidNumber;
    }

    /* Start citation
     * Code adapted from: Google Gemini
     * Date: 10/4/24
     * Conversation link: https://g.co/gemini/share/23edacd93e55
     */
    private static boolean isValidWholeNumber(String number) {
        return number.matches("\\d+(\\.00)?");
    }
    /*
     * End citation
     */

    private static boolean isValidMenuOption(int min, int max, int choice) {
        return choice >= min && choice <= max;
    }

    private static boolean isValidQuantity(int quantity){
        return quantity > 0 && quantity < 6;
    }

}
