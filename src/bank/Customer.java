package bank;

public class Customer extends Person {

	private String customerId;
	private String username;
	private String password;
	private boolean priviliged;
	public Account account;
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Customer(int age, String firstName, String lastName, String email, long mobileNumber) {
		super(age, firstName, lastName, email, mobileNumber);
		// TODO Auto-generated constructor stub
	}
	
	public Customer(int age, String firstName, String lastName, Address address, String email, long mobileNumber) {
		super(age, firstName, lastName, address, email, mobileNumber);
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isPriviliged() {
		return priviliged;
	}
	public void setPriviliged(boolean priviliged) {
		this.priviliged = priviliged;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "\t\t" + username + "\t\t" + priviliged + "\t" + account + "]";
	}

	
}