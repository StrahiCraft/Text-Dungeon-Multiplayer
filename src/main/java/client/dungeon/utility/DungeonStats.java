package client.dungeon.utility;

import client.dungeon.DungeonGenerator;
import client.game.Game;
import utility.file.FileInterpreter;
import utility.file.FileReader;

import java.io.Serializable;
import java.util.ArrayList;

public class DungeonStats implements FileInterpreter, Serializable {
    private int maxRooms;
    private int roomIncrease;
    private float currentThreat;
    private float threatMultiplier;

    private float enemyRoomChance;
    private float lootRoomChance;
    private float shopChance;

    public DungeonStats() {
        resetDungeonStats();
    }

    public DungeonStats(int maxRooms, int roomIncrease, float currentThreat,
                        float threatMultiplier, float enemyRoomChance,
                        float lootRoomChance, float shopChance) {
        this.maxRooms = maxRooms;
        this.roomIncrease = roomIncrease;
        this.currentThreat = currentThreat;
        this.threatMultiplier = threatMultiplier;
        this.enemyRoomChance = enemyRoomChance;
        this.lootRoomChance = lootRoomChance;
        this.shopChance = shopChance;
    }

    @Override
    public void interpretFileData(ArrayList<String> fileData) {
        maxRooms = Integer.parseInt(fileData.get(0));
        roomIncrease = Integer.parseInt(fileData.get(1));
        currentThreat = Float.parseFloat(fileData.get(2));
        threatMultiplier = Float.parseFloat(fileData.get(3));
        enemyRoomChance = Float.parseFloat(fileData.get(4));
        lootRoomChance = Float.parseFloat(fileData.get(5));
        shopChance = Float.parseFloat(fileData.get(6));
    }

    public void resetDungeonStats(){
        interpretFileData(FileReader.readFile("src/main/resources/assets/config/startingDungeonStats.txt"));
    }

    public void progressFloor(){
        maxRooms += roomIncrease;
        currentThreat *= threatMultiplier;
    }

    public int getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(int maxRooms) {
        this.maxRooms = maxRooms;
    }

    public int getRoomIncrease() {
        return roomIncrease;
    }

    public void setRoomIncrease(int roomIncrease) {
        this.roomIncrease = roomIncrease;
    }

    public float getCurrentThreat() {
        return currentThreat;
    }

    public void setCurrentThreat(float currentThreat) {
        this.currentThreat = currentThreat;
    }

    public float getThreatMultiplier() {
        return threatMultiplier;
    }

    public void setThreatMultiplier(float threatMultiplier) {
        this.threatMultiplier = threatMultiplier;
    }

    public float getEnemyRoomChance() {
        return enemyRoomChance;
    }

    public void setEnemyRoomChance(float enemyRoomChance) {
        this.enemyRoomChance = enemyRoomChance;
    }

    public float getLootRoomChance() {
        return lootRoomChance;
    }

    public void setLootRoomChance(float lootRoomChance) {
        this.lootRoomChance = lootRoomChance;
    }

    public float getShopChance() {
        return shopChance;
    }

    public void setShopChance(float shopChance) {
        this.shopChance = shopChance;
    }
}
