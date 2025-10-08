package bank;

public class Admin extends Person {
    private String username;
    private String password;

    public Admin(int age, String firstName, String lastName, String email, long mobileNumber, String username, String password) {
        super(age, firstName, lastName, email, mobileNumber);
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
