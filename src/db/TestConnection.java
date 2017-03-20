package db;

import impl.UserManager;

public class TestConnection {
	public static void main(String[] args) {
		System.out.println(new UserManager().queryUser("12345", "12345"));
	}
}
