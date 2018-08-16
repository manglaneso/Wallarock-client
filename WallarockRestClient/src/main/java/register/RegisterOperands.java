package register;


import java.io.Serializable;

public class RegisterOperands implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String email;

	private String city;

	private String name;

	private String password;
	
	private String repeatpassword;

	private String surname;
	
	private int admin;
	
	public RegisterOperands() {
		super();
	}
	
	public RegisterOperands(String name, String surname, String email, String password, int admin,
			String city) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.setAdmin(admin);
		this.city = city;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public String getRepeatpassword() {
		return repeatpassword;
	}

	public void setRepeatpassword(String repeatpassword) {
		this.repeatpassword = repeatpassword;
	}
}
