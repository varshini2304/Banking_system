package bank;

import java.math.BigDecimal;

public class CheckingAccount implements Account{

	private BigDecimal balance;
	private String IFSC;
	private String accountNumber;
	private AccountType accountType;


	public CheckingAccount() {
		super();
	}

	public CheckingAccount(AccountType accountType,BigDecimal balance) {
		super();
		this.accountType=accountType;
		this.balance=balance;
		this.accountNumber=generateAccountNumber(accountType);
	}



	private String generateAccountNumber(AccountType accountType2) {
		String local = accountType+""+Math.round(Math.random()*1500000000);
		return local;
	}

	@Override
	public void setBalance(BigDecimal balance) {
		this.balance=balance;
	}

	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		return this.balance;
	}

	@Override
	public void setAccountType(AccountType accountType) {
		this.accountType=accountType;
	}

	@Override
	public AccountType getAccountType() {
		// TODO Auto-generated method stub
		return this.accountType;
	}

	@Override
	public String getAccountNumber() {
		// TODO Auto-generated method stub
		return accountNumber;
	}

//	@Override
//	public void setAccountNumber(String accountNumber) {
//
//		this.accountNumber=accountNumber;
//	}

	@Override
	public void setIFSC(String ifsc) {
		this.IFSC=ifsc;

	}

	@Override
	public String getIFSC() {
		// TODO Auto-generated method stub
		return this.IFSC;
	}

}
