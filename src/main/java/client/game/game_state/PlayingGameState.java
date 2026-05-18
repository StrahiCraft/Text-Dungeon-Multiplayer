package client.game.game_state;

import client.entity.player.Player;
import client.game.ClientApplication;
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

        String playerName;

        if(Game.isConnectedToServer()){
            playerName = ClientApplication.getClientInstance().getPlayerUsername();
        }
        else {
            TextRenderer.printText("Enter your name " + Color.getColor("blue") + "PLAYER" + Color.resetColor() + ": ");
            playerName = input.nextLine();
        }

        TextRenderer.skipLine();
        TextRenderer.printText("Entering " + Color.getColor("red") + "DUNGEON" +  Color.resetColor() + "...");
        TextRenderer.skipLine();

        player = new Player();
        Stats playerStats = new Stats();
        playerStats.interpretFileData(FileReader.readFile("src/main/resources/assets/config/playerStats.txt"));
        player.setStats(playerStats);
        player.setName(playerName);

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
