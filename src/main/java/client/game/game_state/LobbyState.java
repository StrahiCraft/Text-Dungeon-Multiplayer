package client.game.game_state;

import client.game.ClientApplication;
import client.game.Game;
import client.graphics.Color;
import client.graphics.TextRenderer;
import client_server_communication.ServerMessageType;

import java.util.Scanner;

public class LobbyState extends GameState{
    private final boolean host;

    public LobbyState(boolean host) {
        this.host = host;
    }

    @Override
    public void updateState(Scanner input) {
        if(host){
            ClientApplication.getClientInstance().createLobbyData();
            TextRenderer.printText(Color.getColor("green") + "The lobby code is " + Color.resetColor() + ClientApplication.getClientInstance().getClientId() +
                    Color.getColor("green") + " give this to other players for them to join the game.");
            TextRenderer.printText(Color.getColor("blue") + "Waiting for players to join, type in " + Color.getColor("bright green") +
                    "start" + Color.resetColor() + " to start the game, or " + Color.getColor("red") + "quit" + Color.resetColor() +
                    " to disband the lobby...");

            String command = input.nextLine();

            if(command.equalsIgnoreCase("start")){
                ClientApplication.getClientInstance().getLobbyData().onGameStarted();
                Game.changeState(new DefaultGameState());
                ClientApplication.getClientInstance().getLobbyData().generatePlayerData();
                ClientApplication.getClientInstance().sendMessage(ServerMessageType.START_GAME, ClientApplication.getClientInstance().getLobbyData());
                return;
            }
            if(command.equalsIgnoreCase("quit")){
                Game.changeState(new MainMenuState());
                ClientApplication.getClientInstance().getLobbyData().removePlayer(ClientApplication.getClientInstance().getClientId());
                ClientApplication.getClientInstance().sendMessage(ServerMessageType.DISBAND_LOBBY, ClientApplication.getClientInstance().getLobbyData());
                ClientApplication.getClientInstance().clearLobbyData();
            }
        }
        else {
            TextRenderer.printText("Type in " + Color.getColor("red") + "leave" + Color.resetColor() + " to leave the lobby");

            TextRenderer.printText("Current list of players:");
            TextRenderer.printText(ClientApplication.getClientInstance().getLobbyData().getPlayerList());

            TextRenderer.skipLine();
            String command =  input.nextLine();

            if(command.equalsIgnoreCase("leave")){
                Game.changeState(new MainMenuState());
                ClientApplication.getClientInstance().getLobbyData().removePlayer(ClientApplication.getClientInstance().getClientId());
                ClientApplication.getClientInstance().sendMessage(ServerMessageType.LEAVE_LOBBY, ClientApplication.getClientInstance().getLobbyData());
                ClientApplication.getClientInstance().clearLobbyData();
            }
        }
    }
}
