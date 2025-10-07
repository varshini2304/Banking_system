package bank;

import java.math.BigDecimal;

public interface Account {
	public void setBalance(BigDecimal balance);
	public BigDecimal getBalance();
	public void setAccountType(AccountType type);
	public AccountType getAccountType();
	public String getAccountNumber();
//	public void setAccountNumber(String accountNumber);
	public void setIFSC(String ifsc);
	public String getIFSC();


}
