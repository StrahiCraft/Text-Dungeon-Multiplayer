package client.entity.player.states;

import client.dungeon.rooms.ShopRoom;
import client.inventory.item.Item;
import client.entity.player.Player;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.util.Scanner;

public class PlayerShopping extends PlayerInInventory {
    public PlayerShopping() {
        super();
        possibleCommands.add("buy");
        possibleCommands.add("sell");

        printShop();
    }

    @Override
    public boolean checkInput(String inputText, Scanner input) {
        String[] splitText = inputText.split(" ");

        switch (splitText[0]){
            case "close" -> {
                Player.Instance.setCurrentState(Player.Instance.getPreviousState());
                TextRenderer.printText("Shop " + Color.getColor("red") + "closed" + Color.resetColor());
                return true;
            }
            case "sell" -> {
                sellItem(splitText);
                printInventory();
                printShop();
                return true;
            }
            case "buy" -> {
                buyItem(splitText);
                printInventory();
                printShop();
                return true;
            }
        }

        return super.checkInput(inputText, input);
    }

    private void sellItem(String[] instructions) {
        if(instructions.length != 2){
            TextRenderer.printText("To sell an item type in sell followed by the item index " +
                    "(number next to the item without the '.')");
            return;
        }

        try {
            Item itemToSell = Player.Instance.getInventory().getItem(Integer.parseInt(instructions[1]));

            if(itemToSell == null){
                TextRenderer.printText("Are you really trying to sell me an item that doesn't exist?");
                TextRenderer.skipLine();
                return;
            }
            if(itemToSell.getPrice() == -1){
                TextRenderer.printText("I don't accept that item for sale.");
                TextRenderer.skipLine();
                return;
            }

            Player.Instance.getInventory().removeItem(itemToSell);
            Player.Instance.addGold(itemToSell.getPrice());

            TextRenderer.printText("Thank you, here's some gold for your troubles. " +
                    itemToSell.getPrice() + Color.getColor("yellow") + " gold" + Color.resetColor() +
                    " has been added.");
            TextRenderer.skipLine();
        }
        catch (NumberFormatException ignored){
            TextRenderer.printText("To sell an item type in sell followed by the item index " +
                    "(number next to the item without the '.')");
        }
    }

    private void buyItem(String[] instructions) {
        if(instructions.length != 2){
            TextRenderer.printText("To buy an item type in buy followed by the item index " +
                    "(number next to the item without the '.')");
            return;
        }

        try {
            Item itemToBuy = ((ShopRoom)Player.Instance.getCurrentRoom()).
                    getItemForSale(Integer.parseInt(instructions[1]));

            if(itemToBuy == null){
                TextRenderer.printText("Sorry, we don't sell that here.");
                TextRenderer.skipLine();
                return;
            }
            if(itemToBuy.getPrice() > Player.Instance.getGold()){
                TextRenderer.printText("Sorry, I don't run a charity here.");
                TextRenderer.skipLine();
                return;
            }

            ((ShopRoom)Player.Instance.getCurrentRoom()).onItemBought(itemToBuy);
            Player.Instance.addGold(-itemToBuy.getPrice());

            TextRenderer.printText("Thank you for your kind purchase.");
            TextRenderer.skipLine();
        }
        catch (NumberFormatException ignored){
            TextRenderer.printText("To buy an item type in buy followed by the item index " +
                    "(number next to the item without the '.')");
        }
        printShop();
    }

    private void printShop(){
        TextRenderer.skipLine();

        TextRenderer.printText(Color.getColor("yellow") + "Gold: " +
                Color.resetColor() + Player.Instance.getGold());
        TextRenderer.printText(Player.Instance.getCurrentRoom().toString());

        TextRenderer.skipLine();
    }
}
