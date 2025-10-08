package bank;

public class Approval {
    private Transaction transaction;
    private String status; // pending, approved, rejected
    private String managerUsername;

    public Approval(Transaction transaction) {
        this.transaction = transaction;
        this.status = "pending";
    }

    public Transaction getTransaction() { return transaction; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getManagerUsername() { return managerUsername; }
    public void setManagerUsername(String managerUsername) { this.managerUsername = managerUsername; }

    public synchronized void waitForApproval() throws InterruptedException {
        while (status.equals("pending")) {
            wait();
        }
    }

    public synchronized void signalApproval() {
        notifyAll();
    }

    @Override
    public String toString() {
        return "Approval [transaction=" + transaction + ", status=" + status + ", managerUsername=" + managerUsername + "]";
    }
}
