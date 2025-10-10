package bank;

import java.math.BigDecimal;

public interface Account {
    void setBalance(BigDecimal balance);
    BigDecimal getBalance();

    void setAccountType(AccountType accountType);
    AccountType getAccountType();

    String getAccountNumber();
    void setAccountNumber(String accountNumber);

    void setIFSC(String ifsc);
    String getIFSC();
    
    default void applyInterest() {
    }
}
