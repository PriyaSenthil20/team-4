package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class InventoryItem {
    private String id;
    private String name;
    private String price;
    private  String edibleCategory;
    private final int MAX_SLOT_CAPACITY=5;
    private List<InventoryItem> inventoryItemList=new ArrayList<>();
    private Map<String,String> inventoryItemsToDisplay =new HashMap<>();
    private Map<String,String> inventoryItemsToDispense =new HashMap<>();
    private final int MAXIMUM_SLOT_CAPACITY=5;
    private int numbersSelected=0;
    private int numbersSold=0;
    private int numberOfItemsRemaining=MAXIMUM_SLOT_CAPACITY-numbersSold;

    public InventoryItem(){}


    public InventoryItem(String id, String name, String price,String edibleCategory){
        this.id=id;
        this.name=name;
        this.price=price;
        this.edibleCategory=edibleCategory;
        if(this.numberOfItemsRemaining==0){
            inventoryItemsToDisplay.put(id, "Name: " + name + " Price: " + price + " Category: " + edibleCategory + " Sold Out");
        }else {
            inventoryItemsToDisplay.put(id, "Name: " + name + " Price: " + price + " Category: " + edibleCategory + " Quantity Available: " + numberOfItemsRemaining);
        }
    }

    public Map<String,String> getInventoryItemsToDisplay(){
        return inventoryItemsToDisplay;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public String getEdibleCategory() {
        return edibleCategory;
    }
    public Map<String,String> getInventoryItemsToDispense(){
        return inventoryItemsToDispense;
    }

    public int getNumberOfItemsRemaining() {
        return numberOfItemsRemaining;
    }

    public boolean isInventoryItemAvailable(int numbersSelected){
        return MAXIMUM_SLOT_CAPACITY-this.numbersSold>=numbersSelected;
    }
    public void updateInventoryItem(InventoryItem inventoryItemSelected,int numbersSold){
        inventoryItemSelected.numbersSold+=numbersSold;
        inventoryItemsToDispense.put(id,"Name: "+name+" Price: "+price+" Category: "+edibleCategory+" numberOfItemsSold: "+numbersSold);
    }
    public InventoryItem dispenseItem(InventoryItem inventoryItem,int quantity){
        inventoryItem.numberOfItemsRemaining-=quantity;
        inventoryItem.numbersSold=quantity;
        inventoryItemsToDispense.put(inventoryItem.id,inventoryItem.getInventoryItemsToDisplay().get(inventoryItem.id)+quantity);
        System.out.println("item dispensed: "+inventoryItem.id);
        return inventoryItem;
    }
    public List<InventoryItem> getInventoryItemList(){
        File inventoryFile=new File("vendingmachine.csv");

        try(Scanner fileLine=new Scanner(inventoryFile)){
            while(fileLine.hasNext()){
                String[] lineInput=fileLine.nextLine().split("\\|");
                inventoryItemList.add(new InventoryItem(lineInput[0],lineInput[1],lineInput[2],lineInput[3]));
            }
        }catch (FileNotFoundException fileNotFoundException){
            System.out.println("File not found in the given path");
        }
        return inventoryItemList;
    }
    InventoryItem getSelectedItem(String id) {
        for (InventoryItem output : inventoryItemList) {
            if (output.getId().equalsIgnoreCase(id)) {
                return output;
            }
        }
        return null;
    }
    public List<String> getAllIds(){
        List<String> allIds=new ArrayList<>();
        for(String id:inventoryItemsToDisplay.keySet()){
            allIds.add(id);
        }
        return allIds;
    }
}
