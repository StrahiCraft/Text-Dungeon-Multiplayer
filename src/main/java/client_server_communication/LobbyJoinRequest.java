package client_server_communication;

import java.io.Serializable;
import java.util.UUID;

public class LobbyJoinRequest implements Serializable {
    // TODO change lobbyCode when the load-balancer is added so that it can work with multiple servers
    private final UUID lobbyCode;
    private final UUID joinerId;

    public LobbyJoinRequest(UUID lobbyCode, UUID joinerId) {
        this.lobbyCode = lobbyCode;
        this.joinerId = joinerId;
    }

    public UUID getLobbyCode() {
        return lobbyCode;
    }

    public UUID getJoinerId() {
        return joinerId;
    }
}
