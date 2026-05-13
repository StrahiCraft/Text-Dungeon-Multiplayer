package client.game;

import client.dungeon.Dungeon;
import client.dungeon.DungeonGenerator;
import client.entity.enemy.EnemyGenerator;
import client.inventory.item.ItemGenerator;
import client.entity.player.Player;
import client.graphics.Color;
import client.graphics.TextRenderer;
import utility.Stats;
import utility.file.FileReader;

import java.util.Scanner;

public class Game implements Runnable {
    private static boolean gameRunning = true;
    private static boolean inGame = true;
    private static Player champion;
    private static boolean connectedToServer = false;
    private static boolean loggingIn = false;

    private static Scanner input;

    public static void initializeGame(){
        input = new Scanner(System.in);
        EnemyGenerator.generateEnemiesFromFiles();
        ItemGenerator.generateItemsFromFiles();
    }

    public static void closeInput(){
        input.close();
    }

    public static void startGameOffline() {
        mainMenu();
    }

    public static void startGameOnline(){
        login();
    }

    private static void login(){
        loggingIn = true;
        boolean goingOffline = false;

        while (loggingIn){
            TextRenderer.printText("Welcome to the login screen, type in " + Color.getColor("green") + "login" + Color.resetColor() +
                    " <" + Color.getColor("yellow") + "username" + Color.resetColor() + "> <" + Color.getColor("yellow") +
                    "password" + Color.resetColor() + "> to log in to your existing account.");

            TextRenderer.printText("If you don't have an account, create one by typing in " + Color.getColor("blue") + "register" + Color.resetColor() +
                    " <" + Color.getColor("yellow") + "username" + Color.resetColor() + "> <" + Color.getColor("yellow") +
                    "password" + Color.resetColor() + ">");

            TextRenderer.printText("If you wish to play offline, type in " + Color.getColor("yellow") + "offline");
            TextRenderer.printText("Type in " + Color.getColor("red") + "quit" + Color.resetColor() + " to quit the game");

            String command = input.nextLine();
            String[] commandParameters = command.split(" ");

            if(commandParameters.length == 1) {
                if(commandParameters[0].equalsIgnoreCase("offline")){
                    loggingIn = false;
                    goingOffline = true;
                    break;
                }
                if(commandParameters[0].equalsIgnoreCase("quit")){
                    loggingIn = false;
                    closeInput();
                    ClientApplication.getClientInstance().disconnect();
                    TextRenderer.printText("Goodbye!");
                    quitGame();
                    return;
                }
            }
        }

        if(goingOffline){
            TextRenderer.printText(Color.getColor("yellow") + "Going offline...");
            ClientApplication.getClientInstance().disconnect();
            startGameOffline();
        }
    }

    private static void mainMenu() {
        while (true) {
            renderMainMenu();
            String command = input.nextLine();

            if(command.equalsIgnoreCase("quit")) {
                TextRenderer.printText("Goodbye!");
                quitGame();
                return;
            }

            if(command.equalsIgnoreCase("start")) {
                inGame = true;
                gameLoop();
            }

            if(!isConnectedToServer()){
                if(command.equalsIgnoreCase("reconnect")){
                    TextRenderer.printText(Color.getColor("yellow") + "Attempting to reconnect to the server...");
                    ClientApplication.connectToServer();
                    break;
                }
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

        TextRenderer.printText("\nType in " + Color.getColor("green") + "start" + Color.resetColor() + " to start the game");
        if(!isConnectedToServer()){
            TextRenderer.printText("\nType in " + Color.getColor("blue") + "reconnect" + Color.resetColor() + " to attempt to reconnect to the server");
        }
        TextRenderer.printText("\nType in " + Color.getColor("red") + "quit" + Color.resetColor() + " to quit game");
    }

    private static void gameLoop() {
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

        while(inGame){
            player.getInput(input);
        }
    }

    public static void quitGame(){
        gameRunning = false;
    }

    public static void backToMainMenu(){
        setInGame(false);
    }

    public static void setInGame(boolean inGame) {
        Game.inGame = inGame;
    }

    public static boolean isConnectedToServer() {
        return connectedToServer;
    }

    public static void setConnectedToServer(boolean connectedToServer) {
        Game.connectedToServer = connectedToServer;
    }

    @Override
    public void run() {
        initializeGame();
        while (gameRunning){

        }
    }
}
