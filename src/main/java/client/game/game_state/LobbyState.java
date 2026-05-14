package client.game.game_state;

import client.game.ClientApplication;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.util.Scanner;

public class LobbyState extends GameState{
    private final boolean host;

    public LobbyState(boolean host){
        this.host = host;
    }

    @Override
    public void updateState(Scanner input) {
        if(host){
            // TODO make some kind of lobby
            TextRenderer.printText(Color.getColor("green") + "The lobby code is " + Color.resetColor() + ClientApplication.getClientInstance().getClientId() +
                    Color.getColor("green") + " give this to other players for them to join the game.");
            TextRenderer.printText(Color.getColor("blue") + "Waiting for players to join, press enter to start the game...");
            input.nextLine();
            Game.changeState(new PlayingGameState());
            // TODO send message to start game to all other players
        }
        else {
            TextRenderer.printText(Color.getColor("green") + "Successfully joined the lobby!");
            TextRenderer.printText("Current list of players:");
            // TODO print player list
        }
    }
}
