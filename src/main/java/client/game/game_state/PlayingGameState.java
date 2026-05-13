package client.game.game_state;

import client.dungeon.Dungeon;
import client.dungeon.DungeonGenerator;
import client.entity.player.Player;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;
import utility.Stats;
import utility.file.FileReader;

import java.util.Scanner;

public class PlayingGameState extends GameState{
    private boolean gameInitialized = false;
    private Player player;

    private void initializeGame(Scanner input){
        player = new Player();
        Stats playerStats = new Stats();
        playerStats.interpretFileData(FileReader.readFile("src/main/resources/assets/config/playerStats.txt"));
        player.setStats(playerStats);

        if(Game.isConnectedToServer()){
            // TODO make some kind of lobby
            TextRenderer.printText(Color.getColor("blue") + "Waiting for players to join, press enter to start the game...");
            input.nextLine();
        }
        else {
            TextRenderer.printText("Enter your name " + Color.getColor("blue") + "PLAYER" + Color.resetColor() + ": ");
            player.setName(input.nextLine());
        }

        TextRenderer.skipLine();
        TextRenderer.printText("Entering " + Color.getColor("red") + "DUNGEON" +  Color.resetColor() + "...");
        TextRenderer.skipLine();

        Dungeon.resetDungeon();
        DungeonGenerator.generateDungeon();
        gameInitialized = true;
    }

    @Override
    public void updateState(Scanner input) {
        if(!gameInitialized){
            initializeGame(input);
        }

        player.getInput(input);
    }
}
