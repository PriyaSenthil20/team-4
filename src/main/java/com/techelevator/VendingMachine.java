package com.techelevator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendingMachine extends InventoryItem implements Edible {
    private String message;
    private Map<String,String> inventoryItem=getInventoryItemsToDisplay();
    Logger log=new Logger();
    private BigDecimal customerFeedMoney;
    // private BigDecimal currentBalance;
    //private BigDecimal totalSales;
    private List<InventoryItem> inventoryItemSelected=getInventoryItemList();

    public VendingMachine(){}
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

}
