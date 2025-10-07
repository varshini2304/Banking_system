package bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String type; // deposit, withdraw
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String accountNumber;
    private String employeeId; // who performed the transaction
    private String customerId; // or customer username

    public Transaction(String type, BigDecimal amount, String accountNumber, String employeeId, String customerId) {
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.accountNumber = accountNumber;
        this.employeeId = employeeId;
        this.customerId = customerId;
    }

    // Getters
    public String getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getAccountNumber() { return accountNumber; }
    public String getEmployeeId() { return employeeId; }
    public String getCustomerId() { return customerId; }

    @Override
    public String toString() {
        return "Transaction [type=" + type + ", amount=" + amount + ", dateTime=" + dateTime + ", accountNumber=" + accountNumber + ", employeeId=" + employeeId + ", customerId=" + customerId + "]";
    }
}
