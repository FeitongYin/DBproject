package upenn.cis550.groupf.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	// login
	private String userName;
	// nick name to be displayed
	private String name;
		
	private String firstName;
	private String lastName;
	private String password;
	private boolean isMale;
	private Date dob;
	private String email;
	private String phone;
	private String affiliation;
	
	// A default constructor MUST be provided for any class implementing IsSerializable!!!
	// Bloody Lesson! ben
	public User() {
		
	}

	public User(String userName, String name, String firstName, String lastName, String password, boolean isMale,
			Date dob, String email, String phone, String affiliation) {
		setUserName(userName);
		setName(name);
		setFirstName(firstName);
		setLastName(lastName);
		setPassword(password);
		setMale(isMale);
		setDob(dob);
		setEmail(email);
		setPhone(phone);
		setAffiliation(affiliation);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

}
