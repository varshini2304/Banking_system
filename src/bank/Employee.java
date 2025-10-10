package bank;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Person {
    private String employeeId;
    private String userName;
    private String password;
    private String designation;
    private double salary;
    private List<Transaction> workLogs; // Feature 7

    public Employee(int age, String firstName, String lastName, String email, long mobileNumber,
                    String userName, String password, String designation, double salary) {
        super(age, firstName, lastName, email, mobileNumber);
        this.userName = userName;
        this.password = password;
        this.designation = designation;
        this.salary = salary;
        this.workLogs = new ArrayList<>();
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getUserName() { return userName; }
    public String getPassword() { return password; }

    public String getDesignation() { return designation; }
    public double getSalary() { return salary; }

    public List<Transaction> getWorkLogs() { return workLogs; }

    public void setWorkLogs(List<Transaction> workLogs) { this.workLogs = workLogs; }

    @Override
    public String toString() {
        return String.format("Employee[ID:%s, Name:%s %s, Designation:%s, Salary:%.2f]",
                employeeId, getFirstName(), getLastName(), designation, salary);
    }
}
