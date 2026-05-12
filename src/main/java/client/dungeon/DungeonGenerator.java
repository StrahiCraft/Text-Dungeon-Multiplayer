package client.dungeon;

import client.dungeon.rooms.*;
import client.entity.player.Player;
import utility.Vector2Int;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Stack;

public class DungeonGenerator {
    public static void generateDungeon(){
        Dungeon.setDungeonRooms(new Hashtable<>());

        generateEmptyDungeon();
        Player.Instance.setCurrentRoom(Dungeon.getStartingRoom());

        generateSpecialRooms(new EnemyRoom(), Dungeon.getDungeonStats().getEnemyRoomChance());
        generateSpecialRooms(new LootRoom(), Dungeon.getDungeonStats().getLootRoomChance());
        generateSpecialRooms(new ShopRoom(), Dungeon.getDungeonStats().getShopChance());

        generateExit();
    }

    private static void generateEmptyDungeon(){
        Stack<DungeonRoom> roomsToPropagate = new Stack<>();

        Dungeon.getDungeonRooms().put(new Vector2Int().toString(), new LootRoom(new Vector2Int(0, 0)));
        roomsToPropagate.push(Dungeon.getDungeonRooms().get(new Vector2Int().toString()));

        while(Dungeon.getDungeonRooms().size() <= Dungeon.getDungeonStats().getMaxRooms() &&
                !roomsToPropagate.isEmpty()){
            DungeonRoom currentRoom = roomsToPropagate.pop();

            tryAddRoomOrConnection(currentRoom, roomsToPropagate, Vector2Int.up());
            tryAddRoomOrConnection(currentRoom, roomsToPropagate, Vector2Int.down());
            tryAddRoomOrConnection(currentRoom, roomsToPropagate, Vector2Int.left());
            tryAddRoomOrConnection(currentRoom, roomsToPropagate, Vector2Int.right());
        }
    }

    private static void tryAddRoomOrConnection(DungeonRoom currentRoom, Stack<DungeonRoom> roomsToPropagate, Vector2Int direction) {
        if(currentRoom.getNeighbouringRoom(direction) != null){
            return;
        }

        if(Math.random() * 100.0 >= (Dungeon.getDungeonStats().getMaxRooms()
                - Dungeon.getDungeonRooms().size() - 1) * (100.0 / Dungeon.getDungeonStats().getMaxRooms())) {
            return;
        }

        DungeonRoom newRoom = makeNewRoomConnection(currentRoom, direction);

        if(newRoom == null){
            return;
        }

        currentRoom.setNeighbouringRoom(newRoom, direction);
        newRoom.setNeighbouringRoom(currentRoom, direction.reversed());

        if(Dungeon.getDungeonRooms().get(newRoom.getPosition().toString()) != null){
            return;
        }

        roomsToPropagate.push(newRoom);
        Dungeon.getDungeonRooms().put(newRoom.getPosition().toString(), newRoom);

        Dungeon.getDungeonBounds().updateDungeonBounds(newRoom);
    }

    private static DungeonRoom makeNewRoomConnection(DungeonRoom currentRoom, Vector2Int direction) {
        for (DungeonRoom dungeonRoom : Collections.list(Dungeon.getDungeonRooms().elements())) {
            if (dungeonRoom.getPosition().equalValue(currentRoom.getPosition().add(direction))) {
                currentRoom.setNeighbouringRoom(dungeonRoom, direction);
                dungeonRoom.setNeighbouringRoom(currentRoom, direction.reversed());
                return null;
            }
        }

        return new EmptyRoom(currentRoom.getPosition().add(direction));
    }

    private static void generateSpecialRooms(DungeonRoom roomType, float chance) {
        int roomsToGenerate = (int)(Dungeon.getDungeonRooms().size() * chance);

        for(int i = 0; i < roomsToGenerate; i++) {
            Dungeon.setRoom(roomType.copy(), Dungeon.getRandomRoom().getPosition());
        }
    }

    private static void generateExit(){
        int minimumNeighbouringRooms = 4;
        ArrayList<DungeonRoom> exitCandidates = new ArrayList<>();
        for (DungeonRoom dungeonRoom : Collections.list(Dungeon.getDungeonRooms().elements())){
            if(dungeonRoom.getConnectionCount() <= minimumNeighbouringRooms){
                exitCandidates.add(dungeonRoom);
                continue;
            }
            if(dungeonRoom.getConnectionCount() < minimumNeighbouringRooms){
                exitCandidates.clear();
                exitCandidates.add(dungeonRoom);
                minimumNeighbouringRooms = dungeonRoom.getConnectionCount();
            }
        }

        if(exitCandidates.isEmpty()){
            System.out.println("Error when generating dungeon exit.");
            return;
        }

        DungeonRoom farthestRoom = Dungeon.getStartingRoom();

        for(DungeonRoom dungeonRoom : exitCandidates){
            if(Vector2Int.distanceFromZero(dungeonRoom.getPosition()) >
                    Vector2Int.distanceFromZero(farthestRoom.getPosition())){
                farthestRoom = dungeonRoom;
            }
        }

        Dungeon.setRoom(new ExitRoom(), farthestRoom.getPosition());
    }
}
