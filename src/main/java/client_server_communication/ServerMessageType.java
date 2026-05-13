package client_server_communication;

public enum ServerMessageType {
    DISCONNECT,

    REGISTER,
    REGISTER_FAIL,
    REGISTER_SUCCESS,

    LOGIN,
    LOGIN_FAIL,
    LOGIN_SUCCESS,

}
