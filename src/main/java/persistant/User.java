package persistant;

import java.util.Date;

public class User {

	private String username;
	private String password;
	private String name;
	private String surname;
	private Date dob;

	public User(String username, String password, String name, String surname,
			Date dob) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.dob = dob;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
}
