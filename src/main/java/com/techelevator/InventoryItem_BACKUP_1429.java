package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class InventoryItem  {
    private String id;
    private String name;
    private BigDecimal price;
    private  String edibleCategory;
    private final int MAX_SLOT_CAPACITY=5;
    private final int MAXIMUM_SLOT_CAPACITY=5;
    private int numbersSold=0;
    private int quantityRemaining=MAXIMUM_SLOT_CAPACITY;

    public InventoryItem(){}

    public InventoryItem(String id, String name, String price, String edibleCategory){
        this.id=id;
        this.name=name;
        this.price= new BigDecimal(price);
        this.edibleCategory=edibleCategory;
        this.quantityRemaining-=this.numbersSold;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public String getEdibleCategory() {
        return edibleCategory;
    }

    public int getQuantityRemaining() {
        return this.quantityRemaining;
    }

<<<<<<< HEAD
    public void setQuantityRemaining(int quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
||||||| 8e9293f
    public boolean isInventoryItemAvailable(int numbersSelected){
        return MAXIMUM_SLOT_CAPACITY-this.numbersSold>=numbersSelected;
    }

    public InventoryItem dispenseItem(InventoryItem inventoryItem,int quantity){
        inventoryItem.quantityRemaining-=quantity;
        inventoryItem.numbersSold=quantity;
        inventoryItemsToDispense.put(inventoryItem.id,inventoryItem.getInventoryItemsToDisplay().get(inventoryItem.id)+quantity);
        System.out.println("item dispensed: "+inventoryItem.id+" name: "+inventoryItem.name+" quantity: "+quantity);
        inventoryItem.numbersSold+=quantity;
        inventoryItemsToDispense.put(id,"Name: "+inventoryItem.name+" Price: "+inventoryItem.price+" Category: "+inventoryItem.edibleCategory+" quantitySold: "+quantity);
        totalSales++;
        ConsoleServices.displayMessage(inventoryItem.edibleCategory);
        salesReport.add("Item Dispensed: "+inventoryItem.id+" name: "+inventoryItem.name+" quantity: "+quantity);
        logger.writeLogEntry("Item Dispensed: "+inventoryItem.id+" name: "+inventoryItem.name+" quantity: "+quantity);
        return inventoryItem;
=======
    public boolean isInventoryItemAvailable(int numbersSelected){
        return MAXIMUM_SLOT_CAPACITY-this.numbersSold>=numbersSelected;
    }

    public InventoryItem dispenseItem(InventoryItem inventoryItem,int quantity){
        inventoryItem.quantityRemaining-=quantity;
        inventoryItem.numbersSold=quantity;
        inventoryItemsToDispense.put(inventoryItem.id,inventoryItem.getInventoryItemsToDisplay().get(inventoryItem.id)+quantity);
        System.out.println("item dispensed: "+inventoryItem.id+" name: "+inventoryItem.name+" quantity: "+quantity);
        inventoryItem.numbersSold+=quantity;
        inventoryItemsToDispense.put(id,"Name: "+inventoryItem.name+" Price: "+inventoryItem.price+" Category: "+inventoryItem.edibleCategory+" quantitySold: "+quantity);
        totalSales+=quantity;
        ConsoleServices.displayMessage(inventoryItem.edibleCategory);
        salesReport.add("Item Dispensed: "+inventoryItem.id+" name: "+inventoryItem.name+" quantity: "+quantity);
        logger.writeLogEntry("Item Dispensed: "+inventoryItem.id+" name: "+inventoryItem.name+" quantity: "+quantity);
        return inventoryItem;
>>>>>>> bd8f3fb3dbbccdb3d15184fcbb72c0430b79dfef
    }

    public int getNumbersSold() {
        return numbersSold;
    }

<<<<<<< HEAD
    public void setNumbersSold(int numbersSold) {
        this.numbersSold = numbersSold;
||||||| 8e9293f
    void salesReport(){
        for(String item:salesReport){
            System.out.println(salesReport);
        }

=======
    void salesReport(){
        System.out.println("Sales Report");
        for(String item:salesReport){
            System.out.println(salesReport);
        }

>>>>>>> bd8f3fb3dbbccdb3d15184fcbb72c0430b79dfef
    }

    public boolean isInventoryItemAvailable(int numbersSelected){
        return MAXIMUM_SLOT_CAPACITY-this.numbersSold>=numbersSelected;
    }

}
