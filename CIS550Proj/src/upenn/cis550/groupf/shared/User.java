package upenn.cis550.groupf.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User implements IsSerializable {
	private int id;
	private String name;
	private String password;
	private boolean isMale;
	private String email;
	private String phone;
	private String affiliation;
	
	// A default constructor MUST be provided for any class implementing IsSerializable!!!
	// Bloody Lesson! ben
	public User() {
		
	}

	public User(int id, String name, String password, boolean isMale,
			String email, String phone, String affiliation) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.isMale = isMale;
		this.email = email;
		this.phone = phone;
		this.affiliation = affiliation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
