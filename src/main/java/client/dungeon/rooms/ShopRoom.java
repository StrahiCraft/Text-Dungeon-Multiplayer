package client.dungeon.rooms;

import client.inventory.item.Item;
import client.inventory.item.ItemGenerator;
import client.entity.player.Player;
import client.entity.player.states.PlayerInShopRoom;
import client.entity.player.states.PlayerState;
import client.graphics.Color;
import client.graphics.TextRenderer;
import utility.Vector2Int;

import java.util.ArrayList;

public class ShopRoom extends EmptyRoom {
    private ArrayList<Item> itemsForSale;

    public ShopRoom(){
        setRoomSymbol('$');
        generateWares();
    }

    public ShopRoom(Vector2Int position) {
        super(position);

        setRoomSymbol('$');
        generateWares();
    }

    public ShopRoom(Vector2Int position, ArrayList<Item> itemsForSale) {
        super(position);
        this.itemsForSale = itemsForSale;

        setRoomSymbol('$');
    }

    @Override
    public PlayerState getRoomState() {
        return new PlayerInShopRoom();
    }

    @Override
    public void onRoomEntered() {
        TextRenderer.printText("You find yourself in a room with a " + Color.getColor("magenta") +
                "shop" + Color.resetColor() + "." + directionText());
        Player.Instance.increaseScore(2);
    }

    @Override
    public DungeonRoom copy() {
        return new ShopRoom();
    }

    private void generateWares(){
        itemsForSale = new ArrayList<>(0);
        for(int i = 0; i < 6; i++){
            Item itemForSale = ItemGenerator.generateItemInstance();

            if(itemForSale == null){
                continue;
            }

            if(itemForSale.getPrice() == -1){
                continue;
            }

            itemsForSale.add(itemForSale);
        }
    }

    public Item getItemForSale(int index){
        if(index < 1 || index > itemsForSale.size()){
            return null;
        }

        return itemsForSale.get(index - 1);
    }

    public void onItemBought(Item boughtItem){
        itemsForSale.remove(boughtItem);
        Player.Instance.getInventory().addItem(boughtItem);
    }

    public ArrayList<Item> getItemsForSale() {
        return itemsForSale;
    }

    public void setItemsForSale(ArrayList<Item> itemsForSale) {
        this.itemsForSale = itemsForSale;
    }

    @Override
    public String toString() {
        StringBuilder shop = new StringBuilder();
        int itemIndex = 1;

        shop.append("Welcome to my shop. Take a look at my wares:\n");

        for(Item item : itemsForSale){
            shop.append(itemIndex++).append(". ").append(item).append('\n');
        }

        return shop.toString();
    }
}
