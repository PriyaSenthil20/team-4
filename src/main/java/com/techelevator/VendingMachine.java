package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class VendingMachine {
    private Map<String, InventoryItem> inventory;
    private List<InventoryItem> inventoryItemList = new ArrayList<>();
    private BigDecimal currentBalance = new BigDecimal("0.00");
    private static BigDecimal totalSales = new BigDecimal("0.00");
    private final int QUARTER_VALUE = 25;
    private final int DIME_VALUE = 10;
    private final int NICKEL_VALUE = 5;
    private int[] coinValues = {QUARTER_VALUE, DIME_VALUE, NICKEL_VALUE};
    private String[] coinTypes = {"quarter", "dime", "nickel"};
    private Logger logger = new Logger();
    private SalesReport salesReport;

    private String activeMenu = "main";

    public VendingMachine() {
        this.inventory = createInventoryMap();
    }

    //Inventory methods
    public Map<String, InventoryItem> createInventoryMap() {
        Map<String, InventoryItem> output = new HashMap<>();

        List<InventoryItem> inventoryItemList = createInventoryItemList();

        for (InventoryItem item : inventoryItemList) {
            output.put(item.getId(), item);
        }

        return output;
    }

    public List<InventoryItem> createInventoryItemList() {
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

            if (quantityLeft > 0) {
                quantityToPrint += ", quantity available: " + quantityLeft;

            } else if (quantityLeft == 0) {
                quantityToPrint += " - SOLD OUT!";
            }

            System.out.println(inventoryItem.getId() + " | " + inventoryItem.getName() + " (" + inventoryItem.getEdibleCategory() + ")" + ", $" + inventoryItem.getPrice() + quantityToPrint + "\n");
        }
    }

    public void dispenseItem(InventoryItem inventoryItem, int quantity) {
        inventoryItem.setQuantityRemaining(inventoryItem.getQuantityRemaining() - quantity);
        inventoryItem.setNumbersSold(inventoryItem.getNumbersSold() + quantity);
        System.out.println("Item dispensed: (" + inventoryItem.getId() + ") " + inventoryItem.getName() + ", quantity: " + quantity);
        ConsoleServices.displayMessage(inventoryItem.getEdibleCategory());

    }

    public void selectProduct(InventoryItem itemSelected, int quantity) {
        if (itemSelected.getQuantityRemaining() >= quantity) {
            BigDecimal totalSale = itemSelected.getPrice().multiply(BigDecimal.valueOf(quantity));
            if (currentBalance.compareTo(totalSale) != -1) {
                dispenseItem(itemSelected, quantity);
                totalSales = totalSales.add(itemSelected.getPrice().multiply(BigDecimal.valueOf(quantity)));
                currentBalance = currentBalance.subtract(totalSale);
                writeLogEntry(itemSelected.getName() + " " + itemSelected.getId() + " $" + totalSale + " $" + currentBalance);
            } else {
                System.out.println("You do not have enough balance at this point of time for requested item and quantity! \nTo make this purchase you need to add $"
                        + totalSale.subtract(currentBalance).setScale(0, RoundingMode.CEILING)+ "." + "\nPlease feed more money or make different order!");
            }
        } else {
            System.out.println("Quantity requested for this item is not available at this point of time!\nPlease change your order!");

        }
    }

    public void dispenseChange() {
        if (currentBalance.compareTo(BigDecimal.valueOf(0)) > 0) {
            System.out.println("Change due: " + currentBalance);
            int currentBalanceInCents = currentBalance.movePointRight(2).intValue();

            List<Integer> coinsDue = new ArrayList<>();

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
        return inventory.get(productId.toUpperCase().trim()) != null;
    }

    //Logging and reporting methods
    public void writeLogEntry(String message) {
        logger.writeLogEntry(message);
    }

    public void generateSalesReport(){
        salesReport = new SalesReport();
        String reportMessage="";
        for(InventoryItem item: inventoryItemList){
            reportMessage += item.getName() + "|" + item.getNumbersSold() + "\n";
        }
        reportMessage+="\n**TOTAL SALES** $" + totalSales;
        salesReport.writeSalesReport(reportMessage);
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

    public Map<String, InventoryItem> getInventory() {
        return inventory;
    }
}
