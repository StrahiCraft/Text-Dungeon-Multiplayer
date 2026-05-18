package client.game.game_state;

import client.game.ClientApplication;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;
import client_server_communication.LobbyJoinRequest;
import client_server_communication.ServerMessageType;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class MainMenuState extends GameState {
    @Override
    public void updateState(Scanner input) {
        renderMainMenu();
        String command = input.nextLine();
        String[] commandParameters = command.split(" ");

        if(command.equalsIgnoreCase("quit")) {
            TextRenderer.printText("Goodbye!");
            Game.quitGame();
            return;
        }

        if(command.equalsIgnoreCase("start")) {
            if(Game.isConnectedToServer()){
                Game.changeState(new LobbyState(true));
            }
            else {
                Game.generateOfflineDungeon();
                Game.changeState(new PlayingGameState());
            }
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
            if(commandParameters.length == 2){
                if(commandParameters[0].equalsIgnoreCase("join")){
                    TextRenderer.printText(Color.getColor("yellow") + "Attempting to join lobby...");
                    try{
                        ClientApplication.getClientInstance().sendMessage(ServerMessageType.JOIN,
                                new LobbyJoinRequest(UUID.fromString(commandParameters[1]), ClientApplication.getClientInstance().getClientId()));
                        Game.changeState(new DefaultGameState());
                    }
                    catch (IllegalArgumentException e){
                        TextRenderer.printText(Color.getColor("red") + "Invalid lobby code, returning to menu...");
                        Game.changeState(new MainMenuState());
                    }
                }
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
            TextRenderer.printText("Type in " + Color.getColor("bright blue") + "join" + Color.resetColor() + " <" + Color.getColor("bright yellow") +
                    "lobby code" + Color.resetColor() + "> to join another player's game");
            TextRenderer.printText("\nType in " + Color.getColor("yellow") + " logout " + Color.resetColor() + " to log out of the current account");
            TextRenderer.printText("Type in " + Color.getColor("yellow") + " offline " + Color.resetColor() + " to enter offline mode");
        }

        if(!Game.isConnectedToServer()){
            TextRenderer.printText("\nType in " + Color.getColor("blue") + "reconnect" + Color.resetColor() + " to attempt to reconnect to the server");
        }

        TextRenderer.printText("\nType in " + Color.getColor("red") + "quit" + Color.resetColor() + " to quit game");
    }
}
