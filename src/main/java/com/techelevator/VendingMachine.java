package com.techelevator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine extends InventoryItem {
    Scanner userInput=new Scanner(System.in);
    private String message;
    private Map<String,String> inventoryItem=getInventoryItemsToDisplay();
    Logger log=new Logger();
    private BigDecimal customerFeedMoney=new BigDecimal("10.00");
    private BigDecimal currentBalance=new BigDecimal("10.00");
    //private BigDecimal totalSales;
    private List<InventoryItem> inventoryItemList= getInventoryItemList();

    public VendingMachine(){
    }
    public VendingMachine(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }
    public void updateBalance(BigDecimal price,int quantity){

    }
    public void displayInventory(){
        System.out.println("Welcome to Vendo Mat: ");
        System.out.println("ID:   Name:     Price:   Category:   QuantityLeft:");
        for(InventoryItem inventoryItem:inventoryItemList){
            Map<String,String> product=inventoryItem.getInventoryItemsToDisplay();
            Map<String,Integer> idVsQuantityLeft=inventoryItem.getItemIdWithQuantityRemaining();
            for(String id:product.keySet()) {
                if(idVsQuantityLeft.get(id)!=0) {
                    System.out.println(id + "   " + product.get(id) + "    " + idVsQuantityLeft.get(id) + "\n");
                }
                else{
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
            System.out.println(itemSelected.getQuantityRemaining());
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
    public BigDecimal feedMoney(BigDecimal currentBalance) {
        Scanner userInput = new Scanner(System.in);
        boolean isFeedingMoney = true;

        while(isFeedingMoney) {
            System.out.println("Please enter the number of dollars you would like to add as a whole number: ");
            String moneyAddedAsStr = userInput.nextLine();
            if (moneyAddedAsStr.matches("\\d+")) {
                BigDecimal moneyAdded = new BigDecimal(moneyAddedAsStr);
                currentBalance = currentBalance.add(moneyAdded);
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


        return currentBalance;

    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
