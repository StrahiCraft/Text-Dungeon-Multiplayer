package client.game;

import client.dungeon.Dungeon;
import client.dungeon.DungeonGenerator;
import client.entity.enemy.EnemyGenerator;
import client.inventory.item.ItemGenerator;
import client.entity.player.Player;
import client.graphics.Color;
import client.graphics.TextRenderer;
import com.mysql.cj.protocol.x.Notice;
import utility.Stats;
import utility.file.FileReader;

import java.util.Scanner;

public class Game {
    private static boolean gameRunning = true;
    private static Player champion;
    private static boolean connectedToServer = false;

    private static Scanner input;

    public static void initializeGame(){
        input = new Scanner(System.in);
        EnemyGenerator.generateEnemiesFromFiles();
        ItemGenerator.generateItemsFromFiles();
    }

    public static void startGameOffline(){

    }

    public static void startGameOnline(){

    }

    private static void mainMenu(Scanner input) {

        while (true) {
            renderMainMenu();
            String command = input.nextLine();

            if(command.equalsIgnoreCase("quit")) {
                TextRenderer.printText("Goodbye!");
                return;
            }

            if(command.equalsIgnoreCase("start")) {
                gameRunning = true;
                gameLoop(input);
            }
        }
    }

    private static void updateChampion() {
        champion = new Player();
        champion.interpretFileData(FileReader.readFile("src/main/resources/assets/champion.txt"));
    }

    private static void renderMainMenu() {
        updateChampion();
        TextRenderer.skipLine();

        TextRenderer.printText("Welcome to the " + Color.getColor("green") + "TEXT DUNGEON" + Color.resetColor() + "!");

        if(champion.getCurrentScore() > 0){
            TextRenderer.skipLine();
            TextRenderer.printText("The current champion is " + Color.getColor("red") + champion.getName() + Color.resetColor() +
                    " with a" + Color.getColor("green") + " score " + Color.resetColor() + "of " + champion.getCurrentScore() + ".");
        }

        TextRenderer.printText("\nType in " + Color.getColor("green") + "start" + Color.resetColor() + " to start the game." +
                "\nType in " + Color.getColor("red") + "quit" + Color.resetColor() + " to quit game");
    }

    private static void gameLoop(Scanner input){
        Player player = new Player();
        Stats playerStats = new Stats();
        playerStats.interpretFileData(FileReader.readFile("src/main/resources/assets/config/playerStats.txt"));
        player.setStats(playerStats);

        TextRenderer.printText("Enter your name " + Color.getColor("blue") + "PLAYER" + Color.resetColor() + ": ");
        player.setName(input.nextLine());

        TextRenderer.skipLine();
        TextRenderer.printText("Entering " + Color.getColor("red") + "DUNGEON" +  Color.resetColor() + "...");
        TextRenderer.skipLine();

        Dungeon.resetDungeon();
        DungeonGenerator.generateDungeon();

        while(gameRunning){
            player.getInput(input);
        }
    }

    public static void quitGame(){
        setGameRunning(false);
    }

    public static void setGameRunning(boolean gameRunning) {
        Game.gameRunning = gameRunning;
    }

    public static boolean isGameRunning() {
        return gameRunning;
    }

    public static Player getChampion() {
        return champion;
    }

    public static void setChampion(Player champion) {
        Game.champion = champion;
    }

    public static boolean isConnectedToServer() {
        return connectedToServer;
    }

    public static void setConnectedToServer(boolean connectedToServer) {
        Game.connectedToServer = connectedToServer;
    }
}
