package bank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import exceptions.AccountNotFoundException;
import exceptions.InsufficientBalanceException;

public class DataManager {
    private List<Admin> admins;
    private List<Manager> managers;
    private List<Employee> employees;
    private List<Customer> customers;
    private List<Transaction> allTransactions;
    private Queue<Approval> approvalQueue;
    private Set<String> employeeIds; // for unique employeeIds

    public DataManager() {
        admins = new ArrayList<>();
        managers = new ArrayList<>();
        employees = new ArrayList<>();
        customers = new ArrayList<>();
        allTransactions = new ArrayList<>();
        approvalQueue = new LinkedList<>();
        employeeIds = new HashSet<>();
        initializeDefaults();
    }

    private void initializeDefaults() {
        // Default admin
        Admin defaultAdmin = new Admin(30, "Default", "Admin", "admin@bank.com", 1234567890L, "admin", "admin123");
        admins.add(defaultAdmin);

        // Default manager
        Manager defaultManager = new Manager(35, "Default", "Manager", "manager@bank.com", 1234567891L, "manager",
                "manager123");
        managers.add(defaultManager);

        // Default 3 employees
        for (int i = 1; i <= 3; i++) {
            String empId = "VBIE" + String.format("%02d", i);
            Employee emp = new Employee(25 + i, "Employee" + i, "Last" + i, "emp" + i + "@bank.com", 1234567890L + i,
                    "emp" + i, "pass" + i, "Teller", 30000 + i * 1000);
            emp.setEmployeeId(empId);
            emp.setWorkLogs(new ArrayList<>());
            employees.add(emp);
            employeeIds.add(empId);
        }

        // Default 5 customers
        for (int i = 1; i <= 5; i++) {
            Address addr = new Address(100 + i, "Street" + i, "City" + i, "State" + i, "Country", "12345" + i);
            Customer cus = new Customer(20 + i, "Customer" + i, "Last" + i, addr, "cus" + i + "@bank.com",
                    1234567890L + i);
            cus.setCustomerId(String.valueOf(i));
            cus.setUsername(String.valueOf(i));
            cus.setPassword("pass");
            // Assign account, say Savings
            Account acc = new SavingsAccount(AccountType.SB, BigDecimal.valueOf(1000 + i * 100));
            cus.setAccount(acc);
            customers.add(cus);
            System.out.println(
                    "Added customer " + i + " username: " + cus.getUsername() + " password: " + cus.getPassword());
        }
    }

    // Authentication methods
    public Admin authenticateAdmin(String username, String password) {
        for (Admin a : admins) {
            if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
                return a;
            }
        }
        return null;
    }

    public Manager authenticateManager(String username, String password) {
        for (Manager m : managers) {
            if (m.getUsername().equals(username) && m.getPassword().equals(password)) {
                return m;
            }
        }
        return null;
    }

    public Employee authenticateEmployee(String username, String password) {
        for (Employee e : employees) {
            if (e.getUserName().equals(username) && e.getPassword().equals(password)) {
                return e;
            }
        }
        return null;
    }

    public Customer authenticateCustomer(String username, String password) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    // Add methods
    public void addManager(Manager m) {
        managers.add(m);
    }

    public void addEmployee(Employee e) {
        employees.add(e);
        employeeIds.add(e.getEmployeeId());
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public void addTransaction(Transaction t) {
        allTransactions.add(t);
    }

    public void addApproval(Approval a) {
        approvalQueue.add(a);
    }

    // Getters
    public List<Manager> getManagers() {
        return managers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public Queue<Approval> getApprovalQueue() {
        return approvalQueue;
    }

    // Find methods
    public Customer findCustomerByAccountNumber(String accountNumber) throws AccountNotFoundException {
        for (Customer c : customers) {
            if (c.getAccount() != null && c.getAccount().getAccountNumber().equals(accountNumber)) {
                return c;
            }
        }
        throw new AccountNotFoundException("Account not found: " + accountNumber);
    }

    public Employee findEmployeeById(String employeeId) {
        for (Employee e : employees) {
            if (e.getEmployeeId().equals(employeeId)) {
                return e;
            }
        }
        return null;
    }

    // Transaction methods
    public void performDeposit(String accountNumber, BigDecimal amount, String employeeId)
            throws AccountNotFoundException, InsufficientBalanceException {
        Customer c = findCustomerByAccountNumber(accountNumber);
        c.getAccount().setBalance(c.getAccount().getBalance().add(amount));
        Transaction t = new Transaction("deposit", amount, accountNumber, employeeId, c.getUsername());
        addTransaction(t);
        // Log to employee's workLogs
        Employee e = findEmployeeById(employeeId);
        if (e != null) {
            e.getWorkLogs().add(t);
        }
    }

    public void performWithdraw(String accountNumber, BigDecimal amount, String employeeId)
            throws AccountNotFoundException, InsufficientBalanceException {
        Customer c = findCustomerByAccountNumber(accountNumber);
        if (c.getAccount().getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }
        if (amount.compareTo(BigDecimal.valueOf(50000)) > 0) {
            // Need approval
            Transaction t = new Transaction("withdraw", amount, accountNumber, employeeId, c.getUsername());
            Approval a = new Approval(t);
            addApproval(a);
            System.out.println("Transaction >50k requires manager approval. Submitted for approval. Waiting...");
            try {
                a.waitForApproval();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (a.getStatus().equals("approved")) {
                System.out.println("Withdrawal approved!");
                c.getAccount().setBalance(c.getAccount().getBalance().subtract(amount));
                Transaction transaction = a.getTransaction();
                addTransaction(transaction);
                Employee emp = findEmployeeById(employeeId);
                if (emp != null) {
                    emp.getWorkLogs().add(transaction);
                }
            } else {
                System.out.println("Withdrawal rejected.");
            }
            return;
        }
        c.getAccount().setBalance(c.getAccount().getBalance().subtract(amount));
        Transaction t = new Transaction("withdraw", amount, accountNumber, employeeId, c.getUsername());
        addTransaction(t);
        Employee e = findEmployeeById(employeeId);
        if (e != null) {
            e.getWorkLogs().add(t);
        }
    }

    public void performWithdraw(String accountNumber, BigDecimal amount, String employeeId, DataManager dm, Scanner sc)
            throws AccountNotFoundException, InsufficientBalanceException {
        Customer c = findCustomerByAccountNumber(accountNumber);
        if (c.getAccount().getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }
        if (amount.compareTo(BigDecimal.valueOf(50000)) > 0) {
            System.out.println("Transaction >50k requires manager approval.");
            System.out.print("Enter manager username: ");
            String managerUsername = sc.nextLine();
            System.out.print("Enter manager password: ");
            String managerPassword = sc.nextLine();

            Manager manager = dm.authenticateManager(managerUsername, managerPassword);
            if (manager != null) {
                System.out.println("Manager login successful!");
                System.out.print("Approve transaction? (y/n): ");
                String choice = sc.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    c.getAccount().setBalance(c.getAccount().getBalance().subtract(amount));
                    Transaction t = new Transaction("withdraw", amount, accountNumber, employeeId, c.getUsername());
                    addTransaction(t);
                    Employee e = findEmployeeById(employeeId);
                    if (e != null) {
                        e.getWorkLogs().add(t);
                    }
                    System.out.println("Withdrawal approved!");
                    System.out.println("Remaining balance: " + c.getAccount().getBalance());
                } else {
                    System.out.println("Withdrawal rejected by manager.");
                }
            } else {
                System.out.println("Invalid manager credentials. Withdrawal rejected.");
            }
            return;
        }
        c.getAccount().setBalance(c.getAccount().getBalance().subtract(amount));
        Transaction t = new Transaction("withdraw", amount, accountNumber, employeeId, c.getUsername());
        addTransaction(t);
        Employee e = findEmployeeById(employeeId);
        if (e != null) {
            e.getWorkLogs().add(t);
        }
    }

    // Approval methods
    public void approveTransaction(Approval a, String managerUsername) {
        a.setStatus("approved");
        a.setManagerUsername(managerUsername);
        addTransaction(a.getTransaction());
        try {
            Employee e = findEmployeeById(a.getTransaction().getEmployeeId());
            if (e != null) {
                e.getWorkLogs().add(a.getTransaction());
            }
        } catch (Exception ex) {
            // Handle
        }
        a.signalApproval();
    }

    public void rejectTransaction(Approval a, String managerUsername) {
        a.setStatus("rejected");
        a.setManagerUsername(managerUsername);
        a.signalApproval();
    }

    // Generate employee ID
    public String generateEmployeeId() {
        int num = employeeIds.size() + 1;
        String id = "VBIE" + String.format("%02d", num);
        while (employeeIds.contains(id)) {
            num++;
            id = "VBIE" + String.format("%02d", num);
        }
        return id;
    }
}