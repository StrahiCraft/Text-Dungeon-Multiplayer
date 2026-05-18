package client.entity.player.states;

import client.dungeon.rooms.EmptyRoom;
import client.dungeon.rooms.LootRoom;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.util.Scanner;

public class PlayerInLootRoom extends PlayerWandering{
    public PlayerInLootRoom() {
        super();
        possibleCommands.add(Color.getColor("bright blue") + "loot" + Color.resetColor());
    }

    @Override
    public boolean checkInput(String inputText, Scanner input) {
        if(super.checkInput(inputText, input)){
            return true;
        }

        if(!inputText.equals("loot")){
            return false;
        }

        if(Game.getPlayer().getInventory().isFull()){
            TextRenderer.printText("Your inventory is " + Color.getColor("red") + "full" + Color.resetColor()
            + ". Drop something to " + Color.getColor("bright blue") + "loot " + Color.resetColor() +
                    "the chest.");
            return true;
        }

        TextRenderer.printText("Picked up " + ((LootRoom)Game.getPlayer().getCurrentRoom()).getLoot());

        Game.getPlayer().getInventory().addItem(((LootRoom)Game.getPlayer().getCurrentRoom()).getLoot());

        Game.getDungeon().setRoom(new EmptyRoom(Game.getPlayer().getCurrentRoom().getPosition()),
                Game.getPlayer().getCurrentRoom().getPosition());

        return true;
    }
}
