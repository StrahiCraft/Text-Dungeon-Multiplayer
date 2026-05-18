package client.entity.player.states;

import client.game.Game;
import client.inventory.item.equipment.EquipmentSlot;
import client.entity.player.Player;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.util.Scanner;

public class PlayerInInventory extends PlayerState {
    public PlayerInInventory() {
        super();
        possibleCommands.add(Color.getColor("yellow") + "drop" + Color.resetColor() +
                " the index of the item.");
        possibleCommands.add(Color.getColor("green") + "use" + Color.resetColor() +
                " the index of the item.");
        possibleCommands.add(Color.getColor("gray") + "unequip" + Color.resetColor() +
                " the index of the item.");
        possibleCommands.add(Color.getColor("magenta") + "info" + Color.resetColor() +
                " the index of the item.");
        possibleCommands.add(Color.getColor("red") + "close" + Color.resetColor());

        printInventory();
    }

    @Override
    public boolean checkInput(String inputText, Scanner input) {
        String[] splitText = inputText.split(" ");

        switch (splitText[0]){
            case "close" -> {
                Game.getPlayer().setCurrentState(Game.getPlayer().getPreviousState());
                TextRenderer.printText("Inventory " + Color.getColor("red") + "closed" + Color.resetColor());
                return true;
            }
            case "use" -> {
                useItem(splitText);
                printInventory();
                return true;
            }
            case "drop" -> {
                dropItem(splitText);
                printInventory();
                return true;
            }
            case "unequip" -> {
                unequipItem(splitText);
                printInventory();
                return true;
            }
            case "info" -> {
                itemInfo(splitText);
                printInventory();
                return true;
            }
        }

        return false;
    }

    private void useItem(String[] instructions) {
        if(instructions.length != 2){
            TextRenderer.printText("To use an item type in "
                    + Color.getColor("green") + "use " + Color.resetColor() +
                    "followed by the item index (number next to the item without the '.')");
            return;
        }

        try {
            Game.getPlayer().getInventory().useItem(Integer.parseInt(instructions[1]));
        }
        catch (NumberFormatException ignored) {
            TextRenderer.printText("To use an item type in "
                    + Color.getColor("green") + "use " + Color.resetColor() +
                    "followed by the item index (number next to the item without the '.')");
        }
    }

    private void dropItem(String[] instructions) {
        if(instructions.length != 2){
            TextRenderer.printText("To drop an item type in "
                    + Color.getColor("yellow") + "drop " + Color.resetColor() +
                    "followed by the item index (number next to the item without the '.')");
            return;
        }

        try {
            Game.getPlayer().getInventory().removeItem(Integer.parseInt(instructions[1]));
        }
        catch (NumberFormatException ignored) {
            TextRenderer.printText("To drop an item type in "
                    + Color.getColor("yellow") + "drop " + Color.resetColor() +
                    "followed by the item index (number next to the item without the '.')");
        }
    }

    private void unequipItem(String[] instructions) {
        if(instructions.length != 2){
            TextRenderer.printText("To unequip an item type in "
                    + Color.getColor("gray") + "unequip " + Color.resetColor() +
                    "followed by the name of the slot (must be one of the following:" +
                    " HEAD, BODY, HANDS, LEGS, FEET or WEAPON)");
            return;
        }

        try{
            EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(instructions[1].toUpperCase());
            Game.getPlayer().getEquipment().unequip(equipmentSlot);
        }
        catch (IllegalArgumentException e){
            TextRenderer.printText("To unequip an item type in "
                    + Color.getColor("gray") + "unequip " + Color.resetColor() +
                    "followed by the name of the slot\n(must be one of the following:" +
                    " HEAD, BODY, HANDS, LEGS, FEET or WEAPON)");
        }
    }

    private void itemInfo(String[] instructions){
        if(instructions.length != 2){
            TextRenderer.printText("To get item info type in "
                    + Color.getColor("magenta") + "info " + Color.resetColor() +
                    "followed by the item index (number next to the item without the '.')");
            return;
        }

        try {
            TextRenderer.printText(Game.getPlayer().getInventory().
                    getItem(Integer.parseInt(instructions[1])).info());
        }
        catch (NumberFormatException ignored){
            TextRenderer.printText("To get item info type in "
                    + Color.getColor("magenta") + "info " + Color.resetColor() +
                    "followed by the item index (number next to the item without the '.')");
        }
        catch (NullPointerException ignored){}
    }

    protected void printInventory() {
        TextRenderer.skipLine();
        TextRenderer.printText("You can either " + Color.getColor("green") + "use " + Color.resetColor()
        + "or " + Color.getColor("yellow") + "drop " + Color.resetColor() + "an item, " + Color.getColor("gray") +
                "unequip " + Color.resetColor() + "an equip item, or " + Color.getColor("red") + "close " + Color.resetColor()
        + "the inventory.");
        TextRenderer.printText(Game.getPlayer().getInventory().toString());
    }
}
