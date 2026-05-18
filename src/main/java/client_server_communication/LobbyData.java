package client_server_communication;

import client.dungeon.Dungeon;
import client.graphics.Color;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * Data for lobbies, each lobby contains a list of the connected clients, the account names of the players logging in
 * from those clients, the name of the lobby, minefield settings and the current minefield being played on in that lobby
 */
public class LobbyData implements Serializable {
    /**
     * List of clients currently connected to this lobby
     */
    private HashMap<UUID, String> players;
    private Dungeon dungeonData;

    private boolean joinable = true;

    public LobbyData(UUID host, String hostName) {
        players = new HashMap<>();
        players.put(host, hostName);
        dungeonData = new Dungeon();
    }

    /**
     * Checks if this lobby contains a player with the given client id
     * @param clientId The client id of the player for which we are checking if they are in the lobby
     * @return True if the lobby contains the player with the given client id, false otherwise
     */
    public boolean containsPlayer(UUID clientId){
        return players.containsKey(clientId);
    }

    /**
     * Adds the given player to the lobby
     * @param clientId The client id of the player being added to the lobby
     * @param clientName The name of the player being added to the lobby
     */
    public void addPlayer(UUID clientId, String clientName){
        players.put(clientId, clientName);
    }

    /**
     * Removes the given player from the lobby
     * @param clientId The client id of the player that is being removed from the lobby
     */
    public void removePlayer(UUID clientId){
        players.remove(clientId);
    }

    public String getPlayerList(){
        StringBuilder playerList = new StringBuilder("-" + Color.getColor("bright blue") + "<host> " + Color.resetColor());
        for(String player : players.values()){
            playerList.append("-").append(player).append("\n");
        }
        return playerList.toString();
    }

    public Dungeon getDungeonData() {
        return dungeonData;
    }

    public void setDungeonData(Dungeon dungeonData) {
        this.dungeonData = dungeonData;
    }

    public void onGameStarted(){
        joinable = false;
    }

    public boolean isJoinable() {
        return joinable;
    }

    public HashMap<UUID, String> getPlayers() {
        return players;
    }
}