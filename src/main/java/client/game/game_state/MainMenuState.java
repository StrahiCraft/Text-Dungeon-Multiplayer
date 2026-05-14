package client.game.game_state;

import client.game.ClientApplication;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.util.Scanner;

public class MainMenuState extends GameState {
    @Override
    public void updateState(Scanner input) {
        renderMainMenu();
        String command = input.nextLine();

        if(command.equalsIgnoreCase("quit")) {
            TextRenderer.printText("Goodbye!");
            Game.quitGame();
            return;
        }

        if(command.equalsIgnoreCase("start")) {
            Game.changeState(new PlayingGameState());
            return;
        }

        if(Game.isConnectedToServer()){
            if(command.equalsIgnoreCase("logout")){
                ClientApplication.getClientInstance().logOut();
                Game.changeState(new LoginState());
                return;
            }
            if(command.equalsIgnoreCase("offline")){
                ClientApplication.getClientInstance().disconnect();
            }
        }
        else {
            if(command.equalsIgnoreCase("reconnect")){
                TextRenderer.printText(Color.getColor("yellow") + "Attempting to reconnect to the server...");
                ClientApplication.connectToServer();
                Game.changeState(new DefaultGameState());
            }
        }
    }

    private static void renderMainMenu() {
        TextRenderer.skipLine();

        TextRenderer.printText("Welcome to the " + Color.getColor("green") + "TEXT DUNGEON" + Color.resetColor() + "!");
        TextRenderer.printText("\nType in " + Color.getColor("green") + "start" + Color.resetColor() + " to start the game");

        if(Game.isConnectedToServer()){
            TextRenderer.printText("\nType in " + Color.getColor("yellow") + " logout " + Color.resetColor() + " to log out of the current account");
            TextRenderer.printText("\nType in " + Color.getColor("yellow") + " offline " + Color.resetColor() + " to enter offline mode");
        }

        if(!Game.isConnectedToServer()){
            TextRenderer.printText("\nType in " + Color.getColor("blue") + "reconnect" + Color.resetColor() + " to attempt to reconnect to the server");
        }

        TextRenderer.printText("\nType in " + Color.getColor("red") + "quit" + Color.resetColor() + " to quit game");
    }
}
