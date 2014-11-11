package persistant;

import java.util.Date;

public class User {

	private String username;
	private String password;
	private String name;
	private String surname;
	private Date dob;
	private String creditCardNumber;
	private Date expiry;
	private String cvv;
	private boolean premium;
	private boolean free;

	public User(String username, String password, String name, String surname,
			Date dob, String creditCardNumber, Date expiry, String cvv,
			boolean premium, boolean free) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.dob = dob;
		this.creditCardNumber = creditCardNumber;
		this.expiry = expiry;
		this.cvv = cvv;
		this.premium = premium;
		this.free = free;
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

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
		this.free = !premium;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
		this.premium = !free;
	}

}
