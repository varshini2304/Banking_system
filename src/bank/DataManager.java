package bank;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import exceptions.AccountNotFoundException;
import exceptions.InsufficientBalanceException;

public class DataManager {
    private List<Admin> admins;
    private List<Manager> managers;
    private List<Employee> employees;
    private List<Customer> customers;
    private List<Transaction> allTransactions;
    private Queue<Approval> approvalQueue;
    private Set<String> employeeIds;

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
        Manager defaultManager = new Manager(35, "Default", "Manager", "manager@bank.com", 1234567891L, "manager", "manager123");
        managers.add(defaultManager);

        // Default employees
        for (int i = 1; i <= 3; i++) {
            String empId = "VBIE" + String.format("%02d", i);
            Employee emp = new Employee(25 + i, "Employee" + i, "Last" + i, "emp" + i + "@bank.com", 1234567890L + i,
                    "emp" + i, "pass" + i, "Teller", 30000 + i * 1000);
            emp.setEmployeeId(empId);
            emp.setWorkLogs(new ArrayList<>());
            employees.add(emp);
            employeeIds.add(empId);
        }

        // Default customers
        for (int i = 1; i <= 5; i++) {
            Address addr = new Address(100 + i, "Street" + i, "City" + i, "State" + i, "Country", "12345" + i);
            Customer cus = new Customer(20 + i, "Customer" + i, "Last" + i, addr, "cus" + i + "@bank.com", 1234567890L + i);
            cus.setCustomerId(String.valueOf(i));
            cus.setUsername(String.valueOf(i));
            cus.setPassword("pass");
            cus.setCreatedBy("default"); // Feature 1
            Account acc = new SavingsAccount(AccountType.SB, BigDecimal.valueOf(1000 + i * 100));
            acc.setAccountNumber(String.valueOf(i));
            cus.setAccount(acc);
            customers.add(cus);
            System.out.println("Added customer " + i + " username: " + cus.getUsername() + " password: " + cus.getPassword());
        }
    }

    // ================= AUTHENTICATION =================
    public Admin authenticateAdmin(String username, String password) {
        return admins.stream().filter(a -> a.getUsername().equals(username) && a.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public Manager authenticateManager(String username, String password) {
        return managers.stream().filter(m -> m.getUsername().equals(username) && m.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public Employee authenticateEmployee(String username, String password) {
        return employees.stream().filter(e -> e.getUserName().equals(username) && e.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public Customer authenticateCustomer(String username, String password) {
        return customers.stream().filter(c -> c.getUsername().equals(username) && c.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    // ================= ADD ENTITIES =================
    public void addManager(Manager m) {
        managers.add(m);
    }

    public void addEmployee(Employee e) {
        employees.add(e);
        employeeIds.add(e.getEmployeeId());
    }

    // Feature 1: add createdBy
    public void addCustomer(Customer c, String createdBy) {
        c.setCreatedBy(createdBy);
        customers.add(c);
    }

    public void addTransaction(Transaction t) {
        allTransactions.add(t);
    }

    public void addApproval(Approval a) {
        approvalQueue.add(a);
    }

    // ================= GETTERS =================
    public List<Manager> getManagers() { return managers; }
    public List<Employee> getEmployees() { return employees; }
    public List<Customer> getCustomers() { return customers; }
    public List<Transaction> getAllTransactions() { return allTransactions; }
    public Queue<Approval> getApprovalQueue() { return approvalQueue; }

    // ================= FINDERS =================
    public Customer findCustomerByAccountNumber(String accountNumber) throws AccountNotFoundException {
        return customers.stream()
                .filter(c -> c.getAccount() != null && c.getAccount().getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
    }

    public Employee findEmployeeById(String employeeId) {
        return employees.stream().filter(e -> e.getEmployeeId().equals(employeeId)).findFirst().orElse(null);
    }

    // ================= TRANSACTIONS =================
    public void performDeposit(String accountNumber, BigDecimal amount, String employeeId)
            throws AccountNotFoundException {
        Customer c = findCustomerByAccountNumber(accountNumber);
        c.getAccount().setBalance(c.getAccount().getBalance().add(amount));
        Transaction t = new Transaction("deposit", amount, accountNumber, employeeId, c.getUsername());
        addTransaction(t);
        Employee e = findEmployeeById(employeeId);
        if (e != null) e.getWorkLogs().add(t);
    }

    public void performWithdraw(String accountNumber, BigDecimal amount, String employeeId)
            throws AccountNotFoundException, InsufficientBalanceException {
        Customer c = findCustomerByAccountNumber(accountNumber);
        if (c.getAccount().getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }

        if (amount.compareTo(BigDecimal.valueOf(50000)) > 0) {
            Transaction t = new Transaction("withdraw", amount, accountNumber, employeeId, c.getUsername());
            Approval a = new Approval(t);
            addApproval(a);
            System.out.println("Transaction >50k requires manager approval.");
            return;
        }

        c.getAccount().setBalance(c.getAccount().getBalance().subtract(amount));
        Transaction t = new Transaction("withdraw", amount, accountNumber, employeeId, c.getUsername());
        addTransaction(t);
        Employee e = findEmployeeById(employeeId);
        if (e != null) e.getWorkLogs().add(t);
    }

    public List<Transaction> getTransactionsByAccount(String accountNumber) {
    	return allTransactions.stream()
    	        .filter(t -> t.getAccountNumber().equals(accountNumber))
    	        .collect(Collectors.toList());
    }

    public void approveTransaction(Approval a, String managerUsername) {
        a.setStatus("approved");
        a.setManagerUsername(managerUsername);
        addTransaction(a.getTransaction());
        Employee e = findEmployeeById(a.getTransaction().getEmployeeId());
        if (e != null) e.getWorkLogs().add(a.getTransaction());
        a.signalApproval();
    }

    public void rejectTransaction(Approval a, String managerUsername) {
        a.setStatus("rejected");
        a.setManagerUsername(managerUsername);
        a.signalApproval();
    }

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
