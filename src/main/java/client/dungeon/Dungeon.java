package client.dungeon;

import client.dungeon.rooms.DungeonRoom;
import client.dungeon.utility.DungeonBounds;
import client.dungeon.utility.DungeonStats;
import client.entity.player.Player;
import utility.Vector2Int;

import java.util.*;

public class Dungeon {
    private static Dictionary<String, DungeonRoom> dungeonRooms = new Hashtable<>();
    private static DungeonBounds dungeonBounds = new DungeonBounds();
    private static DungeonStats dungeonStats = new DungeonStats();

    public static void resetDungeon(){
        dungeonStats.resetDungeonStats();
    }

    public static void setRoom(DungeonRoom newRoom, Vector2Int roomPosition){
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

        if(Player.Instance.getCurrentRoom().getPosition().equalValue(roomPosition)){
            Player.Instance.setCurrentRoom(newRoom);
        }
    }

    public static DungeonRoom getRandomRoom(){
        ArrayList<DungeonRoom> rooms = Collections.list(dungeonRooms.elements());
        rooms.remove(dungeonRooms.get(new Vector2Int().toString()));

        return rooms.get((int)(Math.random() * rooms.size()));
    }

    public static DungeonRoom getStartingRoom(){
        return dungeonRooms.get("x=0, y=0");
    }

    public static void progressFloor(){
        dungeonStats.progressFloor();
    }

    public static float getCurrentDungeonThreat() {
        return dungeonStats.getCurrentThreat();
    }

    public static Dictionary<String, DungeonRoom> getDungeonRooms() {
        return dungeonRooms;
    }

    public static DungeonBounds getDungeonBounds() {
        return dungeonBounds;
    }

    public static void setDungeonBounds(DungeonBounds dungeonBounds) {
        Dungeon.dungeonBounds = dungeonBounds;
    }

    public static void setDungeonRooms(Dictionary<String, DungeonRoom> dungeonRooms) {
        Dungeon.dungeonRooms = dungeonRooms;
    }

    public static DungeonStats getDungeonStats() {
        return dungeonStats;
    }

    public static void setDungeonStats(DungeonStats dungeonStats) {
        Dungeon.dungeonStats = dungeonStats;
    }
}
