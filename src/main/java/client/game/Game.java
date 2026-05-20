package client.game;

import client.dungeon.Dungeon;
import client.dungeon.DungeonGenerator;
import client.dungeon.utility.DungeonStats;
import client.entity.enemy.EnemyGenerator;
import client.entity.player.Player;
import client.game.game_state.DefaultGameState;
import client.game.game_state.GameState;
import client.game.game_state.LoginState;
import client.game.game_state.MainMenuState;
import client.inventory.item.ItemGenerator;
import utility.Stats;
import utility.file.FileReader;

import java.util.Scanner;

public class Game extends Thread {
    private static boolean gameRunning = true;
    private static boolean connectedToServer = false;
    private static GameState currentState = new DefaultGameState();

    private static Dungeon offlineDungeon;

    private static Scanner input;

    private static Player offlinePlayer;

    public static void initializeGame(){
        input = new Scanner(System.in);
        EnemyGenerator.generateEnemiesFromFiles();
        ItemGenerator.generateItemsFromFiles();
    }

    public static void closeInput(){
        input.close();
    }

    public static void startGameOffline() {
        changeState(new MainMenuState());
    }

    public static void startGameOnline(){
        changeState(new LoginState());
    }

    public static void quitGame(){
        gameRunning = false;
    }

    public static boolean isConnectedToServer() {
        return connectedToServer;
    }

    public static void setConnectedToServer(boolean connectedToServer) {
        Game.connectedToServer = connectedToServer;
    }

    public static void changeState(GameState newState){
        currentState = newState;
    }

    public static Dungeon getDungeon(){
        if(connectedToServer){
            return  ClientApplication.getClientInstance().getLobbyData().getDungeonData();
        }
        return offlineDungeon;
    }

    public static void generateOfflineDungeon(DungeonStats stats){
        if(!connectedToServer){
            offlineDungeon = DungeonGenerator.generateDungeon(stats);
            Game.getPlayer().setCurrentRoom(offlineDungeon.getStartingRoom());
        }
    }

    public static void generateOfflineDungeon(){
        if(!connectedToServer){
            offlineDungeon = new Dungeon();
            offlineDungeon = DungeonGenerator.generateDungeon();
        }
    }

    public static Player getPlayer(){
        if(connectedToServer){
            return ClientApplication.getClientInstance().getLobbyData().getPlayerData(ClientApplication.getClientInstance().getPlayerUsername());
        }
        return offlinePlayer;
    }

    public static void generateOfflinePlayer(){
        if(!connectedToServer){
            offlinePlayer = new Player();
            Stats playerStats = new Stats();
            playerStats.interpretFileData(FileReader.readFile("src/main/resources/assets/config/playerStats.txt"));
            offlinePlayer.setStats(playerStats);
        }
    }

    @Override
    public void run() {
        initializeGame();
        while (gameRunning){
            currentState.updateState(input);
            System.out.print("");
        }
        ClientApplication.getClientInstance().disconnect();
        closeInput();
    }
}
