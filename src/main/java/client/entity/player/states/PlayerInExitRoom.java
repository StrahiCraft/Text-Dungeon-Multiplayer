package client.entity.player.states;

import client.dungeon.Dungeon;
import client.entity.player.Player;
import client.graphics.Color;

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

        Dungeon.progressFloor();
        Player.Instance.increaseScore(10);
        return true;
    }
}
