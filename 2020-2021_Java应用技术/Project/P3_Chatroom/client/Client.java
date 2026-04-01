package client;

import java.io.*;
import java.net.*;

public class Client extends Thread {
    private BufferedReader input;
    private PrintWriter output;
    private Socket socket;
    // private boolean stop;

    public Client() {
        try {
            socket = new Socket("127.0.0.1", 8080); // nc localhost 8080
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
    }

    public void login() {
        try {
            int Login = 0;
            String line;
            while (Login < 2) {
                // get the input
                if ((line = input.readLine()) != null) {
                    System.out.println(line);
                }
                // send the message
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String strName;
                strName = bufferedReader.readLine();
                output.println(strName);
                Login++;
            }
            chat();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void chat() {
        start();// create a thread
        String line;
        // read in messages
        try {
            while (true) {
                line = input.readLine();
                if (line != null) {
                    System.out.println(line);
                    // if it means the client is closed
                    if ((line.equals("Fail to login because of wrong password!"))
                            || (line.equals("Fails to login as it's already in the chatroom!")
                                    || line.equals("You've left the chatroom!"))) {
                        socket.close();
                        // stop the program
                        System.exit(0);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {
        while (true) {
            // send messages
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String strName;
            try {
                strName = bufferedReader.readLine();
                output.println(strName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
