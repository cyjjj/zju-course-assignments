package server;

import java.io.*;
import java.net.Socket;

// create a thread for each client
public class ServerThread extends Thread {
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private UserInfo user;

	private ClientListener listener;

	void addClientListener(ClientListener listener) {
		this.listener = listener;
	}

	public ServerThread(Socket client) {
		this.client = client;
	}

	public UserInfo getOwerUser() {
		return this.user;
	}

	public void run() {
		try {
			processSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processSocket() throws IOException {
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

		// login
		sendMsg2Client("Welcome to the chatroom! Please enter your user name:");
		String userName = in.readLine();
		System.out.println("Login name: " + userName);
		sendMsg2Client("Please enter your password:");
		String pwd = in.readLine();
		System.out.println("Login password: " + pwd);
		user = new UserInfo(userName, pwd);
		int loginState = UserDB.checkLogin(user);// check the user in the database
		if (loginState == 0) {
			// password error
			sendMsg2ServerClient("Fail to login because of wrong password!");
			client.close();
			return;
		} else if (loginState == 1) {
			// user exits and password correct
			sendMsg2ServerClient("Login successfully!");
		} else if (loginState == 2) {
			// user not exit so create it
			sendMsg2ServerClient("New user is added and logins successfully!");
		} else if (loginState == 3){
			// the user is already in the chatroom
			sendMsg2ServerClient("Fails to login as it's already in the chatroom!");
			client.close();
			return;
		}

		// enter the chatroom and begin chatting
		listener.addClient(this); // add the client thread into the thread array
		String input = in.readLine();
		while (!input.equals("bye")) {
			System.out.println(this.user.getName() + ": " + input); // print info on Server
			listener.castMsg(this.user, input); // send message to all
			input = in.readLine();
		}

		// if type "bye", leave the chatroom
		sendMsg2Client("You've left the chatroom!");
		UserDB.setOffline(this.getOwerUser().getName());
		System.out.println(this.getOwerUser().getName() + " has left the chatroom.");
		listener.removeClient(this);
		client.close();
	}

	// print the message received on Client
	public void sendMsg2Client(String msg) throws IOException {
		out.println(msg);
	}

	// print the message received on Server and Client
	public void sendMsg2ServerClient(String msg) throws IOException {
		out.println(msg);
		System.out.println(msg);
	}
}