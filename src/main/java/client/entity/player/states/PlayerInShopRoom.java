package client.entity.player.states;

import client.entity.player.Player;
import client.game.Game;
import client.graphics.Color;

import java.util.Scanner;

public class PlayerInShopRoom extends PlayerWandering {
    public PlayerInShopRoom(){
        super();
        possibleCommands.add(Color.getColor("magenta") + "shop" + Color.resetColor());
    }

    @Override
    public boolean checkInput(String inputText, Scanner input) {
        if(super.checkInput(inputText, input)){
            return true;
        }

        if(!inputText.equals("shop")){
            return false;
        }

        Game.getPlayer().setCurrentState(new PlayerShopping());
        return true;
    }
}
