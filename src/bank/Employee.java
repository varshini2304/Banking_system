package bank;

public class Employee extends Person {

	private String designation;
	private double salary;
	private String userName;
	private String password;


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(int age, String firstName, String lastName, String email, long mobileNumber) {
		super(age, firstName, lastName, email, mobileNumber);
		// TODO Auto-generated constructor stub
	}
	
	public Employee(int age, String firstName, String lastName, String email, long mobileNumber, String userName,String password,String designation,double salary) {
		super(age, firstName, lastName, email, mobileNumber);
		this.password=password;
		this.userName= userName;
		this.designation=designation;
		this.salary= salary;
		// TODO Auto-generated constructor stub
	}
	
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		super.toString();
		return "\t" + designation + "\t\t" + salary + ", userName=" + userName + "\n";
	}
	
}
