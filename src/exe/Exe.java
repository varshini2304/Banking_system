package exe;

import java.math.BigDecimal;
import java.util.Scanner;
import bank.Account;
import bank.AccountType;
import bank.Address;
import bank.Admin;
import bank.Approval;
import bank.CheckingAccount;
import bank.CurrentAccount;
import bank.Customer;
import bank.DataManager;
import bank.Employee;
import bank.Manager;
import bank.SavingsAccount;
import bank.Transaction;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientBalanceException;

public class Exe {

	public static void main(String[] args) {
		DataManager dm = new DataManager();
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Varshini Banking Private Limited");

		while (true) {
			System.out.println("\nSelect your role:");
			System.out.println("1. Admin");
			System.out.println("2. Manager");
			System.out.println("3. Employee");
			System.out.println("4. Customer");
			System.out.println("5. Exit");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 5) {
				System.out.println("Thank you for using our banking system!");
				break;
			}

			System.out.print("Enter username: ");
			String username = sc.nextLine();
			System.out.print("Enter password: ");
			String password = sc.nextLine();

			switch (choice) {
			case 1: // Admin
				Admin admin = dm.authenticateAdmin(username, password);
				if (admin != null) {
					adminMenu(dm, sc);
				} else {
					System.out.println("Invalid credentials!");
				}
				break;
			case 2: // Manager
				Manager manager = dm.authenticateManager(username, password);
				if (manager != null) {
					managerMenu(dm, manager, sc);
				} else {
					System.out.println("Invalid credentials!");
				}
				break;
			case 3: // Employee
				System.out.print("Enter Employee ID: ");
				String empId = sc.nextLine();
				Employee employee = dm.authenticateEmployee(empId, password);
				if (employee != null) {
					employeeMenu(dm, employee, sc);
				} else {
					System.out.println("Invalid credentials!");
				}
				break;
			case 4: // Customer
				Customer customer = dm.authenticateCustomer(username, password);
				if (customer != null) {
					customerMenu(dm, customer, sc);
				} else {
					System.out.println("Invalid credentials!");
				}
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
		sc.close();
	}

	private static void adminMenu(DataManager dm, Scanner sc) {
		while (true) {
			System.out.println("\nAdmin Menu:");
			System.out.println("1. Create Manager");
			System.out.println("2. Change Manager Password");
			System.out.println("3. Logout");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 3) break;

			switch (choice) {
			case 1:
				System.out.print("Manager Age: ");
				int age = sc.nextInt();
				sc.nextLine();
				System.out.print("First Name: ");
				String fname = sc.nextLine();
				System.out.print("Last Name: ");
				String lname = sc.nextLine();
				System.out.print("Email: ");
				String email = sc.nextLine();
				System.out.print("Mobile: ");
				long mobile = sc.nextLong();
				sc.nextLine();
				System.out.print("Username: ");
				String uname = sc.nextLine();
				System.out.print("Password: ");
				String pass = sc.nextLine();
				Manager m = new Manager(age, fname, lname, email, mobile, uname, pass);
				dm.addManager(m);
				System.out.println("Manager created!");
				break;
			case 2:
				System.out.print("Manager Username: ");
				String mUname = sc.nextLine();
				for (Manager mgr : dm.getManagers()) {
					if (mgr.getUsername().equals(mUname)) {
						System.out.print("New Password: ");
						String newPass = sc.nextLine();
						mgr.setPassword(newPass);
						System.out.println("Password changed!");
						break;
					}
				}
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	private static void managerMenu(DataManager dm, Manager manager, Scanner sc) {
		while (true) {
			System.out.println("\nManager Menu:");
			System.out.println("1. Create Employee");
			System.out.println("2. Delete Employee");
			System.out.println("3. Change Employee Password");
			System.out.println("4. View Reports");
			System.out.println("5. Approve Transactions");
			System.out.println("6. Logout");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 6) break;

			switch (choice) {
			case 1:
				if (dm.getEmployees().size() >= 10) {
					System.out.println("Cannot create more than 10 employees!");
					break;
				}
				System.out.print("Employee Age: ");
				int age = sc.nextInt();
				sc.nextLine();
				System.out.print("First Name: ");
				String fname = sc.nextLine();
				System.out.print("Last Name: ");
				String lname = sc.nextLine();
				System.out.print("Email: ");
				String email = sc.nextLine();
				System.out.print("Mobile: ");
				long mobile = sc.nextLong();
				sc.nextLine();
				System.out.print("Username: ");
				String uname = sc.nextLine();
				System.out.print("Password: ");
				String pass = sc.nextLine();
				System.out.print("Designation: ");
				String desig = sc.nextLine();
				System.out.print("Salary: ");
				double sal = sc.nextDouble();
				sc.nextLine();
				Employee e = new Employee(age, fname, lname, email, mobile, uname, pass, desig, sal);
				e.setEmployeeId(dm.generateEmployeeId());
				e.setWorkLogs(new java.util.ArrayList<>());
				dm.addEmployee(e);
				System.out.println("Employee created with ID: " + e.getEmployeeId());
				break;
			case 2:
				System.out.print("Employee ID to delete: ");
				String empId = sc.nextLine();
				Employee emp = dm.findEmployeeById(empId);
				if (emp != null) {
					dm.getEmployees().remove(emp);
					System.out.println("Employee deleted!");
				} else {
					System.out.println("Employee not found!");
				}
				break;
			case 3:
				System.out.print("Employee ID: ");
				String eId = sc.nextLine();
				Employee emp2 = dm.findEmployeeById(eId);
				if (emp2 != null) {
					System.out.print("New Password: ");
					String newPass = sc.nextLine();
					emp2.setPassword(newPass);
					System.out.println("Password changed!");
				} else {
					System.out.println("Employee not found!");
				}
				break;
			case 4:
				System.out.println("Total Customers: " + dm.getCustomers().size());
				System.out.println("Total Transactions: " + dm.getAllTransactions().size());
				System.out.println("Total Accounts Created: " + dm.getCustomers().size());
				System.out.println("Employees and their work:");
				for (Employee em : dm.getEmployees()) {
					System.out.println("Employee " + em.getEmployeeId() + ": " + em.getWorkLogs().size() + " transactions");
				}
				break;
			case 5:
				for (Approval a : dm.getApprovalQueue()) {
					if (a.getStatus().equals("pending")) {
						System.out.println("Transaction: " + a.getTransaction());
						System.out.print("Approve? (y/n): ");
						String appr = sc.nextLine();
						if (appr.equalsIgnoreCase("y")) {
							dm.approveTransaction(a, manager.getUsername());
							System.out.println("Approved!");
						} else {
							dm.rejectTransaction(a, manager.getUsername());
							System.out.println("Rejected!");
						}
					}
				}
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	private static void employeeMenu(DataManager dm, Employee employee, Scanner sc) {
		while (true) {
			System.out.println("\nEmployee Menu:");
			System.out.println("1. Create Customer");
			System.out.println("2. Deposit");
			System.out.println("3. Withdraw");
			System.out.println("4. Check Balance");
			System.out.println("5. View Work Logs");
			System.out.println("6. Logout");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 6) break;

			switch (choice) {
			case 1:
				System.out.print("Customer Age: ");
				int age = sc.nextInt();
				sc.nextLine();
				System.out.print("First Name: ");
				String fname = sc.nextLine();
				System.out.print("Last Name: ");
				String lname = sc.nextLine();
				System.out.print("Door No: ");
				int doorno = sc.nextInt();
				sc.nextLine();
				System.out.print("Street: ");
				String street = sc.nextLine();
				System.out.print("City: ");
				String city = sc.nextLine();
				System.out.print("State: ");
				String state = sc.nextLine();
				System.out.print("Country: ");
				String country = sc.nextLine();
				System.out.print("Pincode: ");
				String pincode = sc.nextLine();
				Address addr = new Address(doorno, street, city, state, country, pincode);
				System.out.print("Email: ");
				String email = sc.nextLine();
				System.out.print("Mobile: ");
				long mobile = sc.nextLong();
				sc.nextLine();
				Customer c = new Customer(age, fname, lname, addr, email, mobile);
				System.out.print("Username: ");
				String uname = sc.nextLine();
				c.setUsername(uname);
				System.out.print("Password: ");
				String pass = sc.nextLine();
				c.setPassword(pass);
				System.out.println("Account Type: 1.Savings 2.Checking 3.Current");
				int atype = sc.nextInt();
				sc.nextLine();
				AccountType at;
				if (atype == 1) at = AccountType.SB;
				else if (atype == 2) at = AccountType.CHK;
				else at = AccountType.CUR;
				System.out.print("Initial Balance: ");
				double bal = sc.nextDouble();
				sc.nextLine();
				Account acc;
				if (at == AccountType.SB) acc = new SavingsAccount(at, BigDecimal.valueOf(bal));
				else if (at == AccountType.CHK) acc = new CheckingAccount(at, BigDecimal.valueOf(bal));
				else acc = new CurrentAccount(at, BigDecimal.valueOf(bal));
				c.setAccount(acc);
				dm.addCustomer(c);
				System.out.println("Customer created! Account Number: " + acc.getAccountNumber());
				break;
			case 2:
				System.out.print("Account Number: ");
				String accNo = sc.nextLine();
				System.out.print("Amount: ");
				double amt = sc.nextDouble();
				sc.nextLine();
				try {
					dm.performDeposit(accNo, BigDecimal.valueOf(amt), employee.getEmployeeId());
					System.out.println("Deposited!");
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				break;
			case 3:
				System.out.print("Account Number: ");
				String accNo2 = sc.nextLine();
				System.out.print("Amount: ");
				double amt2 = sc.nextDouble();
				sc.nextLine();
				try {
					dm.performWithdraw(accNo2, BigDecimal.valueOf(amt2), employee.getEmployeeId());
					System.out.println("Withdrawn!");
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				break;
			case 4:
				System.out.print("Account Number: ");
				String accNo3 = sc.nextLine();
				try {
					Customer cus = dm.findCustomerByAccountNumber(accNo3);
					System.out.println("Balance: " + cus.getAccount().getBalance());
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				break;
			case 5:
				System.out.println("Work Logs:");
				for (Transaction t : employee.getWorkLogs()) {
					System.out.println(t);
				}
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	private static void customerMenu(DataManager dm, Customer customer, Scanner sc) {
		while (true) {
			System.out.println("\nCustomer Menu:");
			System.out.println("1. Check Balance");
			System.out.println("2. Deposit");
			System.out.println("3. Withdraw");
			System.out.println("4. Logout");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 4) break;

			switch (choice) {
			case 1:
				System.out.println("Balance: " + customer.getAccount().getBalance().toString());
				break;
			case 2:
				System.out.print("Amount: ");
				double amt = sc.nextDouble();
				sc.nextLine();
				customer.getAccount().setBalance(customer.getAccount().getBalance().add(BigDecimal.valueOf(amt)));
				Transaction t = new Transaction("deposit", BigDecimal.valueOf(amt), customer.getAccount().getAccountNumber(), "self", customer.getUsername());
				dm.addTransaction(t);
				System.out.println("Deposited!");
				break;
			case 3:
				System.out.print("Password: ");
				String pass = sc.nextLine();
				if (!pass.equals(customer.getPassword())) {
					System.out.println("Invalid password!");
					break;
				}
				System.out.print("Amount: ");
				double amt2 = sc.nextDouble();
				sc.nextLine();
				if (customer.getAccount().getBalance().compareTo(BigDecimal.valueOf(amt2)) < 0) {
					System.out.println("Insufficient balance!");
				} else {
					customer.getAccount().setBalance(customer.getAccount().getBalance().subtract(BigDecimal.valueOf(amt2)));
					Transaction t2 = new Transaction("withdraw", BigDecimal.valueOf(amt2), customer.getAccount().getAccountNumber(), "self", customer.getUsername());
					dm.addTransaction(t2);
					System.out.println("Withdrawn!");
				}
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}
}
