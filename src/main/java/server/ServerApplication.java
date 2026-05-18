package server;

import client_server_communication.LobbyData;
import server.database.DatabaseManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

/**
 * Application for the server, this needs to be running for clients to connect and play the game
 */
public class ServerApplication {
    /**
     * Port that the server is hosted on
     */
    public static final int PORT = 25655;

    /**
     * Hashmap of all currently connected clients, each client receives a unique ID and the client handler thread is stored
     * using it as the key
     */
    private static HashMap<UUID, ClientHandler> connectedClients = new HashMap<>();
    private static HashMap<UUID, LobbyData> lobbies = new HashMap<>();

    /**
     * Main function of the server, all client connection logic from the server side is done here
     * @param args
     */
    public static void main(String[] args) {
        if(!DatabaseManager.connect()){
            System.out.println("Connection to database failed!");
            return;
        }
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            try {

                System.out.println("Server started on port " + PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    objectOutputStream.flush();
                    ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                    String clientConnectMessage = objectInputStream.readObject().toString();

                    if (clientConnectMessage != null) {
                        System.out.println(clientConnectMessage);
                        UUID clientId = UUID.randomUUID();
                        objectOutputStream.writeObject(clientId);
                        objectOutputStream.flush();

                        ClientHandler clientHandler = new ClientHandler(clientSocket, clientId, objectInputStream, objectOutputStream);
                        clientHandler.start();

                        connectedClients.put(clientId, clientHandler);

                        System.out.println("Client connected! with ID: " + clientId);
                    } else {
                        System.out.println("Client failed to connect.");
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            DatabaseManager.disconnect();
        }
    }

    /**
     * Removes client data from the list of connected clients after the client disconnects
     * @param clientId ID of the client that has just disconnected
     */
    public static void onClientDisconnected(UUID clientId){
        System.out.println("Client " + clientId + " has disconnected!");
        connectedClients.get(clientId).interrupt();
        connectedClients.remove(clientId);
    }

    /**
     * Gets the handler of the client based on the clients UUID
     * @param clientId The client id of the client we are getting the handler from
     * @return The client handler with the given client id
     */
    public static ClientHandler getClientHandler(UUID clientId){
        return connectedClients.get(clientId);
    }
}