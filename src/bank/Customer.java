package bank;

public class Customer extends Person {
    private String customerId;
    private String username;
    private String password;
    private boolean priviliged;
    private Account account;
    private String createdBy; // Feature 1

    public Customer() { super(); }

    public Customer(int age, String firstName, String lastName, Address address, String email, long mobileNumber) {
        super(age, firstName, lastName, address, email, mobileNumber);
    }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isPriviliged() { return priviliged; }
    public void setPriviliged(boolean priviliged) { this.priviliged = priviliged; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public String getCreatedBy() { return createdBy; } // Feature 1
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    @Override
    public String toString() {
        return String.format("Customer[ID:%s, Username:%s, Privileged:%s, Account:%s, CreatedBy:%s]",
                customerId, username, priviliged, account, createdBy);
    }
}
