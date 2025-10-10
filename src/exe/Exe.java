package exe;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import bank.*;
import exceptions.*;

public class Exe {

    public static void main(String[] args) {
        System.out.println("Welcome to Varshini Banking Private Limited");
        run();
    }

    private static void run() {
        Scanner sc = new Scanner(System.in);
        DataManager dm = new DataManager();

        while (true) {
            int choice = -1;
            int attempts = 0;

            while (attempts < 3) {
                try {
                    System.out.println("\nSelect your role:");
                    System.out.println("1. Admin");
                    System.out.println("2. Manager");
                    System.out.println("3. Employee");
                    System.out.println("4. Customer");
                    System.out.println("5. Exit");
                    System.out.print("Enter choice: ");
                    choice = sc.nextInt();
                    sc.nextLine(); 
                    if (choice >= 1 && choice <= 5) break;
                    else {
                        attempts++;
                        System.out.println("Invalid input! Please enter a valid number between 1 and 5. (" + (3 - attempts) + " attempts remaining)");
                    }
                } catch (Exception e) {
                    attempts++;
                    System.out.println("Invalid input! Please enter a valid number between 1 and 5. (" + (3 - attempts) + " attempts remaining)");
                    sc.nextLine();
                }
            }

            if (attempts >= 3) {
                System.out.println("Too many invalid attempts. Exiting...");
                break;
            }

            if (choice == 5) {
                System.out.println("Thank you for using our banking system!");
                break;
            }

            String username = "";
            String password = "";
            attempts = 0;
            while (attempts < 3) {
                System.out.print("Enter username: ");
                username = sc.nextLine();
                System.out.print("Enter password: ");
                password = sc.nextLine();

                Object user = null;
                switch (choice) {
                    case 1: user = dm.authenticateAdmin(username, password); break;
                    case 2: user = dm.authenticateManager(username, password); break;
                    case 3: user = dm.authenticateEmployee(username, password); break;
                    case 4: user = dm.authenticateCustomer(username, password); break;
                }

                if (user != null) {
                    System.out.println("Login successful!");
                    Session session = new Session(dm, user, sc);
                    session.run(); 
                    break;
                } else {
                    attempts++;
                    if (attempts < 3)
                        System.out.println("Invalid credentials! " + (3 - attempts) + " attempts remaining.");
                    else
                        System.out.println("Too many failed login attempts. Returning to main menu.");
                }
            }
        }

        sc.close();
    }

    public static void employeeMenu(DataManager dm, Employee employee, Scanner sc) {
        while (true) {
            int choice = -1;
            int attempts = 0;
            while (attempts < 3) {
                try {
                    System.out.println("\nEmployee Menu:");
                    System.out.println("1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. View Customer Transactions");
                    System.out.println("4. Logout");
                    System.out.print("Enter choice: ");
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice >= 1 && choice <= 4) break;
                    else {
                        System.out.println("Invalid choice! Please enter 1 to 4.");
                        attempts++;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    sc.nextLine();
                    attempts++;
                }
            }
            if (attempts >= 3) {
                System.out.println("Too many invalid attempts. Logging out...");
                break;
            }

            switch (choice) {
                case 1: // Deposit
                    try {
                        System.out.print("Enter customer account number: ");
                        String accNum = sc.nextLine();
                        System.out.print("Enter amount to deposit: ");
                        double amount = sc.nextDouble();
                        sc.nextLine();
                        dm.performDeposit(accNum, BigDecimal.valueOf(amount), employee.getEmployeeId());
                        System.out.println("Deposit successful!");
                    } catch (Exception e) {
                        System.out.println("Error during deposit: " + e.getMessage());
                        sc.nextLine();
                    }
                    break;

                case 2: // Withdraw
                    try {
                        System.out.print("Enter customer account number: ");
                        String accNum = sc.nextLine();
                        System.out.print("Enter amount to withdraw: ");
                        double amount = sc.nextDouble();
                        sc.nextLine();
                        dm.performWithdraw(accNum, BigDecimal.valueOf(amount), employee.getEmployeeId());
                        System.out.println("Withdrawal processed!");
                    } catch (Exception e) {
                        System.out.println("Error during withdrawal: " + e.getMessage());
                        sc.nextLine();
                    }
                    break;

                case 3: // View customer transactions
                    try {
                        System.out.print("Enter customer account number: ");
                        String accNum = sc.nextLine();
                        List<Transaction> trans = dm.getTransactionsByAccount(accNum);
                        if (trans.isEmpty()) System.out.println("No transactions yet for this account.");
                        else {
                            System.out.println("\nTransactions:");
                            trans.forEach(System.out::println);
                        }

                        System.out.println("\nWork Logs for employee " + employee.getEmployeeId() + ":");
                        if (employee.getWorkLogs().isEmpty()) System.out.println("No transactions performed yet.");
                        else employee.getWorkLogs().forEach(System.out::println);

                        // Apply interest for all customers
                        dm.getCustomers().forEach(c -> {
                            if (c.getAccount() != null) c.getAccount().applyInterest();
                        });

                    } catch (Exception e) {
                        System.out.println("Error fetching transactions: " + e.getMessage());
                    }
                    break;

                case 4: // Logout
                    System.out.print("Are you sure you want to logout? (y/n): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("y")) {
                        System.out.println("Logging out... Have a great day, " + employee.getFirstName() + "!");
                        return;
                    }
                    break;
            }
        }
    }

    public static void customerMenu(DataManager dm, Customer customer, Scanner sc) {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    if (customer.getAccount() != null)
                        System.out.println("Your balance is: " + customer.getAccount().getBalance());
                    else
                        System.out.println("No account found.");
                    break;

                case 2:
                    if (customer.getAccount() != null) {
                        try {
                            System.out.print("Enter amount to deposit: ");
                            double amount = sc.nextDouble();
                            sc.nextLine();
                            dm.performDeposit(customer.getAccount().getAccountNumber(),
                                    BigDecimal.valueOf(amount), "customer"); // "customer" as depositor
                            System.out.println("Deposit successful! New balance: " + customer.getAccount().getBalance());
                        } catch (Exception e) {
                            System.out.println("Error during deposit: " + e.getMessage());
                            sc.nextLine();
                        }
                    } else {
                        System.out.println("No account found to deposit into.");
                    }
                    break;

                case 3:
                    System.out.println("Thank you for visiting VS Bank. Have a great day!");
                    return;

                default:
                    System.out.println("Invalid choice! Please select 1, 2, or 3.");
            }
        }
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

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Manager Age: "); int age = sc.nextInt(); sc.nextLine();
                        System.out.print("First Name: "); String fname = sc.nextLine();
                        System.out.print("Last Name: "); String lname = sc.nextLine();
                        System.out.print("Email: "); String email = sc.nextLine();
                        System.out.print("Mobile: "); long mobile = sc.nextLong(); sc.nextLine();
                        System.out.print("Username: "); String uname = sc.nextLine();
                        System.out.print("Password: "); String pass = sc.nextLine();

                        Manager m = new Manager(age, fname, lname, email, mobile, uname, pass);
                        dm.addManager(m);
                        System.out.println("Manager created successfully!");
                    } catch (Exception e) {
                        System.out.println("Error creating manager: " + e.getMessage());
                    }
                    break;

                case 2:{
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
                    System.out.println(changed ? "Password changed!" : "Manager not found!");
                    break;
                }
                case 3:{
                	System.out.println("**************have a great day dear ADMIN*******************");

                }
            }
        	break;
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
        
        switch (choice) {
            case 1:
                try {
                    System.out.print("Employee Age: "); int age = sc.nextInt(); sc.nextLine();
                    System.out.print("First Name: "); String fname = sc.nextLine();
                    System.out.print("Last Name: "); String lname = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Mobile: "); long mobile = sc.nextLong(); sc.nextLine();
                    System.out.print("Username: "); String uname = sc.nextLine();
                    System.out.print("Password: "); String pass = sc.nextLine();
                    System.out.print("Designation: "); String desig = sc.nextLine();
                    System.out.print("Salary: "); double sal = sc.nextDouble(); sc.nextLine();

                    Employee e = new Employee(age, fname, lname, email, mobile, uname, pass, desig, sal);
                    e.setEmployeeId(dm.generateEmployeeId());
                    dm.addEmployee(e);
                    System.out.println("Employee created successfully!");
                } catch (Exception e) {
                    System.out.println("Error creating employee: " + e.getMessage());
                }
                break;

            case 2:
                System.out.print("Employee ID to delete: ");
                String empId = sc.nextLine();
                Employee emp = dm.findEmployeeById(empId);
                if (emp != null) {
                    dm.getEmployees().remove(emp);
                    System.out.println("Employee deleted!");
                } else System.out.println("Employee not found!");
                break;

            case 3:
                viewReportsMenu(sc, dm); 
                break;

            case 4:
                Queue<Approval> approvals = dm.getApprovalQueue() != null ? dm.getApprovalQueue() : new java.util.LinkedList<>();
                
                boolean hasPending = approvals.stream().anyMatch(a -> a != null && "pending".equalsIgnoreCase(a.getStatus()));
                
                if (!hasPending) {
                    System.out.println("No pending transactions to approve at the moment.");
                    break;
                }

                for (Approval a : approvals) {
                    if (a != null && "pending".equalsIgnoreCase(a.getStatus())) {
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


            case 5:
                System.out.println("************** Have a great day dear Manager! *******************");
                return; 

            default:
                System.out.println("Invalid choice! Please select 1 to 5.");
        }
    }
}

private static void viewReportsMenu(Scanner sc, DataManager dm) {
    while (true) {
        System.out.println("\nView Reports:");
        System.out.println("1. Full Dashboard");
        System.out.println("2. Today’s Report");
        System.out.println("3. Last 7 Days");
        System.out.println("4. Custom Date Range");
        System.out.println("5. Back");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 5) break;

        switch (choice) {
            case 1: displayFullDashboard(dm); break;
            case 2: displayDateReport(dm, LocalDate.now(), LocalDate.now()); break;
            case 3: displayDateReport(dm, LocalDate.now().minusDays(7), LocalDate.now()); break;
            case 4:
                System.out.print("Start Date (yyyy-MM-dd): ");
                LocalDate start = LocalDate.parse(sc.nextLine());
                System.out.print("End Date (yyyy-MM-dd): ");
                LocalDate end = LocalDate.parse(sc.nextLine());
                displayDateReport(dm, start, end);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}

private static void displayFullDashboard(DataManager dm) {
    List<Transaction> transactions = dm.getAllTransactions() != null ? dm.getAllTransactions() : new ArrayList<>();
    List<Employee> employees = dm.getEmployees() != null ? dm.getEmployees() : new ArrayList<>();
    List<Customer> customers = dm.getCustomers() != null ? dm.getCustomers() : new ArrayList<>();
    Queue<Approval> approvals = dm.getApprovalQueue() != null ? dm.getApprovalQueue() : new LinkedList<>();

    long totalDeposits = transactions.stream()
            .filter(t -> "deposit".equalsIgnoreCase(t.getType()))
            .count();
    BigDecimal totalDepositAmt = transactions.stream()
            .filter(t -> "deposit".equalsIgnoreCase(t.getType()))
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    long totalWithdrawals = transactions.stream()
            .filter(t -> "withdraw".equalsIgnoreCase(t.getType()))
            .count();
    BigDecimal totalWithdrawAmt = transactions.stream()
            .filter(t -> "withdraw".equalsIgnoreCase(t.getType()))
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    long pendingApprovals = approvals.stream()
            .filter(a -> "pending".equalsIgnoreCase(a.getStatus()))
            .count();

    System.out.println("\n================= MANAGER DASHBOARD =================");
    System.out.println("Total Employees: " + employees.size());
    System.out.println("Total Customers: " + customers.size());
    System.out.println("Total Transactions: " + transactions.size());
    System.out.println("-----------------------------------------------------");
    System.out.println("Transaction Summary:");
    System.out.println("  • Deposits: " + totalDeposits + "  (₹" + totalDepositAmt + ")");
    System.out.println("  • Withdrawals: " + totalWithdrawals + "  (₹" + totalWithdrawAmt + ")");
    System.out.println("  • Pending Approvals: " + pendingApprovals + "  (> ₹50,000)");
    System.out.println("-----------------------------------------------------");

    System.out.println("Employee Performance:");
    System.out.printf("%-12s | %-13s | %-10s | %-12s%n",
            "Employee ID", "Transactions", "Customers", "Total Amount");
    System.out.println("--------------------------------------------------------");

    employees.forEach(emp -> {
        long empTransCount = transactions.stream()
                .filter(t -> emp.getEmployeeId().equals(t.getEmployeeId()))
                .count();
        BigDecimal empTotalAmt = transactions.stream()
                .filter(t -> emp.getEmployeeId().equals(t.getEmployeeId()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long empCustCount = customers.stream()
                .filter(c -> c.getCreatedBy() != null && c.getCreatedBy().equals(emp.getEmployeeId()))
                .count();

        System.out.printf("%-12s | %-13d | %-10d | ₹%-12s%n",
                emp.getEmployeeId(), empTransCount, empCustCount, empTotalAmt);
    });

    System.out.println("--------------------------------------------------------");

    Employee topPerformer = employees.stream()
            .max(Comparator.comparingLong(emp ->
                    transactions.stream()
                            .filter(t -> emp.getEmployeeId().equals(t.getEmployeeId()))
                            .count()))
            .orElse(null);

    if (topPerformer != null)
        System.out.println("Highest Performing Employee: " + topPerformer.getEmployeeId());
    else
        System.out.println("No transactions yet.");

    System.out.println("=====================================================");
}

/* ====================== DATE-WISE REPORT ====================== */
private static void displayDateReport(DataManager dm, LocalDate start, LocalDate end) {
    List<Transaction> allTrans = dm.getAllTransactions() != null ? dm.getAllTransactions() : new ArrayList<>();
    List<Transaction> filtered = allTrans.stream()
            .filter(t -> t != null && t.getDateTime() != null)
            .filter(t -> !t.getDateTime().toLocalDate().isBefore(start)
                    && !t.getDateTime().toLocalDate().isAfter(end))
            .collect(Collectors.toList());

    BigDecimal totalAmt = filtered.stream()
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    long deposits = filtered.stream()
            .filter(t -> "deposit".equalsIgnoreCase(t.getType()))
            .count();
    BigDecimal depAmt = filtered.stream()
            .filter(t -> "deposit".equalsIgnoreCase(t.getType()))
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    long withdrawals = filtered.stream()
            .filter(t -> "withdraw".equalsIgnoreCase(t.getType()))
            .count();
    BigDecimal withAmt = filtered.stream()
            .filter(t -> "withdraw".equalsIgnoreCase(t.getType()))
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    System.out.println("\nReport from " + start + " to " + end);
    System.out.println("-----------------------------------------------------");
    System.out.println("Total Transactions: " + filtered.size());
    System.out.println("Deposits: " + deposits + " (₹" + depAmt + ")");
    System.out.println("Withdrawals: " + withdrawals + " (₹" + withAmt + ")");
    System.out.println("Total Amount Handled: ₹" + totalAmt);
    System.out.println("-----------------------------------------------------");

    System.out.println("Employee Performance in Date Range:");
    System.out.printf("%-12s | %-13s | %-12s%n", "Employee ID", "Transactions", "Total Amount");
    System.out.println("--------------------------------------------");

    dm.getEmployees().forEach(emp -> {
        long empTrans = filtered.stream()
                .filter(t -> emp.getEmployeeId().equals(t.getEmployeeId()))
                .count();
        BigDecimal empAmt = filtered.stream()
                .filter(t -> emp.getEmployeeId().equals(t.getEmployeeId()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.printf("%-12s | %-13d | ₹%-12s%n", emp.getEmployeeId(), empTrans, empAmt);
    });

    System.out.println("--------------------------------------------");
}
}
