package client.game;

import client.dungeon.DungeonGenerator;
import client.entity.player.Player;
import client.game.game_state.LobbyState;
import client.game.game_state.LoginState;
import client.game.game_state.MainMenuState;
import client.graphics.Color;
import client.graphics.TextRenderer;
import client_server_communication.LobbyData;
import client_server_communication.ServerMessage;
import client_server_communication.ServerMessageType;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class Client extends Thread {
    /**
     * Socket used to connect to the server
     */
    private Socket socket;

    /**
     * IP address of the client (currently set to localhost)
     */
    private final String IP = "127.0.0.1";
    /**
     * Port of the server the client will connect to
     */
    private final int PORT = 25655;

    /**
     * The unique id every client is given by the server upon connecting
     */
    private UUID clientId;

    /**
     * Name of the player connecting to the server through this client
     */
    private String playerUsername;

    private LobbyData lobbyData;

    /**
     * Output stream for sending objects to the server
     */
    private ObjectOutputStream objectOutputStream;
    /**
     * Input stream for receiving objects from the server
     */
    private ObjectInputStream objectInputStream;

    public Client() {
        try{
            socket = new Socket(IP, PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e){
            TextRenderer.printText(Color.getColor("red") + "Server not found...");
        }
    }

    /**
     * This is called when first trying to connect to the server, if the connection is successful, the game scene
     * will be switched to the log in scene, if the connection fails, the scene remains as the connection failed scene
     */
    public void attemptConnectionToServer(){
        try{
            objectOutputStream.writeObject("Trying to connect client");

            clientId = (UUID) objectInputStream.readObject();
            Game.setConnectedToServer(true);
            TextRenderer.printText(Color.getColor("green") + "Connected to the server!");
            Game.startGameOnline();
        }
        catch (Exception e){
            TextRenderer.printText(Color.getColor("red") + "Connection to server failed...");
            Game.setConnectedToServer(false);
            Game.startGameOffline();
            interrupt();
        }
    }

    public void logOut(){
        sendMessage(ServerMessageType.LOGOUT, playerUsername);
        playerUsername = "";
    }

    public void disconnect() {
        logOut();
        sendMessage(ServerMessageType.DISCONNECT);
        Game.setConnectedToServer(false);
        interrupt();
    }

    /**
     * Sends a message to the server with no additional data
     * @param messageType Type of message being sent to the server
     */
    public void sendMessage(ServerMessageType messageType){
        sendMessage(messageType, null);
    }

    /**
     * Sends a message to the server with additional data in the form of an Object
     * @param messageType Type of message being sent to the server
     * @param data Data sent to the server
     */
    public void sendMessage(ServerMessageType messageType, Object data){
        try{
            objectOutputStream.writeObject(new ServerMessage(clientId, messageType, data));
            objectOutputStream.reset();
            objectOutputStream.flush();
        }
        catch (Exception e){
            System.out.println("Connection to server failed, no message was sent.");
        }
    }

    /**
     * Runs all client side client-server logic after successful connection to the server
     */
    @Override
    public void run() {
        try {
            attemptConnectionToServer();

            while (!Thread.currentThread().isInterrupted()){
                if(objectInputStream == null){
                    continue;
                }

                ServerMessage receivedMessage = (ServerMessage) objectInputStream.readObject();

                if(receivedMessage == null){
                    continue;
                }

                switch (receivedMessage.getMessageType()){
                    case REGISTER_SUCCESS -> onRegister(true, receivedMessage);
                    case REGISTER_FAIL -> onRegister(false, receivedMessage);
                    case LOGIN_SUCCESS -> onLogin(true, receivedMessage);
                    case LOGIN_FAIL -> onLogin(false, receivedMessage);
                    case LOGIN_FAIL_ALREADY_LOGGED_IN -> onAlreadyLoggedIn();
                    case JOIN_SUCCESS -> onJoin(true, receivedMessage);
                    case JOIN_FAIL -> onJoin(false, receivedMessage);
                    case UPDATE_LOBBY -> updateLobbyData(receivedMessage);
                    case LOBBY_DISBANDED -> onLobbyDisbanded();
                }
            }

            if(objectOutputStream != null){
                objectOutputStream.close();
            }
            if(objectInputStream != null){
                objectInputStream.close();
            }
        }
        catch (EOFException e){
            TextRenderer.skipLine();
            TextRenderer.printText(Color.getColor("red") + "Disconnecting from the server...");
            TextRenderer.skipLine();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onRegister(boolean success, ServerMessage messageData){
        if(success){
            TextRenderer.printText(Color.getColor("green") + "Account successfully registered!");
            playerUsername = messageData.getMessageData().toString();
            Game.changeState(new MainMenuState());
            return;
        }
        TextRenderer.printText(Color.getColor("red") + "Registration failed, account already exists!");
        Game.changeState(new LoginState());
    }

    private void onLogin(boolean success, ServerMessage messageData){
        if(success){
            TextRenderer.printText(Color.getColor("green") + "Login successful!");
            playerUsername = messageData.getMessageData().toString();
            Game.changeState(new MainMenuState());
            return;
        }
        TextRenderer.printText(Color.getColor("red") + "Login failed, account details are incorrect!");
        Game.changeState(new LoginState());
    }

    private void onAlreadyLoggedIn(){
        TextRenderer.printText(Color.getColor("red") + "Login failed, account is already logged in!");
        Game.changeState(new LoginState());
    }

    private void onJoin(boolean success, ServerMessage messageData){
        if(success){
            TextRenderer.printText(Color.getColor("green") + "Successfully joined the lobby!");
            lobbyData = (LobbyData) messageData.getMessageData();
            Game.changeState(new LobbyState(false));
            return;
        }
        TextRenderer.printText(Color.getColor("red") + "Failed to join the lobby, returning to main menu...");
        Game.changeState(new MainMenuState());
    }

    public void createLobbyData() {
        lobbyData = new LobbyData(clientId, playerUsername);
        lobbyData.setDungeonData(DungeonGenerator.generateDungeon());
        sendMessage(ServerMessageType.CREATE_LOBBY, lobbyData);
    }

    private void onLobbyDisbanded(){
        TextRenderer.printText(Color.getColor("red") + "Lobby has been disbanded, press enter to return to the menu...");
        Game.changeState(new MainMenuState());
        sendMessage(ServerMessageType.LOBBY_DISBANDED);
        lobbyData = null;
    }

    private void updateLobbyData(ServerMessage messageData){
        LobbyData newData = (LobbyData) messageData.getMessageData();
        if(newData.isJoinable()){
            checkForNewPlayers(newData);
        }
        checkForPlayersLeaving(newData);
        lobbyData = newData;
    }

    private void checkForNewPlayers(LobbyData newLobbyData) {
        if(newLobbyData.getPlayerNames().size() == lobbyData.getPlayerNames().size()){
            return;
        }
        for(UUID player : newLobbyData.getPlayerNames().keySet()) {
            if(!lobbyData.containsPlayer(player)){
                TextRenderer.printText(newLobbyData.getPlayerNames().get(player) + " joined the game");
                return;
            }
        }
    }

    private void checkForPlayersLeaving(LobbyData newLobbyData){
        if(newLobbyData.getPlayerNames().size() == lobbyData.getPlayerNames().size()){
            return;
        }
        for(UUID player : lobbyData.getPlayerNames().keySet()) {
            if(!newLobbyData.containsPlayer(player)){
                TextRenderer.printText(Color.getColor("red") + lobbyData.getPlayerNames().get(player) + " left the game");
                return;
            }
        }
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public UUID getClientId() {
        return clientId;
    }

    public LobbyData getLobbyData() {
        return lobbyData;
    }

    public void clearLobbyData() {
        lobbyData = null;
    }
}