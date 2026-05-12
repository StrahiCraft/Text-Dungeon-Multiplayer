package client.dungeon.utility;

import client.dungeon.rooms.DungeonRoom;
import utility.Vector2Int;

public class DungeonBounds {
    private Vector2Int maxDungeonCoordinate;
    private Vector2Int minDungeonCoordinate;

    public DungeonBounds() {
        maxDungeonCoordinate = new Vector2Int(0, 0);
        minDungeonCoordinate = new Vector2Int(0, 0);
    }

    public void updateDungeonBounds(DungeonRoom newRoom){
        if(newRoom.getPosition().getX() > maxDungeonCoordinate.getX()){
            maxDungeonCoordinate.setX(newRoom.getPosition().getX());
        }
        if(newRoom.getPosition().getY() > maxDungeonCoordinate.getY()){
            maxDungeonCoordinate.setY(newRoom.getPosition().getY());
        }

        if(newRoom.getPosition().getX() < minDungeonCoordinate.getX()){
            minDungeonCoordinate.setX(newRoom.getPosition().getX());
        }
        if(newRoom.getPosition().getY() < minDungeonCoordinate.getY()){
            minDungeonCoordinate.setY(newRoom.getPosition().getY());
        }
    }

    public Vector2Int getMaxDungeonCoordinate() {
        return maxDungeonCoordinate;
    }

    public void setMaxDungeonCoordinate(Vector2Int maxDungeonCoordinate) {
        this.maxDungeonCoordinate = maxDungeonCoordinate;
    }

    public Vector2Int getMinDungeonCoordinate() {
        return minDungeonCoordinate;
    }

    public  void setMinDungeonCoordinate(Vector2Int minDungeonCoordinate) {
        this.minDungeonCoordinate = minDungeonCoordinate;
    }
}
