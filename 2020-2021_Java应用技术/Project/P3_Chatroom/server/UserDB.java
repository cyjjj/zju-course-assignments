package server;

import java.util.HashMap;
import java.util.Map;

class UserInfo {
	// private String ip;
	private String name; // the name of the user
	private String password; // the password of the user
	private boolean isOnline = false; // whether the user is already in the chatroom

	public UserInfo(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public UserInfo(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setState(boolean s) {
		this.isOnline = s;
	}

	public boolean getState() {
		return isOnline;
	}
}

public class UserDB {
	// the database of users
	private static Map<String, UserInfo> UserMap = new HashMap<String, UserInfo>();

	// initial users and corresponding passwords, e.g. user1 pwd1
	// automatically put them into UserMap when the program starts
	static {
		for (int i = 1; i <= 10; i++) {
			UserInfo userInfo = new UserInfo("user" + i, "pwd" + i);
			UserMap.put(userInfo.getName(), userInfo);
		}
	}

	public static void setOffline(String name) {
		if (UserMap.containsKey(name))
			UserMap.get(name).setState(false);
	}

	// check login name and password
	public static int checkLogin(UserInfo user) {
		if (UserMap.containsKey(user.getName())) {
			// already exit the user
			UserInfo rightUserInfo = UserMap.get(user.getName());
			String rightUserPassword = rightUserInfo.getPassword();
			// check the password
			if (rightUserPassword.equals(user.getPassword()) && !rightUserInfo.getState()) {
				UserMap.get(user.getName()).setState(true);
				return 1; // 1 means user exits and password correct
			} else if (rightUserPassword.equals(user.getPassword()) && rightUserInfo.getState()) {
				return 3; // 3 means the user is already in the chatroom
			}
		} else {
			user.setState(true);
			UserMap.put(user.getName(), user);
			return 2; // 2 means user not exit so create it
		}
		return 0; // 0 means password error
	}
}