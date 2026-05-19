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

public class Player extends Entity {
    private DungeonRoom currentRoom;
    private DungeonRoom previousRoom;
    private PlayerState currentState;
    private PlayerState previousState;

    private Inventory inventory;
    private Equipment equipment;

    private int gold = 0;

    public Player() {
        super();
        currentRoom = Game.getDungeon().getStartingRoom();
        inventory = new Inventory(10);

        equipment = new Equipment();
    }

    public Player(String name, Stats stats) {
        super(name, stats);
        currentRoom = Game.getDungeon().getStartingRoom();
        inventory = new Inventory(10);

        equipment = new Equipment();
    }

    @Override
    public void handleDeath() {
        if(Game.isConnectedToServer()){
            // TODO handle multiplayer death
        }
        else {
            CombatManager.setInCombat(false);
            TextRenderer.printText(Color.getColor("red") + "GAME OVER" + Color.resetColor());
            Game.changeState(new MainMenuState());
        }
    }

    public void getInput(Scanner input) {
        if(currentRoom == null){
            return;
        }
        if(currentState == null){
            setCurrentRoom(currentRoom);
        }

        currentState.getInput(input);
    }

    public void addGold(int amount) {
        gold += amount;

        if(gold < 0) {
            gold = 0;
        }
    }

    public DungeonRoom getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(DungeonRoom currentRoom) {
        previousRoom = this.currentRoom;
        this.currentRoom = currentRoom;
        currentState = currentRoom.getRoomState();
        currentRoom.setExplored(true);

        if(this == Game.getPlayer()){
            currentRoom.onRoomEntered();
        }
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
}
