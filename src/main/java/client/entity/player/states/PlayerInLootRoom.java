package client.entity.player.states;

import client.dungeon.Dungeon;
import client.dungeon.rooms.EmptyRoom;
import client.dungeon.rooms.LootRoom;
import client.entity.player.Player;
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

        if(Player.Instance.getInventory().isFull()){
            TextRenderer.printText("Your inventory is " + Color.getColor("red") + "full" + Color.resetColor()
            + ". Drop something to " + Color.getColor("bright blue") + "loot " + Color.resetColor() +
                    "the chest.");
            return true;
        }

        TextRenderer.printText("Picked up " + ((LootRoom)Player.Instance.getCurrentRoom()).getLoot());

        Player.Instance.getInventory().addItem(((LootRoom)Player.Instance.getCurrentRoom()).getLoot());
        Player.Instance.increaseScore(1);

        Game.getDungeon().setRoom(new EmptyRoom(Player.Instance.getCurrentRoom().getPosition()),
                Player.Instance.getCurrentRoom().getPosition());


        return true;
    }
}
