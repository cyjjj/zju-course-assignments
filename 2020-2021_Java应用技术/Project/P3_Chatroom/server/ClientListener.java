package server;

import java.io.IOException;

public interface ClientListener {
    // broadcast message to all clients
    void castMsg(UserInfo sender, String msg) throws IOException;

    // add a new client thread entering the chatroom into the array
    void addClient(ServerThread serverThread) throws IOException;

    // remove a client thread leaving the chatroom out of the array
    void removeClient(ServerThread serverThread) throws IOException;
}
