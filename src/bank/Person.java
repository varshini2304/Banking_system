package bank;

public class Person {
	private int age;
	private String firstName;
	private String lastName;
	private Address address;
	private String email;
	private long mobileNumber;

	public Person() {
		super();
	}
	public Person(int age, String firstName, String lastName, String email, long mobileNumber) {
		super();
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}
	public Person(int age, String firstName, String lastName, Address address, String email, long mobileNumber) {
		super();
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}
	@Override
	public String toString() {
		return age+"\t"+firstName+"\t"+lastName+"\t"+address+"\t\t\t\t"+email+"\t\t\t\t"+mobileNumber;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
