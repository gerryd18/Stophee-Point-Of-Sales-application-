package finalproject_BAD;

public class User {
	private String userName, userEmail, userPassword, userPhone, userGender, userRole;

	public User(String userName, String userEmail, String userPassword, String userPhone, String userGender,
			String userRole) {
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.userGender = userGender;
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public String getUserGender() {
		return userGender;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}
