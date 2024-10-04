package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class InventoryItem  {
    private String id;
    private String name;
    private String price;
    private  String edibleCategory;
    private final int MAX_SLOT_CAPACITY=5;
    private List<InventoryItem> inventoryItemList=new ArrayList<>();
    private Map<String,String> inventoryItemsToDisplay =new HashMap<>();
    private Map<String,String> inventoryItemsToDispense =new HashMap<>();
    private List<String> salesReport=new ArrayList<>();
    private static List<String> allIdsList=new ArrayList<>();
    private final int MAXIMUM_SLOT_CAPACITY=5;
    private int numbersSold=0;
    private int quantityRemaining=MAXIMUM_SLOT_CAPACITY;
    private Logger logger=new Logger();
    private static int totalSales=0;
    public InventoryItem(){}

    public InventoryItem(String id, String name, String price, String edibleCategory){
        this.id=id;
        allIdsList.add(id);
        this.name=name;
        this.price=price;
        this.edibleCategory=edibleCategory;
        this.quantityRemaining-=this.numbersSold;
        inventoryItemsToDisplay.put(id,  name + "      " + price + "      " + edibleCategory );
    }

    public List<String> getSalesReport() {
        return salesReport;
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

    public int getQuantityRemaining() {
        return this.quantityRemaining;
    }

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
    }

    public static List<String> getAllIdsList() {
        return allIdsList;
    }

    void salesReport(){
        for(String item:salesReport){
            System.out.println(salesReport);
        }

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
    public boolean isValidId(String userInputId){
        for(String id:allIdsList){
            if(userInputId.trim().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

}
