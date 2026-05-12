package client.entity.player.states;

import client.entity.player.Player;

import java.util.Scanner;

public class PlayerPreCombat extends PlayerState {

    public PlayerPreCombat() {
        super();
        possibleCommands.add("fight");
        possibleCommands.add("flee");
    }

    @Override
    public boolean checkInput(String inputText, Scanner input) {
        if(inputText.equals("fight")){
            Player.Instance.setCurrentState(new PlayerInCombat());
            return true;
        }

        if(inputText.equals("flee")){
            Player.Instance.setCurrentRoom(Player.Instance.getPreviousRoom());
            return true;
        }

        return false;
    }
}
