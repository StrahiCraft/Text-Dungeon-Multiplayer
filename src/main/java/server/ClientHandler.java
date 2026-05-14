package server;

import client_server_communication.ServerMessage;
import client_server_communication.ServerMessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * Server side class for handling incoming client messages and sending back server responses
 */
public class ClientHandler extends Thread {
    /**
     * Clients connection socket that connects the client to the server
     */
    private final Socket clientSocket;
    /**
     * Unique id given to the client upon connection with the server, the id is assigned by the server
     */
    private final UUID clientId;
    /**
     * Username of the currently logged in account on this client
     */
    private String clientAccountUsername;

    /**
     * Output stream for sending objects to the client from the server
     */
    private ObjectOutputStream objectOutputStream;
    /**
     * Input stream for receiving objects from the client
     */
    private ObjectInputStream objectInputStream;

    public ClientHandler(Socket clientSocket, UUID clientId, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream){
        this.clientSocket = clientSocket;
        this.clientId = clientId;

        try {
            this.objectOutputStream = objectOutputStream;
            this.objectInputStream = objectInputStream;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the client with no additional data
     * @param messageType Type of message being sent to the client
     */
    private void sendMessage(ServerMessageType messageType){
        sendMessage(messageType, null);
    }

    /**
     * Sends a message to the client with additional data in the form of an Object
     * @param messageType Type of message being sent to the client
     * @param data Data sent to the client
     */
    private void sendMessage(ServerMessageType messageType, Object data){
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
     * Runs all server side client-server logic after successful connection from the client until the client disconnects
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if(objectInputStream == null){
                    continue;
                }

                ServerMessage messageFromClient = (ServerMessage) objectInputStream.readObject();

                if(messageFromClient == null) {
                    continue;
                }
                ServerMessageType messageType = messageFromClient.getMessageType();

                switch (messageType) {
                    case DISCONNECT -> ServerApplication.onClientDisconnected(messageFromClient.getClientId());
                    case REGISTER -> registerPlayer(messageFromClient);
                    case LOGIN -> loginPlayer(messageFromClient);
                    case LOGOUT -> logPlayerOut(messageFromClient);
                    default -> System.out.println("Unknown message type " + messageType);
                }
            }
            objectOutputStream.close();
            if(objectInputStream  != null){
                objectInputStream.close();
            }
            clientSocket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerPlayer(ServerMessage message){
        String[] registerData = (String[])message.getMessageData();
        if(AccountValidation.validRegistration(registerData[0], registerData[1])){
            clientAccountUsername = registerData[0];
            AccountValidation.setLoggedIn(registerData[0], true);
            sendMessage(ServerMessageType.REGISTER_SUCCESS, registerData[0]);
        }
        else {
            sendMessage(ServerMessageType.REGISTER_FAIL);
        }
    }

    private void loginPlayer(ServerMessage message){
        String[] loginData = (String[])message.getMessageData();
        if(AccountValidation.validLogin(loginData[0], loginData[1])){
            if(AccountValidation.notAlreadyLoggedIn(loginData[0])){
                clientAccountUsername = loginData[0];
                AccountValidation.setLoggedIn(loginData[0], true);
                sendMessage(ServerMessageType.LOGIN_SUCCESS, loginData[0]);
                return;
            }
            sendMessage(ServerMessageType.LOGIN_FAIL_ALREADY_LOGGED_IN);
        }
        else {
            sendMessage(ServerMessageType.LOGIN_FAIL);
        }
    }

    private void logPlayerOut(ServerMessage messageData){
        String playerName = messageData.getMessageData().toString();
        AccountValidation.setLoggedIn(playerName, false);
    }
}