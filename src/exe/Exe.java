package exe;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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
        System.out.println("Welcome to Varshini Banking Private Limited");
        run();
    }

    private static void run() {
        Scanner sc = new Scanner(System.in);
        DataManager dm = new DataManager();

        while (true) {
            int choice;
            String username;
            String password;
            synchronized (System.out) {
                System.out.println("\nSelect your role:");
                System.out.println("1. Admin");
                System.out.println("2. Manager");
                System.out.println("3. Employee");
                System.out.println("4. Customer");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                if (choice == 5) {
                    System.out.println("Thank you for using our banking system!");
                    break;
                }

                System.out.print("Enter username: ");
                username = sc.nextLine();
                System.out.print("Enter password: ");
                password = sc.nextLine();
            }

            Object user = null;
            switch (choice) {
                case 1: // Admin
                    user = dm.authenticateAdmin(username, password);
                    break;
                case 2: // Manager
                    user = dm.authenticateManager(username, password);
                    break;
                case 3: // Employee
                    user = dm.authenticateEmployee(username, password);
                    break;
                case 4: // Customer
                    user = dm.authenticateCustomer(username, password);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            if (user != null) {
                System.out.println("Login successful!");
                Session session = new Session(dm, user);
                Thread thread = new Thread(session);
                thread.start();
                try {
                    thread.join(); // Wait for the session thread to complete
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (choice >= 1 && choice <= 4) {
                System.out.println("Invalid credentials!");
            }
        }
        sc.close();
    }

    public static void adminMenu(Scanner sc, DataManager dm) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Create Manager");
            System.out.println("2. Change Manager Password");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 3)
                break;

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
                    String mgrUname = sc.nextLine();
                    System.out.print("New Password: ");
                    String newPass = sc.nextLine();
                    boolean changed = false;
                    for (Manager mgr : dm.getManagers()) {
                        if (mgr.getUsername().equals(mgrUname)) {
                            mgr.setPassword(newPass);
                            changed = true;
                            break;
                        }
                    }
                    if (changed) {
                        System.out.println("Password changed!");
                    } else {
                        System.out.println("Manager not found!");
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void managerMenu(Scanner sc, DataManager dm) {
        while (true) {
            System.out.println("\nManager Menu:");
            System.out.println("1. Create Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. View Reports");
            System.out.println("4. Approve Transactions");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 5)
                break;

            switch (choice) {
                case 1:
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
                    dm.addEmployee(e);
                    System.out.println("Employee created!");
                    break;
                case 2:
                    System.out.print("Employee ID to delete: ");
                    String empId2 = sc.nextLine();
                    Employee emp = dm.findEmployeeById(empId2);
                    if (emp != null) {
                        dm.getEmployees().remove(emp);
                        System.out.println("Employee deleted!");
                    } else {
                        System.out.println("Employee not found!");
                    }
                    break;
                case 3:
                    viewReportsMenu(sc, dm);
                    break;
                case 4:
                    for (Approval a : dm.getApprovalQueue()) {
                        if (a.getStatus().equals("pending")) {
                            System.out.println(a);
                            System.out.print("Approve? (y/n): ");
                            String appr = sc.nextLine();
                            if (appr.equalsIgnoreCase("y")) {
                                dm.approveTransaction(a, "manager");
                                System.out.println("Approved!");
                            } else {
                                dm.rejectTransaction(a, "manager");
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

    public static void employeeMenu(DataManager dm, Employee employee, Scanner sc) {
        // This menu remains largely the same as it already used DataManager directly
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

            if (choice == 6)
                break;

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
                    if (atype == 1)
                        at = AccountType.SB;
                    else if (atype == 2)
                        at = AccountType.CHK;
                    else
                        at = AccountType.CUR;
                    System.out.print("Initial Balance: ");
                    double bal = sc.nextDouble();
                    sc.nextLine();
                    Account acc;
                    if (at == AccountType.SB)
                        acc = new SavingsAccount(at, BigDecimal.valueOf(bal));
                    else if (at == AccountType.CHK)
                        acc = new CheckingAccount(at, BigDecimal.valueOf(bal));
                    else
                        acc = new CurrentAccount(at, BigDecimal.valueOf(bal));
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
                        dm.performWithdraw(accNo2, BigDecimal.valueOf(amt2), employee.getEmployeeId(), dm, sc);
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

    public static void customerMenu(DataManager dm, Customer customer, Scanner sc) {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 4)
                break;

            switch (choice) {
                case 1:
                    System.out.println("Balance: " + customer.getAccount().getBalance().toString());
                    break;
                case 2:
                    System.out.print("Amount: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();
                    try {
                        dm.performDeposit(customer.getAccount().getAccountNumber(), BigDecimal.valueOf(amt), "self");
                        System.out.println("Deposited!");
                    } catch (AccountNotFoundException | InsufficientBalanceException e) {
                        System.out.println(e.getMessage());
                    }
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

                    if (BigDecimal.valueOf(amt2).compareTo(BigDecimal.valueOf(50000)) > 0) {
                        Transaction t2 = new Transaction("withdraw", BigDecimal.valueOf(amt2),
                                customer.getAccount().getAccountNumber(), "self", customer.getUsername());
                        Approval a = new Approval(t2);
                        dm.addApproval(a);
                        System.out.println("Withdrawal amount > 50000. Submitted for manager approval.");

                    } else {
                        try {
                            dm.performWithdraw(customer.getAccount().getAccountNumber(), BigDecimal.valueOf(amt2),
                                    "self");
                            System.out.println("Withdrawn!");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void displayFullDashboard(DataManager dm) {
        List<Transaction> transactions = dm.getAllTransactions();
        List<Employee> employees = dm.getEmployees();
        List<Customer> customers = dm.getCustomers();
        List<Approval> approvals = dm.getApprovalQueue().stream().filter(a -> a.getStatus().equals("pending"))
                .collect(Collectors.toList());

        // Calculate summary
        int totalDeposits = 0;
        int totalWithdrawals = 0;
        double totalDepositAmount = 0;
        double totalWithdrawAmount = 0;
        for (Transaction t : transactions) {
            if (t.getType().equals("deposit")) {
                totalDeposits++;
                totalDepositAmount += t.getAmount().doubleValue();
            } else if (t.getType().equals("withdraw")) {
                totalWithdrawals++;
                totalWithdrawAmount += t.getAmount().doubleValue();
            }
        }
        int pendingApprovals = approvals.size();

        // ANSI colors
        String blue = "\u001B[34m";
        String reset = "\u001B[0m";

        System.out.println("================= MANAGER DASHBOARD =================" + reset);
        System.out.println("Total Employees: " + employees.size());
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Transactions: " + transactions.size());
        System.out.println("-----------------------------------------------------");
        System.out.println("Transaction Summary:");
        System.out.printf("  → Deposits: %d  (₹%,.0f)\n", totalDeposits, totalDepositAmount);
        System.out.printf("  → Withdrawals: %d  (₹%,.0f)\n", totalWithdrawals, totalWithdrawAmount);
        System.out.printf("  → Pending Approvals: %d  (> ₹50,000)\n", pendingApprovals);
        System.out.println("-----------------------------------------------------");

        // Employee performance
        System.out.println("Employee Performance:");
        System.out.println("Employee ID   | Transactions | Customers | Total Amount");
        System.out.println("--------------------------------------------------------");
        for (Employee emp : employees) {
            int transCount = emp.getWorkLogs().size();
            // Customers handled: assume unique customers from transactions
            long custCount = emp.getWorkLogs().stream().map(Transaction::getCustomerId).distinct().count();
            double totalAmt = emp.getWorkLogs().stream().mapToDouble(t -> t.getAmount().doubleValue()).sum();
            System.out.printf("% -12s | %-12d | %-9d | ₹%,.0f\n", emp.getEmployeeId(), transCount, custCount, totalAmt);
        }
        System.out.println("--------------------------------------------------------");

        // Highest and lowest performing
        Employee highest = employees.stream()
                .max((e1, e2) -> Integer.compare(e1.getWorkLogs().size(), e2.getWorkLogs().size())).orElse(null);
        Employee lowest = employees.stream()
                .min((e1, e2) -> Integer.compare(e1.getWorkLogs().size(), e2.getWorkLogs().size())).orElse(null);
        if (highest != null) {
            System.out.println("Highest Performing Employee: " + highest.getEmployeeId() + " (" 
                    + highest.getWorkLogs().size() + " transactions)");
        }
        if (lowest != null) {
            System.out.println("Least Active Employee: " + lowest.getEmployeeId() + " (" + lowest.getWorkLogs().size()
                    + " transactions)");
        }
        System.out.println("=====================================================");

        // Pending Approvals
        if (!approvals.isEmpty()) {
            System.out.println("\nPending Approvals:");
            System.out.println("--------------------------------------------------------");
            System.out.println("Account No     | Employee ID | Amount      | DateTime");
            for (Approval a : approvals) {
                Transaction t = a.getTransaction();
                System.out.printf("% -14s | %-11s | ₹%,-9.0f | %s\n", t.getAccountNumber(), t.getEmployeeId(),
                        t.getAmount().doubleValue(),
                        t.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))); 
            }
            System.out.println("--------------------------------------------------------");
        }

        // Employee Activity Logs (recent, say last 5 per employee)
        System.out.println("\nEmployee Activity Logs:");
        for (Employee emp : employees) {
            System.out.println("Employee " + emp.getEmployeeId() + " Recent Activities:");
            List<Transaction> logs = emp.getWorkLogs().stream()
                    .sorted((t1, t2) -> t2.getDateTime().compareTo(t1.getDateTime())).limit(5)
                    .collect(Collectors.toList());
            for (Transaction t : logs) {
                String action = t.getType().equals("deposit") ? "Deposited" : "Withdrew";
                System.out.println("- " + action + " ₹" + t.getAmount() + " to " + t.getAccountNumber() + " on "
                        + t.getDateTime().toLocalDate());
            }
            if (logs.isEmpty()) {
                System.out.println("- No recent activities");
            }
        }
    }

    private static void displayDateReport(DataManager dm, LocalDate start, LocalDate end) {
        List<Transaction> filtered = dm.getAllTransactions().stream()
                .filter(t -> !t.getDateTime().toLocalDate().isBefore(start)
                        && !t.getDateTime().toLocalDate().isAfter(end))
                .collect(Collectors.toList());

        System.out.println("Report from " + start + " to " + end);
        System.out.println("Total Transactions: " + filtered.size());

        int deposits = 0, withdrawals = 0;
        double depAmt = 0, withAmt = 0;
        for (Transaction t : filtered) {
            if (t.getType().equals("deposit")) {
                deposits++;
                depAmt += t.getAmount().doubleValue();
            } else {
                withdrawals++;
                withAmt += t.getAmount().doubleValue();
            }
        }
        System.out.printf("Deposits: %d (₹%,.0f)\n", deposits, depAmt);
        System.out.printf("Withdrawals: %d (₹%,.0f)\n", withdrawals, withAmt);

        // Employee performance in date range
        System.out.println("\nEmployee Performance in Date Range:");
        System.out.println("Employee ID   | Transactions | Total Amount");
        System.out.println("--------------------------------------------");
        for (Employee emp : dm.getEmployees()) {
            List<Transaction> empTrans = emp.getWorkLogs().stream()
                    .filter(t -> !t.getDateTime().toLocalDate().isBefore(start)
                            && !t.getDateTime().toLocalDate().isAfter(end))
                    .collect(Collectors.toList());
            int count = empTrans.size();
            double amt = empTrans.stream().mapToDouble(t -> t.getAmount().doubleValue()).sum();
            System.out.printf("% -12s | %-12d | ₹%,.0f\n", emp.getEmployeeId(), count, amt);
        }
        System.out.println("--------------------------------------------");
    }

    private static void viewReportsMenu(Scanner sc, DataManager dm) {
        while (true) {
            System.out.println("\nView Reports:");
            System.out.println("1. Full Dashboard");
            System.out.println("2. Today’s Report");
            System.out.println("3. Last 7 Days");
            System.out.println("4. Custom Date Range");
            System.out.println("5. Back to Manager Menu");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 5)
                break;

            switch (choice) {
                case 1:
                    displayFullDashboard(dm);
                    break;
                case 2:
                    displayDateReport(dm, LocalDate.now(), LocalDate.now());
                    break;
                case 3:
                    displayDateReport(dm, LocalDate.now().minusDays(7), LocalDate.now());
                    break;
                case 4:
                    System.out.print("Start Date (yyyy-MM-dd): ");
                    String start = sc.nextLine();
                    System.out.print("End Date (yyyy-MM-dd): ");
                    String end = sc.nextLine();
                    LocalDate startDate = LocalDate.parse(start);
                    LocalDate endDate = LocalDate.parse(end);
                    displayDateReport(dm, startDate, endDate);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

}