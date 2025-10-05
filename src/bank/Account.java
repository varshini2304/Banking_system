package bank;

public interface Account {
	public void setBalance(double balance);
	public double getBalance();
	public void setAccountType(AccountType type);
	public AccountType getAccountType();
	public String getAccountNumber();
//	public void setAccountNumber(String accountNumber);
	public void setIFSC(String ifsc);
	public String getIFSC();
	

}
