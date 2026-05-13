package client.game;

public class ClientApplication {
    private static Client clientInstance;

    public static void main(String[] args) {
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
