package bank;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrentAccount implements Account {

    private BigDecimal balance;
    private String IFSC;
    private String accountNumber;
    private AccountType accountType;

    public CurrentAccount() {
        this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    public CurrentAccount(AccountType accountType, BigDecimal balance) {
        this.accountType = accountType;
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        this.accountNumber = generateAccountNumber(accountType);
    }

    private String generateAccountNumber(AccountType type) {
        return type + "" + Math.round(Math.random() * 1_500_000_000L);
    }

    @Override
    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    @Override
    public String getIFSC() {
        return IFSC;
    }

    @Override
    public String toString() {
        return String.format("[AccountNumber:%s, Type:%s, Balance:₹%s, IFSC:%s]",
                accountNumber, accountType, balance, IFSC);
    }
}
