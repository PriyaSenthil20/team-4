package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class InventoryItem {
    private String id;
    private String name;
    private BigDecimal price;
    private String edibleCategory;
    private final int MAX_SLOT_CAPACITY = 5;
    private final int MAXIMUM_SLOT_CAPACITY = 5;
    private int numbersSold = 0;
    private int quantityRemaining = MAXIMUM_SLOT_CAPACITY;

    public InventoryItem() {
    }

    public InventoryItem(String id, String name, String price, String edibleCategory) {
        this.id = id;
        this.name = name;
        this.price = new BigDecimal(price);
        this.edibleCategory = edibleCategory;
        this.quantityRemaining -= this.numbersSold;
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

    public void setQuantityRemaining(int quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    public int getNumbersSold() {
        return numbersSold;
    }

    public void setNumbersSold(int numbersSold) {
        this.numbersSold = numbersSold;
    }

}