package client_server_communication;

import java.io.Serializable;
import java.util.UUID;

/**
 * Class used for sending messages to the server or back to the client using client ID
 */
public class ServerMessage implements Serializable {
    /**
     * ID of the player sending / receiving the message
     */
    private UUID clientId;
    /**
     * The message being sent / received
     */
    private ServerMessageType messageType;

    /**
     * Data the message is sending, can be any object
     */
    private Object messageData;

    public ServerMessage(UUID clientId, ServerMessageType message, Object messageData) {
        this.clientId = clientId;
        this.messageType = message;
        this.messageData = messageData;
    }

    public ServerMessage(UUID clientId, ServerMessageType message) {
        this.clientId = clientId;
        this.messageType = message;
        messageData = null;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public ServerMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(ServerMessageType messageType) {
        this.messageType = messageType;
    }

    public Object getMessageData() {
        return messageData;
    }

    public void setMessageData(Object messageData) {
        this.messageData = messageData;
    }
}