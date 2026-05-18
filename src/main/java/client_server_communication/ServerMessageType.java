package client_server_communication;

public enum ServerMessageType {
    DISCONNECT,

    REGISTER,
    REGISTER_FAIL,
    REGISTER_SUCCESS,

    LOGIN,
    LOGIN_FAIL,
    LOGIN_FAIL_ALREADY_LOGGED_IN,
    LOGIN_SUCCESS,
    LOGOUT,

    JOIN,
    JOIN_FAIL,
    JOIN_SUCCESS,

    CREATE_LOBBY,
    UPDATE_LOBBY,

    DISBAND_LOBBY,
    LOBBY_DISBANDED,
    LEAVE_LOBBY,

    START_GAME,
}
