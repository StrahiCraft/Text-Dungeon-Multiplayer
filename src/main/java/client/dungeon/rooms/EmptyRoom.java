package client.dungeon.rooms;

import client.entity.player.Player;
import client.entity.player.states.PlayerState;
import client.entity.player.states.PlayerWandering;
import client.graphics.Color;
import client.graphics.TextRenderer;
import utility.Vector2Int;

public class EmptyRoom extends DungeonRoom {

    public EmptyRoom() {

    }
    public EmptyRoom(Vector2Int position) {
        super(position);
    }

    @Override
    public PlayerState getRoomState() {
        return new PlayerWandering();
    }

    @Override
    public void onRoomEntered() {
        String onRoomEnterText = "You find yourself in an empty room." + directionText();
        TextRenderer.printText(onRoomEnterText);
        Player.Instance.increaseScore(1);
    }

    @Override
    public DungeonRoom copy() {
        return new EmptyRoom();
    }

    protected String directionText(){
        StringBuilder actions = new StringBuilder();

        actions.append("\nYou can either check your ").
                append(Color.getColor("blue")).
                append("map").append(Color.resetColor()).append(", or go ");

        if(getNorthRoom() != null){
            actions.append(Color.getColor("bright yellow")).append("north ").append(Color.resetColor());
        }
        if(getEastRoom() != null){
            actions.append(Color.getColor("bright yellow")).append("east ").append(Color.resetColor());
        }
        if(getSouthRoom() != null){
            actions.append(Color.getColor("bright yellow")).append("south ").append(Color.resetColor());
        }
        if(getWestRoom() != null){
            actions.append(Color.getColor("bright yellow")).append("west ").append(Color.resetColor());
        }

        return actions.toString();
    }
}
