package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachine {
    private Map<String, InventoryItem> inventory;
    private List<InventoryItem> inventoryItemList = new ArrayList<>();
    private BigDecimal currentBalance = new BigDecimal("0.00");
    private static BigDecimal totalSales = new BigDecimal("0.00");
    private final int QUARTER_VALUE = 25;
    private final int DIME_VALUE = 10;
    private final int NICKEL_VALUE = 5;
    private int inventory_Size = 0;
    private int[] coinValues = {QUARTER_VALUE, DIME_VALUE, NICKEL_VALUE};
    private String[] coinTypes = {"quarter", "dime", "nickel"};
    private List<Integer> coinsDue;
    private Logger logger = new Logger();
    private SalesReport salesReport=new SalesReport();

    private String activeMenu = "main";

    public VendingMachine() {
        this.inventory = getInventoryMap();
    }

    //Inventory methods
    public Map<String, InventoryItem> getInventoryMap() {
        Map<String, InventoryItem> output = new HashMap<>();

        List<InventoryItem> inventoryItemList = getInventoryItemList();

        for (InventoryItem item : inventoryItemList) {
            output.put(item.getId(), item);
        }

        return output;
    }

    public List<InventoryItem> getInventoryItemList() {
        File inventoryFile = new File("vendingmachine.csv");

        try (Scanner fileLine = new Scanner(inventoryFile)) {
            while (fileLine.hasNext()) {
                String[] lineInput = fileLine.nextLine().split("\\|");
                inventoryItemList.add(new InventoryItem(lineInput[0], lineInput[1], lineInput[2], lineInput[3]));
            }

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found in the given path.");
        }
        return inventoryItemList;
    }

    public void displayInventory() {
        for (InventoryItem inventoryItem : inventoryItemList) {
            int quantityLeft = inventoryItem.getQuantityRemaining();
            String quantityToPrint = "";
            String id = inventoryItem.getId();

            if (quantityLeft > 0) {
                quantityToPrint += ", quantity available: " + quantityLeft;

            } else if (quantityLeft == 0) {
                quantityToPrint += " SOLD OUT!";
            }

            System.out.println(id + " | " + inventoryItem.getName() + " (" + inventoryItem.getEdibleCategory() + ")" + ", $" + inventoryItem.getPrice() + quantityToPrint + "\n");
        }
    }

    public void dispenseItem(InventoryItem inventoryItem, int quantity) {
        inventoryItem.setQuantityRemaining(inventoryItem.getQuantityRemaining() - quantity);
        inventoryItem.setNumbersSold(inventoryItem.getNumbersSold() + quantity);
        System.out.println("Item dispensed: (" + inventoryItem.getId() + ") " + inventoryItem.getName() + ", quantity: " + quantity);
        ConsoleServices.displayMessage(inventoryItem.getEdibleCategory());

    }

    public void selectProduct(String productID, int quantity) {
        InventoryItem itemSelected = inventory.get(productID);
        if (itemSelected.getQuantityRemaining() >= quantity) {
            BigDecimal totalSale = itemSelected.getPrice().multiply(BigDecimal.valueOf(quantity));
            if (currentBalance.compareTo(totalSale) != -1) {
                dispenseItem(itemSelected, quantity);
                totalSales = totalSales.add(itemSelected.getPrice().multiply(BigDecimal.valueOf(quantity)));
                currentBalance = currentBalance.subtract(totalSale);
                writeLogEntry(itemSelected.getName() + " " + itemSelected.getId() + " $" + totalSale + " $" + currentBalance);
            } else {
                System.out.println("You do not have enough balance at this point of time for requested item and quantity! \nPlease feed more money or make different order!");
            }
        } else {
            System.out.println("Quantity requested for this item is not available at this point of time!\nPlease change your order!");

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
            writeLogEntry("GIVE CHANGE: $" + currentBalance + " $0.00");
            setCurrentBalance(BigDecimal.ZERO.setScale(2));
        }
    }

    //Input validation
    public boolean isValidProductId(String productId) {
        return inventory.get(productId) != null;
    }

    //Logging and reporting methods
    public void writeLogEntry(String message) {
        logger.writeLogEntry(message);
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
    public void generateSalesReport(List<InventoryItem> updatedInventoryItems){
        String reportMessage="";
        for(InventoryItem item:updatedInventoryItems){
            if(reportMessage.contains(item.getName())){
                break;
            }
            reportMessage+=item.getName() + "|" + item.getNumbersSold() + "\n";
        }
        reportMessage+="\n**TOTAL SALES** $" + totalSales;
        salesReport.writeSalesReport(reportMessage);
    }
}
