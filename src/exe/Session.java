package exe;

import java.util.Scanner;
import bank.Admin;
import bank.Customer;
import bank.DataManager;
import bank.Employee;
import bank.Manager;

public class Session implements Runnable {
    private DataManager dm;
    private Object user;
    private Scanner sc;

    public Session(DataManager dm, Object user, Scanner sc) { // ✅ reuse existing scanner
        this.dm = dm;
        this.user = user;
        this.sc = sc;
    }

    @Override
    public void run() {
        if (user instanceof Admin) {
            Exe.adminMenu(sc, dm);
        } else if (user instanceof Manager) {
            Exe.managerMenu(sc, dm);
        } else if (user instanceof Employee) {
            Exe.employeeMenu(dm, (Employee) user, sc);
        } else if (user instanceof Customer) {
            Exe.customerMenu(dm, (Customer) user, sc);
        }
        // ⚠️ Do NOT close sc here — main() will close it once at program end
    }
}
