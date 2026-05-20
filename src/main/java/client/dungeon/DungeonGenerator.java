package client.dungeon;

import client.dungeon.rooms.*;
import client.dungeon.utility.DungeonStats;
import utility.Vector2Int;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Stack;

public class DungeonGenerator {
    public static Dungeon generateDungeon(DungeonStats stats){
        return generateDungeon(new Dungeon(stats));
    }

    public static Dungeon generateDungeon(){
        return generateDungeon(new Dungeon());
    }

    public static Dungeon generateDungeon(Dungeon dungeon){
        dungeon.setDungeonRooms(new Hashtable<>());

        generateEmptyDungeon(dungeon);

        generateSpecialRooms(new EnemyRoom(), dungeon.getDungeonStats().getEnemyRoomChance(), dungeon);
        generateSpecialRooms(new LootRoom(), dungeon.getDungeonStats().getLootRoomChance(), dungeon);
        generateSpecialRooms(new ShopRoom(), dungeon.getDungeonStats().getShopChance(), dungeon);

        generateExit(dungeon);

        return dungeon;
    }

    private static void generateEmptyDungeon(Dungeon dungeon){
        Stack<DungeonRoom> roomsToPropagate = new Stack<>();

        dungeon.getDungeonRooms().put(new Vector2Int().toString(), new LootRoom(new Vector2Int(0, 0)));
        roomsToPropagate.push(dungeon.getDungeonRooms().get(new Vector2Int().toString()));

        while(dungeon.getDungeonRooms().size() <= dungeon.getDungeonStats().getMaxRooms() &&
                !roomsToPropagate.isEmpty()){
            DungeonRoom currentRoom = roomsToPropagate.pop();

            tryAddRoomOrConnection(currentRoom, roomsToPropagate, Vector2Int.up(), dungeon);
            tryAddRoomOrConnection(currentRoom, roomsToPropagate, Vector2Int.down(), dungeon);
            tryAddRoomOrConnection(currentRoom, roomsToPropagate, Vector2Int.left(), dungeon);
            tryAddRoomOrConnection(currentRoom, roomsToPropagate, Vector2Int.right(), dungeon);
        }
    }

    private static void tryAddRoomOrConnection(DungeonRoom currentRoom, Stack<DungeonRoom> roomsToPropagate, Vector2Int direction, Dungeon dungeon) {
        if(currentRoom.getNeighbouringRoom(direction) != null){
            return;
        }

        if(Math.random() * 100.0 >= (dungeon.getDungeonStats().getMaxRooms()
                - dungeon.getDungeonRooms().size() - 1) * (100.0 / dungeon.getDungeonStats().getMaxRooms())) {
            return;
        }

        DungeonRoom newRoom = makeNewRoomConnection(currentRoom, direction, dungeon);

        if(newRoom == null){
            return;
        }

        currentRoom.setNeighbouringRoom(newRoom, direction);
        newRoom.setNeighbouringRoom(currentRoom, direction.reversed());

        if(dungeon.getDungeonRooms().get(newRoom.getPosition().toString()) != null){
            return;
        }

        roomsToPropagate.push(newRoom);
        dungeon.getDungeonRooms().put(newRoom.getPosition().toString(), newRoom);

        dungeon.getDungeonBounds().updateDungeonBounds(newRoom);
    }

    private static DungeonRoom makeNewRoomConnection(DungeonRoom currentRoom, Vector2Int direction, Dungeon dungeon) {
        for (DungeonRoom dungeonRoom : Collections.list(dungeon.getDungeonRooms().elements())) {
            if (dungeonRoom.getPosition().equalValue(currentRoom.getPosition().add(direction))) {
                currentRoom.setNeighbouringRoom(dungeonRoom, direction);
                dungeonRoom.setNeighbouringRoom(currentRoom, direction.reversed());
                return null;
            }
        }

        return new EmptyRoom(currentRoom.getPosition().add(direction));
    }

    private static void generateSpecialRooms(DungeonRoom roomType, float chance, Dungeon dungeon) {
        int roomsToGenerate = (int)(dungeon.getDungeonRooms().size() * chance);

        for(int i = 0; i < roomsToGenerate; i++) {
            dungeon.setRoom(roomType.copy(), dungeon.getRandomRoom().getPosition());
        }
    }

    private static void generateExit(Dungeon dungeon){
        int minimumNeighbouringRooms = 4;
        ArrayList<DungeonRoom> exitCandidates = new ArrayList<>();
        for (DungeonRoom dungeonRoom : Collections.list(dungeon.getDungeonRooms().elements())){
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

        DungeonRoom farthestRoom = dungeon.getStartingRoom();

        for(DungeonRoom dungeonRoom : exitCandidates){
            if(Vector2Int.distanceFromZero(dungeonRoom.getPosition()) >
                    Vector2Int.distanceFromZero(farthestRoom.getPosition())){
                farthestRoom = dungeonRoom;
            }
        }

        dungeon.setRoom(new ExitRoom(), farthestRoom.getPosition());
    }
}
