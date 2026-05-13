package client.game;

public class ClientApplication {
    private static Client clientInstance;

    public static void main(String[] args) {
        Game.initializeGame();
        connectToServer();
    }

    public static void connectToServer(){
        if(clientInstance != null) {
            clientInstance = null;
        }

        clientInstance = new Client();
        clientInstance.start();
    }

    public static Client getClientInstance() {
        return clientInstance;
    }
}
