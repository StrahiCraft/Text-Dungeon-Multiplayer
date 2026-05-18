package client.entity.player.states;

import client.entity.player.Player;
import client.game.Game;

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
            Game.getPlayer().setCurrentState(new PlayerInCombat());
            return true;
        }

        if(inputText.equals("flee")){
            Game.getPlayer().setCurrentRoom(Game.getPlayer().getPreviousRoom());
            return true;
        }

        return false;
    }
}
