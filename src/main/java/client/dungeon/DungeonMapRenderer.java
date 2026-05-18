package client.dungeon;

import client.dungeon.rooms.DungeonRoom;
import client.dungeon.utility.DungeonBounds;
import client.entity.player.Player;
import client.graphics.Color;
import utility.Vector2Int;

import java.util.Arrays;
import java.util.Collections;

public class DungeonMapRenderer {

    public static String renderDungeonMap(Dungeon dungeon){
        char[][] dungeonMap = fillDungeonMap(dungeon);
        StringBuilder mapString = new StringBuilder();

        for(int x = dungeonMap.length - 1; x > 0; x--){
            for(int y = 0; y < dungeonMap[x].length; y++){
                mapString.append(getRoomColor(dungeonMap[x][y]) + dungeonMap[x][y] + "\u001B[0m");
            }
            mapString.append('\n');
        }
        return mapString.toString();
    }

    public static String getRoomColor(char roomType){
        if(roomType == 'P'){
            return Color.getColor("green");
        }
        if(roomType == 'E'){
            return Color.getColor("red");
        }
        if(roomType == 'L'){
            return Color.getColor("blue");
        }
        if(roomType == '$'){
            return Color.getColor("magenta");
        }
        if(roomType == 'V'){
            return Color.getColor("bright red");
        }
        return Color.resetColor();
    }

    private static char[][] fillDungeonMap(Dungeon dungeon){
        DungeonBounds dungeonBounds = dungeon.getDungeonBounds();

        char[][]dungeonMap = new char[(dungeonBounds.getMaxDungeonCoordinate().getY() -
                dungeonBounds.getMinDungeonCoordinate().getY() + 1) * 3]
                [(dungeonBounds.getMaxDungeonCoordinate().getX() -
                dungeonBounds.getMinDungeonCoordinate().getX() + 1) * 3];

        for (char[] chars : dungeonMap) {
            Arrays.fill(chars, ' ');
        }

        for(DungeonRoom currentRoom : Collections.list(dungeon.getDungeonRooms().elements())){
            fillDungeonRoom(currentRoom, dungeonMap, dungeonBounds);
        }

        return dungeonMap;
    }

    private static void fillDungeonRoom(DungeonRoom currentRoom, char[][] dungeonMap, DungeonBounds dungeonBounds){
        if(!currentRoom.getExplored()){
            return;
        }

        char roomSymbol = currentRoom.getPosition().equalValue(Player.Instance.getCurrentRoom().getPosition())?
                'P' : currentRoom.getRoomSymbol();

        dungeonMap[(currentRoom.getPosition().getY() - dungeonBounds.getMinDungeonCoordinate().getY()) * 3 + 1]
                [(currentRoom.getPosition().getX() - dungeonBounds.getMinDungeonCoordinate().getX()) * 3 + 1] = roomSymbol;

        fillHallways(currentRoom, Vector2Int.up(), dungeonMap, dungeonBounds);
        fillHallways(currentRoom, Vector2Int.down(), dungeonMap, dungeonBounds);
        fillHallways(currentRoom, Vector2Int.left(), dungeonMap, dungeonBounds);
        fillHallways(currentRoom, Vector2Int.right(), dungeonMap, dungeonBounds);
    }

    private static void fillHallways(DungeonRoom currentRoom, Vector2Int direction, char[][] dungeonMap, DungeonBounds dungeonBounds) {
        if(currentRoom.getNeighbouringRoom(direction) == null){
            return;
        }

        char hallwayCharacter = direction.getX() == 0? '|' : '-';

        dungeonMap[(currentRoom.getPosition().getY() - dungeonBounds.getMinDungeonCoordinate().getY()) * 3 + 1 + direction.getY()]
                [(currentRoom.getPosition().getX() - dungeonBounds.getMinDungeonCoordinate().getX()) * 3 + 1 + direction.getX()] = hallwayCharacter;
    }
}
