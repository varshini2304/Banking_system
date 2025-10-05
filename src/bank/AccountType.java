package bank;

public enum AccountType {
	CHK("Checking"),SB("Savings"),CUR("Current");
	private String mytype;
	public String getMyType() {
		return this.mytype;
	}
	private AccountType(String mytype) {
		this.mytype= mytype;
	}

}