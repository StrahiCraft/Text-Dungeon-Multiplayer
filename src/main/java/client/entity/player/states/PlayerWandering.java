package client.entity.player.states;

import client.dungeon.DungeonMapRenderer;
import client.dungeon.rooms.DungeonRoom;
import client.entity.player.Player;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;
import utility.Vector2Int;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class PlayerWandering extends PlayerState{

    private Dictionary<String, Vector2Int> directionalDictionary =  new Hashtable<>();

    public PlayerWandering() {
        super();
        possibleCommands.add(Color.getColor("blue") + "map" + Color.resetColor());
        possibleCommands.add(Color.getColor("green") + "inventory" + Color.resetColor());

        DungeonRoom currentRoom = Player.Instance.getCurrentRoom();

        if(currentRoom.getNorthRoom() != null){
            possibleCommands.add(Color.getColor("bright yellow") + "north" + Color.resetColor());
        }
        if(currentRoom.getEastRoom() != null){
            possibleCommands.add(Color.getColor("bright yellow") + "east" + Color.resetColor());
        }
        if(currentRoom.getSouthRoom() != null){
            possibleCommands.add(Color.getColor("bright yellow") + "south" + Color.resetColor());
        }
        if(currentRoom.getWestRoom() != null){
            possibleCommands.add(Color.getColor("bright yellow") + "west" + Color.resetColor());
        }

        directionalDictionary.put("north", Vector2Int.up());
        directionalDictionary.put("east", Vector2Int.right());
        directionalDictionary.put("south", Vector2Int.down());
        directionalDictionary.put("west", Vector2Int.left());
    }

    @Override
    public boolean checkInput(String inputText, Scanner input) {
        switch (inputText) {
            case "map" -> {
                TextRenderer.printText(DungeonMapRenderer.renderDungeonMap(Game.getDungeon()));
                return true;
            }
            case "inventory" -> {
                Player.Instance.setCurrentState(new PlayerInInventory());
                return true;
            }
            case "north", "east", "south", "west" -> {
                goToRoom(inputText, input);
                return true;
            }
        }

        return false;
    }

    private void goToRoom(String direction, Scanner input) {
        if(!possibleCommands.contains(Color.getColor("bright yellow") + direction+ Color.resetColor())){
            TextRenderer.printText("This room doesn't have a " + direction + "ern door, try another direction.\n");
            getInput(input);
            return;
        }

        Player.Instance.setCurrentRoom(Player.Instance.getCurrentRoom().getNeighbouringRoom(directionalDictionary.get(direction)));
    }
}
