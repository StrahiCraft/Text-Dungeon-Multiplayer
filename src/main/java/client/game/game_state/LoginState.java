package client.game.game_state;

import client.game.ClientApplication;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;
import client_server_communication.ServerMessageType;

import java.util.Scanner;

public class LoginState extends GameState {
    @Override
    public void updateState(Scanner input) {
        renderLoginScreen();

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
        if(commandParameters.length == 3){
            if(commandParameters[0].equalsIgnoreCase("register")){
                String[] registerParameters = { commandParameters[1], commandParameters[2] };
                ClientApplication.getClientInstance().sendMessage(ServerMessageType.REGISTER, registerParameters);

                TextRenderer.printText(Color.getColor("yellow") + "Attempting to register account...");
                Game.changeState(new DefaultGameState());
                return;
            }
            if(commandParameters[0].equalsIgnoreCase("login")){
                String[] loginParameters = { commandParameters[1], commandParameters[2] };
                ClientApplication.getClientInstance().sendMessage(ServerMessageType.LOGIN, loginParameters);

                TextRenderer.printText(Color.getColor("yellow") + "Attempting to log into account...");
                Game.changeState(new DefaultGameState());
            }
        }
    }

    private void renderLoginScreen(){
        TextRenderer.printText("Welcome to the login screen, type in " + Color.getColor("green") + "login" + Color.resetColor() +
                " <" + Color.getColor("yellow") + "username" + Color.resetColor() + "> <" + Color.getColor("yellow") +
                "password" + Color.resetColor() + "> to log in to your existing account.");

        TextRenderer.printText("If you don't have an account, create one by typing in " + Color.getColor("blue") + "register" + Color.resetColor() +
                " <" + Color.getColor("yellow") + "username" + Color.resetColor() + "> <" + Color.getColor("yellow") +
                "password" + Color.resetColor() + ">");

        TextRenderer.printText("If you wish to play offline, type in " + Color.getColor("yellow") + "offline");
        TextRenderer.printText("Type in " + Color.getColor("red") + "quit" + Color.resetColor() + " to quit the game");
    }
}
