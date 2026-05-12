package client.inventory;

import client.inventory.item.Item;
import client.inventory.item.Rarity;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.util.ArrayList;

public class Inventory {
    private int slotCount;
    private ArrayList<Item> items;

    public Inventory() {
        slotCount = 1;
        items = new ArrayList<>();
    }

    public Inventory(int slotCount){
        this.slotCount = slotCount;
        items = new ArrayList<>();
    }

    public Inventory(int slotCount, ArrayList<Item> items) {
        this.slotCount = slotCount;
        this.items = items;
    }

    public void addItem(Item newItem){
        if(items.size() + 1 > slotCount){
            TextRenderer.printText(Color.getColor("red") + "Inventory is full" + Color.resetColor());
            return;
        }
        items.add(newItem);
    }

    public void removeItem(int index){
        if(index > items.size() || index < 1){
            TextRenderer.printText(Color.getColor("red") + "There is no item in that slot!" + Color.resetColor());
            return;
        }

        items.remove(index - 1);
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    public void removeItem(String itemName){
        removeItem(itemName, null);
    }

    public void removeItem(String itemName, Rarity rarity) {
        Item itemToRemove = getItem(itemName, rarity);

        if(itemToRemove == null){
            TextRenderer.printText(Color.getColor("red") + "There is no such item!" + Color.resetColor());
            return;
        }

        items.remove(itemToRemove);
    }

    public void useItem(int index){
        if(index > items.size() || index < 1){
            TextRenderer.printText(Color.getColor("red") + "There is no item in that slot!" + Color.resetColor());
            return;
        }

        items.get(index - 1).onUse();
    }

    public void useItem(String itemName){
        useItem(itemName, null);
    }

    public void useItem(String itemName, Rarity rarity) throws NullPointerException {
        Item itemToUse = getItem(itemName, rarity);

        if(itemToUse == null){
            TextRenderer.printText(Color.getColor("red") + "There is no such item!" + Color.resetColor());
            return;
        }

        itemToUse.onUse();
    }

    /**
     * Gets the item from the items list by its name and rarity.
     * @param itemName The name of the item.
     * @param rarity The rarity of the item.
     * @return An item from the inventory if it's found, NULL if not.
     */
    private Item getItem(String itemName, Rarity rarity){
        for(Item item : items){
            if(item.getName().equals(itemName) &&
                    (item.getRarity().equals(rarity) || rarity == null)){
                return item;
            }
        }

        return null;
    }

    public Item getItem(int index){
        if(index > items.size() || index < 1){
            TextRenderer.printText(Color.getColor("red") + "There is no item in that slot!" + Color.resetColor());
            return null;
        }

        return items.get(index - 1);
    }

    public boolean isFull(){
        return items.size() == slotCount;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder inventory = new StringBuilder();
        inventory.append("Inventory: ");

        if(items.size() < slotCount / 2){
            inventory.append(Color.getColor("bright green"));
        }
        else if(items.size() < slotCount){
            inventory.append(Color.getColor("bright yellow"));
        }
        else {
            inventory.append(Color.getColor("red"));
        }

        inventory.append(items.size()).append(Color.resetColor()).append("/").append(slotCount).append('\n');

        for(int i = 0; i < items.size(); i++){
            inventory.append(i + 1).append(". ").append(items.get(i).toString()).append('\n');
        }

        return inventory.toString();
    }
}
