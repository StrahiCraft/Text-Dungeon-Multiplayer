package client.inventory.item;

import client.graphics.Color;
import utility.file.FileInterpreter;

import java.io.Serializable;

public abstract class Item implements utility.file.FileWriter, FileInterpreter, Serializable {
    private String name;
    private Rarity rarity;
    private int price;

    public Item() {
        name = "Item";
        rarity = Rarity.COMMON;
        price = 0;
    }

    public Item(Item otherItem){
        name = otherItem.getName();
        price = otherItem.getPrice();
        rerollRarity();
    }

    public Item(String name, Rarity rarity, int price) {
        this.name = name;
        this.rarity = rarity;
        this.price = price;
    }

    public abstract void onUse();
    public abstract void handleRarity();
    public abstract String info();
    public abstract Item copy();

    public void rerollRarity(){
        double roll = Math.random() * 100;

        if(roll > 50){
            rarity = Rarity.COMMON;
            return;
        }
        if(roll > 25){
            rarity = Rarity.UNCOMMON;
            return;
        }
        if(roll > 12.5){
            rarity = Rarity.RARE;
            return;
        }
        if(roll > 6.25){
            rarity = Rarity.EPIC;
            return;
        }
        if(roll > 3.125){
            rarity = Rarity.LEGENDARY;
            return;
        }
        if(roll > 1.6125){
            rarity = Rarity.MYTHIC;
            return;
        }
        rerollRarity();
    }

    protected String getUnformattedName() {
        return name.replaceAll("\u001B\\[[;\\d]*[ -/]*[@-~]", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return Color.getRarityColor(rarity) + rarity.toString() + Color.resetColor()
                + " " + name + (price != -1? " " + price + Color.getColor("yellow") + "g" + Color.resetColor() : "");
    }
}
