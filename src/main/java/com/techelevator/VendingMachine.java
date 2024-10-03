package com.techelevator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine extends InventoryItem implements Edible {
    private String message;
    private Map<String,String> inventoryItem=getInventoryItemsToDisplay();
    Logger log=new Logger();
    private BigDecimal customerFeedMoney;
    private BigDecimal currentBalance;
    //private BigDecimal totalSales;
    private List<InventoryItem> inventoryItemSelected;

    public VendingMachine(){
        this.inventoryItemSelected = getInventoryItemList();
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

        for(InventoryItem inventoryItem:inventoryItemSelected){
            Map<String,String> product=inventoryItem.getInventoryItemsToDisplay();
            for(String id:product.keySet()) {
                   System.out.println( id + " "+product.get(id));
            }
        }
    }
    public boolean selectProduct(List<InventoryItem> itemsSelected,int itemQuantity){
        for(InventoryItem inventoryItem:itemsSelected){
            if(inventoryItem.getNumberOfItemsRemaining()>=itemQuantity){
                BigDecimal price=new BigDecimal(Integer.parseInt(inventoryItem.getPrice()));
                if(customerFeedMoney.compareTo(price)!=-1){
                    dispenseItem(inventoryItem,itemQuantity);
                    updateBalance(new BigDecimal(inventoryItem.getPrice()),itemQuantity);
                    log.writeLogEntry("Items Selected:"+inventoryItem.getName()+" Quantity: "+itemQuantity);
                    return true;
                }
                else{

                }
            }
        }
        return false;
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
