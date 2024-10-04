package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachine extends InventoryItem {
    Scanner userInput=new Scanner(System.in);
    private String message;
    private Map<String,String> inventoryItem = getInventoryItemsToDisplay();
    Logger log=new Logger();
    private BigDecimal customerFeedMoney=new BigDecimal("10.00");
    private BigDecimal currentBalance=new BigDecimal("10.00");
    //private BigDecimal totalSales;
    private List<InventoryItem> inventoryItemList;
    private List<InventoryItem> inventoryItemSelected;
    private final int QUARTER_VALUE = 25;
    private final int DIME_VALUE = 10;
    private final int NICKEL_VALUE = 5;
    private int[] coinValues = {QUARTER_VALUE, DIME_VALUE, NICKEL_VALUE};
    private String[] coinTypes = {"quarter", "dime", "nickel"};
    private List<Integer> coinsDue;

    private String activeMenu = "main";

    public VendingMachine() {
        currentBalance = new BigDecimal("0.00");
        inventoryItemList = getInventoryItemList();
    }
    public VendingMachine(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }
    public void updateBalance(BigDecimal price,int quantity){

    }


    //Inventory methods
    public void displayInventory(){

        for(InventoryItem inventoryItem:inventoryItemList){
            Map<String,String> product=inventoryItem.getInventoryItemsToDisplay();
            int quantityLeft=inventoryItem.getQuantityRemaining();
            for(String id:product.keySet()) {
                if(quantityLeft>0) {
                    System.out.println(id + "   " + product.get(id) + "    " + quantityLeft + "\n");
                }
                else if(quantityLeft==0){
                    System.out.println(id + "   " + product.get(id) + " Sold Out " + "\n");
                }
            }
        }
    }


    public void selectProduct() {
        displayInventory();
        System.out.println("Select 1 product ID: (example A1)");
        String productID = userInput.nextLine();
        if (isValidId(productID)) {
            System.out.println("Enter the quantity needed: (Numbers only)");
            int quantity = userInput.nextInt();
            InventoryItem itemSelected = getSelectedItem(productID);
            if (itemSelected.getQuantityRemaining() >= quantity) {
                BigDecimal price = new BigDecimal(itemSelected.getPrice());
                if (customerFeedMoney.compareTo(price) != -1) {
                    dispenseItem(itemSelected, quantity);
                    updateBalance(new BigDecimal(itemSelected.getPrice()), quantity);
                    log.writeLogEntry("Items Selected:" + itemSelected.getName() + " Quantity: " + quantity);
                } else {
                    System.out.println("You do not have enough balance at this point of time for requested item and quantity! \nPlease feed more money or make different order!");
                }
            } else {
                System.out.println("Quantity requested for this item is not Available at this point of time!\nPlease change your order!");

            }
        }
        else{
            System.out.println("Enter valid product number");
        }
    }


    //Money methods
    public void feedMoney() {
        Scanner userInput = new Scanner(System.in);
        boolean isFeedingMoney = true;

        while(isFeedingMoney) {
            System.out.println("Please enter the number of dollars you would like to add as a whole number: ");
            String moneyAddedAsStr = userInput.nextLine();
            if (moneyAddedAsStr.matches("\\d+")) {
                BigDecimal moneyAdded = new BigDecimal(moneyAddedAsStr);
                this.setCurrentBalance(currentBalance.add(moneyAdded));
            } else {
                System.out.println("Invalid input. Please enter a whole number.");
                continue;
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

    public void dispenseChange() {
        coinsDue = new ArrayList<>();

        if (currentBalance.compareTo(BigDecimal.valueOf(0)) > 0) {
            System.out.println("Change due: " + currentBalance);
            int currentBalanceInCents = currentBalance.movePointRight(2).intValue();
            for (int coinValue : coinValues) {
                coinsDue.add(currentBalanceInCents / coinValue);
                currentBalanceInCents = currentBalanceInCents % coinValue;
            }
            System.out.println("Your change has been dispensed as the following: ");
            for (int i = 0; i < coinsDue.size(); i++) {
                if (coinsDue.get(i) == 0) {
                    continue;
                }
                String coinTypeDue = coinsDue.get(i) > 1 ? coinTypes[i] + "s" : coinTypes[i];
                System.out.println("\t" + coinsDue.get(i) + " " + coinTypeDue);
            }

            if (currentBalanceInCents > 1) {
                System.out.println("\t" + currentBalanceInCents + " pennies");
            } else if (currentBalanceInCents == 1) {
                System.out.println("\t 1 penny");
            }

            System.out.println();
        }
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(String activeMenu) {
        this.activeMenu = activeMenu;
    }
}
