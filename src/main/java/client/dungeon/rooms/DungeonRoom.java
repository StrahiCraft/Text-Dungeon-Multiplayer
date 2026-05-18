package client.dungeon.rooms;

import client.entity.player.states.PlayerState;
import utility.Vector2Int;

import java.io.Serializable;

public abstract class DungeonRoom implements Serializable {
    private Vector2Int position;

    private char roomSymbol = '#';

    private boolean explored = false;

    private DungeonRoom northRoom;
    private DungeonRoom eastRoom;
    private DungeonRoom southRoom;
    private DungeonRoom westRoom;

    public DungeonRoom() {

    }

    public DungeonRoom(Vector2Int position) {
        this.position = position;
    }

    public abstract PlayerState getRoomState();
    public abstract void onRoomEntered();
    public abstract DungeonRoom copy();

    public void setNeighbouringRoom(DungeonRoom room, Vector2Int direction) {
        if(direction == null) {
            System.out.println("ERROR! Direction is null.");
            return;
        }

        if(direction.equalValue(Vector2Int.up())){
            setNorthRoom(room);
            return;
        }

        if(direction.equalValue(Vector2Int.down())){
            setSouthRoom(room);
            return;
        }

        if(direction.equalValue(Vector2Int.left())){
            setWestRoom(room);
            return;
        }

        if(direction.equalValue(Vector2Int.right())){
            setEastRoom(room);
            return;
        }

        System.out.println("ERROR! Invalid direction.");
    }

    public DungeonRoom getNeighbouringRoom(Vector2Int direction) {
        if(direction == null) {
            System.out.println("ERROR! Direction is null.");
            return this;
        }

        if(direction.equalValue(Vector2Int.up())){
            return getNorthRoom();
        }

        if(direction.equalValue(Vector2Int.down())){
            return getSouthRoom();
        }

        if(direction.equalValue(Vector2Int.left())){
            return getWestRoom();
        }

        if(direction.equalValue(Vector2Int.right())){
            return getEastRoom();
        }

        System.out.println("ERROR! Invalid direction.");
        return this;
    }

    public int getConnectionCount(){
        int connectionCount = 0;
        if(northRoom != null){
            connectionCount++;
        }
        if(eastRoom != null){
            connectionCount++;
        }
        if(southRoom != null){
            connectionCount++;
        }
        if(westRoom != null){
            connectionCount++;
        }
        return connectionCount;
    }

    public Vector2Int getPosition() {
        return position;
    }

    public void setPosition(Vector2Int position) {
        this.position = position;
    }

    public DungeonRoom getNorthRoom() {
        return northRoom;
    }

    public void setNorthRoom(DungeonRoom northRoom) {
        this.northRoom = northRoom;
    }

    public DungeonRoom getEastRoom() {
        return eastRoom;
    }

    public void setEastRoom(DungeonRoom eastRoom) {
        this.eastRoom = eastRoom;
    }

    public DungeonRoom getSouthRoom() {
        return southRoom;
    }

    public void setSouthRoom(DungeonRoom southRoom) {
        this.southRoom = southRoom;
    }

    public DungeonRoom getWestRoom() {
        return westRoom;
    }

    public void setWestRoom(DungeonRoom westRoom) {
        this.westRoom = westRoom;
    }

    public boolean getExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public char getRoomSymbol() {
        return roomSymbol;
    }

    public void setRoomSymbol(char roomSymbol) {
        this.roomSymbol = roomSymbol;
    }

    @Override
    public String toString() {
        return "DungeonRoom{" +
                "position=" + position +
                ", roomSymbol=" + roomSymbol +
                ", explored=" + explored +
                '}';
    }
}
