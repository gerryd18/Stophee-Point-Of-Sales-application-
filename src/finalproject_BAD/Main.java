package finalproject_BAD;

import java.util.Vector;

public class Main {
Vector<User> userVector = new Vector<>();

	public Main() {
		DatabaseConnection db = new DatabaseConnection();	
		new Login(db);
	}

	public static void main(String[] args) {
		new Main();	
		
	}
}
