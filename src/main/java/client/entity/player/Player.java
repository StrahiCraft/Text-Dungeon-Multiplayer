package client.entity.player;

import client.combat.CombatManager;
import client.dungeon.Dungeon;
import client.dungeon.rooms.DungeonRoom;
import client.entity.Entity;
import client.exceptions.IllegalScoreIncreaseException;
import client.game.game_state.MainMenuState;
import client.inventory.Inventory;
import client.inventory.item.equipment.Equipment;
import client.entity.player.states.PlayerState;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;
import utility.Stats;
import utility.file.FileInterpreter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Player extends Entity implements utility.file.FileWriter, FileInterpreter {
    public static Player Instance;

    private DungeonRoom currentRoom;
    private DungeonRoom previousRoom;
    private PlayerState currentState;
    private PlayerState previousState;

    private Inventory inventory;
    private Equipment equipment;

    private int gold = 0;

    private int currentScore = 0;

    public Player() {
        super();
        Instance = this;
        currentRoom = Dungeon.getStartingRoom();
        inventory = new Inventory(10);

        equipment = new Equipment();
    }

    public Player(String name, Stats stats) {
        super(name, stats);
        Instance = this;
        currentRoom = Dungeon.getStartingRoom();
        inventory = new Inventory(10);

        equipment = new Equipment();
    }

    @Override
    public void handleDeath() {
        CombatManager.setInCombat(false);
        TextRenderer.printText(Color.getColor("red") + "GAME OVER" + Color.resetColor() + "\nYour score was: " + currentScore);
        Game.changeState(new MainMenuState());
    }

    public void getInput(Scanner input) {
        if(currentRoom == null){
            return;
        }

        currentState.getInput(input);
    }

    public void addGold(int amount) {
        gold += amount;

        if(gold < 0) {
            gold = 0;
        }
    }

    public int increaseScore(int amount) throws IllegalScoreIncreaseException {
        if(amount < 0) {
            throw new IllegalScoreIncreaseException("Score increase cannot be negative!");
        }

        currentScore += amount;
        
        return currentScore;
    }

    @Override
    public void writeToFile() {
        try {
            File file = new File("src/main/resources/assets/champion.txt");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("name=" + getName() + "\nhighScore=" + currentScore + "\n");

            fileWriter.close();
        } catch (IOException e) {
            System.out.println(Color.getColor("bright red") + "Error while creating or writing to utility.file: "
                    + Color.resetColor() + "src/main/resources/assets/enemies" + getName() + ".txt");
            e.printStackTrace();
        }
    }

    @Override
    public void interpretFileData(ArrayList<String> fileData) {
        if(fileData.size() != 2 || fileData == null){
            writeToFile();
        }

        setName(fileData.get(0));
        try{
            currentScore = Integer.parseInt(fileData.get(1));
        }
        catch(NumberFormatException e){
            System.out.println("Error: " + Color.getColor("red") + "Error when reading champion score." + Color.resetColor());
        }
    }

    @Override
    public void writeToFile(FileWriter fileWriter) {
        writeToFile();
    }

    public DungeonRoom getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(DungeonRoom currentRoom) {
        previousRoom = this.currentRoom;
        this.currentRoom = currentRoom;
        currentState = currentRoom.getRoomState();
        currentRoom.setExplored(true);

        currentRoom.onRoomEntered();
    }

    public DungeonRoom getPreviousRoom() {
        return previousRoom;
    }

    public void setPreviousRoom(DungeonRoom previousRoom) {
        this.previousRoom = previousRoom;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(PlayerState currentState) {
        previousState = this.currentState;
        this.currentState = currentState;
    }

    public PlayerState getPreviousState() {
        return previousState;
    }

    public void setPreviousState(PlayerState previousState) {
        this.previousState = previousState;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
}
