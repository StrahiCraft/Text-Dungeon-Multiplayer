package client.game.game_state;

import client.game.ClientApplication;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;

import java.util.Scanner;

public class LoginState extends GameState {
    @Override
    public void updateState(Scanner input) {
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
                ClientApplication.getClientInstance().disconnect();
                Game.changeState(new MainMenuState());
                return;
            }
            if(commandParameters[0].equalsIgnoreCase("quit")){
                ClientApplication.getClientInstance().disconnect();
                TextRenderer.printText("Goodbye!");
                Game.quitGame();
            }
        }
    }
}
