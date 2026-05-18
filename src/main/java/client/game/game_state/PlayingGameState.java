package client.game.game_state;

import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.util.Scanner;

public class PlayingGameState extends GameState{
    private boolean gameInitialized = false;

    private void initializeGame(Scanner input){

        if(!Game.isConnectedToServer()){
            TextRenderer.printText("Enter your name " + Color.getColor("blue") + "PLAYER" + Color.resetColor() + ": ");
            String playerName = input.nextLine();
            Game.getPlayer().setName(playerName);
        }

        TextRenderer.skipLine();
        TextRenderer.printText("Entering " + Color.getColor("red") + "DUNGEON" +  Color.resetColor() + "...");
        TextRenderer.skipLine();

        gameInitialized = true;
    }

    @Override
    public void updateState(Scanner input) {
        if(!gameInitialized){
            initializeGame(input);
        }

        Game.getPlayer().getInput(input);
    }
}
