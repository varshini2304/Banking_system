package bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String type;
    private BigDecimal amount;
    private String accountNumber;
    private String employeeId;
    private String customerUsername;
    private LocalDateTime dateTime;

    public Transaction(String type, BigDecimal amount, String accountNumber, String employeeId, String customerUsername) {
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.employeeId = employeeId;
        this.customerUsername = customerUsername;
        this.dateTime = LocalDateTime.now();
    }

    public String getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public String getAccountNumber() { return accountNumber; }
    public String getEmployeeId() { return employeeId; }
    public String getCustomerUsername() { return customerUsername; }
    public LocalDateTime getDateTime() { return dateTime; }

    @Override
    public String toString() {
        return String.format("Transaction[%s, ₹%s, Account:%s, By:%s, Date:%s]",
                type, amount, accountNumber, employeeId, dateTime);
    }
}
