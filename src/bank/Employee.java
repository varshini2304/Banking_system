package bank;

import java.util.List;

public class Employee extends Person {

	private String designation;
	private double salary;
	private String userName;
	private String password;
	private String employeeId;
	private List<Transaction> workLogs;


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public List<Transaction> getWorkLogs() {
		return workLogs;
	}
	public void setWorkLogs(List<Transaction> workLogs) {
		this.workLogs = workLogs;
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
