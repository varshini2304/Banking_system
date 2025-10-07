package bank;

public class Manager extends Person {
    private String username;
    private String password;

    public Manager(int age, String firstName, String lastName, String email, long mobileNumber, String username, String password) {
        super(age, firstName, lastName, email, mobileNumber);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Manager [username=" + username + ", " + super.toString() + "]";
    }
}
