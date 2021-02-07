package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class ClientEvents implements ClientListener {
    public void castMsg(UserInfo sender, String msg) throws IOException {
        msg = sender.getName() + ": " + msg; // add the name of the speaker in front
        for (int i = 0; i < Server.serverThreadList.size(); i++) {
            ServerThread serverThread = Server.serverThreadList.get(i);
            serverThread.sendMsg2Client(msg);
        }
    }

    public void addClient(ServerThread serverThread) throws IOException {
        Server.serverThreadList.add(serverThread);
        System.out.println(serverThread.getOwerUser().getName() + " enters the chatroom! Current number: "
                + Server.serverThreadList.size());
        castMsg(serverThread.getOwerUser(), "Enter the chatroom! Current number: " + Server.serverThreadList.size());
    }

    public void removeClient(ServerThread serverThread) throws IOException {
        Server.serverThreadList.remove(serverThread);
        System.out.println(serverThread.getOwerUser().getName() + " leaves the chatroom! Current number: "
                + Server.serverThreadList.size());
        castMsg(serverThread.getOwerUser(), "Leave the chatroom! Current number: " + Server.serverThreadList.size());
    }
}

public class Server {
    static final int PORT = 8080;
    static ArrayList<ServerThread> serverThreadList = new ArrayList<ServerThread>();
    Server chatServer = new Server();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server created successfully! Port number is " + PORT); // print out the port number

        try {
            while (true) {
                // Blocks until a connection occurs:
                Socket socket = server.accept();
                System.out.println("A client connection entered: " + socket.getRemoteSocketAddress().toString());
                ServerThread serverThread = new ServerThread(socket);
                serverThread.addClientListener(new ClientEvents());
                serverThread.start();
            }
        } finally {
            server.close();
        }
    }
}