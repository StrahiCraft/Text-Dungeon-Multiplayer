package client.dungeon;

import client.dungeon.rooms.DungeonRoom;
import client.dungeon.utility.DungeonBounds;
import client.dungeon.utility.DungeonStats;
import client.entity.player.Player;
import utility.Vector2Int;

import java.io.Serializable;
import java.util.*;

public class Dungeon implements Serializable {
    private Dictionary<String, DungeonRoom> dungeonRooms = new Hashtable<>();
    private DungeonBounds dungeonBounds = new DungeonBounds();
    private DungeonStats dungeonStats = new DungeonStats();

    public Dungeon() {
        resetDungeon();
    }

    public void resetDungeon(){
        dungeonStats.resetDungeonStats();
    }

    public void setRoom(DungeonRoom newRoom, Vector2Int roomPosition){
        DungeonRoom oldRoom = dungeonRooms.get(roomPosition.toString());

        if(oldRoom.getNorthRoom() != null){
            oldRoom.getNorthRoom().setSouthRoom(newRoom);
        }
        if(oldRoom.getEastRoom() != null){
            oldRoom.getEastRoom().setWestRoom(newRoom);
        }
        if(oldRoom.getSouthRoom() != null){
            oldRoom.getSouthRoom().setNorthRoom(newRoom);
        }
        if(oldRoom.getWestRoom() != null){
            oldRoom.getWestRoom().setEastRoom(newRoom);
        }

        newRoom.setNorthRoom(oldRoom.getNorthRoom());
        newRoom.setEastRoom(oldRoom.getEastRoom());
        newRoom.setSouthRoom(oldRoom.getSouthRoom());
        newRoom.setWestRoom(oldRoom.getWestRoom());

        newRoom.setPosition(roomPosition);

        dungeonRooms.put(roomPosition.toString(), newRoom);

        // TODO send some signal that a room was changed to change it for all the players
//        if(Player.Instance.getCurrentRoom().getPosition().equalValue(roomPosition)){
//            Player.Instance.setCurrentRoom(newRoom);
//        }
    }

    public DungeonRoom getRandomRoom(){
        ArrayList<DungeonRoom> rooms = Collections.list(dungeonRooms.elements());
        rooms.remove(dungeonRooms.get(new Vector2Int().toString()));

        return rooms.get((int)(Math.random() * rooms.size()));
    }

    public DungeonRoom getStartingRoom(){
        return dungeonRooms.get("x=0, y=0");
    }

    public void progressFloor(){
        dungeonStats.progressFloor();
    }

    public float getCurrentDungeonThreat() {
        return dungeonStats.getCurrentThreat();
    }

    public Dictionary<String, DungeonRoom> getDungeonRooms() {
        return dungeonRooms;
    }

    public DungeonBounds getDungeonBounds() {
        return dungeonBounds;
    }

    public void setDungeonBounds(DungeonBounds dungeonBounds) {
        this.dungeonBounds = dungeonBounds;
    }

    public void setDungeonRooms(Dictionary<String, DungeonRoom> dungeonRooms) {
        this.dungeonRooms = dungeonRooms;
    }

    public DungeonStats getDungeonStats() {
        return dungeonStats;
    }

    public void setDungeonStats(DungeonStats dungeonStats) {
        this.dungeonStats = dungeonStats;
    }
}
