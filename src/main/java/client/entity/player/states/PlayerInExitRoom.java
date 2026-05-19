package client.entity.player.states;

import client.dungeon.DungeonGenerator;
import client.game.ClientApplication;
import client.game.Game;
import client.graphics.Color;
import client_server_communication.ServerMessageType;

import java.util.Scanner;

public class PlayerInExitRoom extends PlayerWandering{
    public PlayerInExitRoom(){
        super();
        possibleCommands.add(Color.getColor("green") + "descend" + Color.resetColor());
    }

    @Override
    public boolean checkInput(String inputText, Scanner input) {
        if(super.checkInput(inputText, input)){
            return true;
        }

        if(!inputText.equals("descend")){
            return false;
        }

        Game.getDungeon().progressFloor();
        if(Game.isConnectedToServer()){
            ClientApplication.getClientInstance().getLobbyData().setDungeonData(DungeonGenerator.generateDungeon(Game.getDungeon().getDungeonStats()));
            ClientApplication.getClientInstance().getLobbyData().progressPlayersToNextFloor();
            ClientApplication.getClientInstance().sendMessage(ServerMessageType.UPDATE_LOBBY);
        }
        else {
            Game.generateOfflineDungeon(Game.getDungeon().getDungeonStats());
            Game.getPlayer().setCurrentRoom(Game.getDungeon().getStartingRoom());
        }
        return true;
    }
}
