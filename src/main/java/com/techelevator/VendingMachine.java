package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachine {
    Scanner userInput=new Scanner(System.in);
    private String message;
    private Map<String,InventoryItem> inventory;
    private List<InventoryItem> inventoryItemList = new ArrayList<>();
    private List<String> salesReport = new ArrayList<>();
    private BigDecimal customerFeedMoney= new BigDecimal("0.00");
    private BigDecimal currentBalance= new BigDecimal("0.00");
    private static BigDecimal totalSales= new BigDecimal("0.00");

    private final int QUARTER_VALUE = 25;
    private final int DIME_VALUE = 10;
    private final int NICKEL_VALUE = 5;
    private int[] coinValues = {QUARTER_VALUE, DIME_VALUE, NICKEL_VALUE};
    private String[] coinTypes = {"quarter", "dime", "nickel"};
    private List<Integer> coinsDue;
    private Logger logger=new Logger();

    private String activeMenu = "main";

    public VendingMachine() {
        this.inventory = getInventoryMap();
    }

    //Inventory methods
    public Map<String,InventoryItem> getInventoryMap(){
        Map<String, InventoryItem> output = new HashMap<>();

        List<InventoryItem> inventoryItemList = getInventoryItemList();

        for (InventoryItem item : inventoryItemList){
            output.put(item.getId(), item);
        }

        return output;
    }

    public List<InventoryItem> getInventoryItemList() {
        File inventoryFile = new File("vendingmachine.csv");

        try(Scanner fileLine=new Scanner(inventoryFile)){
            while(fileLine.hasNext()){
                String[] lineInput=fileLine.nextLine().split("\\|");
                inventoryItemList.add(new InventoryItem(lineInput[0],lineInput[1],lineInput[2],lineInput[3]));
            }
        }catch (FileNotFoundException fileNotFoundException){
            System.out.println("File not found in the given path.");
        }
        return inventoryItemList;
    }

    public void displayInventory(){

        for(InventoryItem inventoryItem:inventoryItemList){
            int quantityLeft =  inventoryItem.getQuantityRemaining();
            String quantityToPrint = "";
            String id = inventoryItem.getId();

                if(quantityLeft>0) {
                    quantityToPrint += ", quantity available: " + quantityLeft;

                }
                else if(quantityLeft==0){
                    quantityToPrint += " SOLD OUT!";
                }

            System.out.println(id + " | " + inventoryItem.getName() + " (" + inventoryItem.getEdibleCategory() + ")" + ", $" + inventoryItem.getPrice() + quantityToPrint + "\n");
            }
        }

    public void dispenseItem(InventoryItem inventoryItem, int quantity) {
        inventoryItem.setQuantityRemaining(inventoryItem.getQuantityRemaining() - quantity);
        inventoryItem.setNumbersSold(inventoryItem.getNumbersSold() + quantity);

        System.out.println("Item dispensed: " + inventoryItem.getId() + " | " + inventoryItem.getName() + ", quantity: " + quantity);

        ConsoleServices.displayMessage(inventoryItem.getEdibleCategory());
        logger.writeLogEntry("Item dispensed: " + inventoryItem.getId() + " | " + inventoryItem.getName() + ", quantity: " + quantity);
    }

    public void selectProduct(String productID,int quantity) {

        //VALIDATING INPUT
            InventoryItem itemSelected = inventory.get(productID);
            if (itemSelected.getQuantityRemaining() >= quantity) {
                BigDecimal price = itemSelected.getPrice().multiply(BigDecimal.valueOf(quantity));
                if (currentBalance.compareTo(price) != -1) {
                    dispenseItem(itemSelected, quantity);
                    BigDecimal totalSale = price.multiply(BigDecimal.valueOf(quantity));
                    totalSales = totalSales.add(itemSelected.getPrice().multiply(BigDecimal.valueOf(quantity)));
                    currentBalance = currentBalance.subtract(totalSale);
                    //logger.writeLogEntry("Items Selected:" + itemSelected.getName() + " ");
                } else {
                    System.out.println("You do not have enough balance at this point of time for requested item and quantity! \nPlease feed more money or make different order!");
                }
            } else {
                System.out.println("Quantity requested for this item is not Available at this point of time!\nPlease change your order!");

            }

    }
    public boolean isValidProductId(String productId){
        return getInventoryMap().get(productId)!=null ;
    }
    public boolean isValidQuantity(int quantity){
      if(quantity>0&&quantity<6){
          return true;
      }
      else {
          return false;
      }
    }

    //Money methods
    public void feedMoney() {
        Scanner userInput = new Scanner(System.in);
        boolean isFeedingMoney = true;

        while(isFeedingMoney) {
            //VALIDATING INPUT
            System.out.println("Please enter the number of dollars you would like to add as a whole number: ");
            String moneyAddedAsStr = userInput.nextLine();
            if (moneyAddedAsStr.matches("\\d+")) {
                BigDecimal moneyAdded = new BigDecimal(moneyAddedAsStr);
                this.setCurrentBalance(currentBalance.add(moneyAdded));
            } else {
                System.out.println("I'm sorry, the Vendo-Matic 800 only accepts whole dollars. Please enter a whole number.");
                continue;
            }
            //VALIDATING INPUT
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

    //Logging and reporting methods
    void salesReport(){
        for(InventoryItem item:inventoryItemList){
            System.out.println(">" + item.getName() + "|" + item.getNumbersSold());
        }

        System.out.println("\n**TOTAL SALES** $" + totalSales);
    }

    //Getters and setters
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
