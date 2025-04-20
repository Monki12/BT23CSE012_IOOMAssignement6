package Library;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String userId;
    protected String name;
    protected String password;
    protected ArrayList<Transaction> transactions;
    protected double fines;
    protected boolean isLibrarian;

    // Constructors
    public User() {
        this.userId = "";
        this.name = "";
        this.password = "";
        this.transactions = new ArrayList<>();
        this.fines = 0.0;
        this.isLibrarian = false;
    }

    public User(String userId, String name, String password, boolean isLibrarian) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.transactions = new ArrayList<>();
        this.fines = 0.0;
        this.isLibrarian = isLibrarian;
    }

    // Copy constructor
    public User(User other) {
        this.userId = other.userId;
        this.name = other.name;
        this.password = other.password;
        this.transactions = new ArrayList<>(other.transactions);
        this.fines = other.fines;
        this.isLibrarian = other.isLibrarian;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public ArrayList<Transaction> getTransactions() { return transactions; }
    public double getFines() { return fines; }
    public boolean isLibrarian() { return isLibrarian; }

    public void setFines(double fines) { this.fines = fines; }
    public void addFine(double amount) { this.fines += amount; }
    public void payFine(double amount) { this.fines -= amount; }

    // Methods
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void displayTransactions() {
        System.out.println("\nTransaction History for " + name + ":");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
        System.out.println("Current Fines: $" + fines);
    }

    // Abstract methods
    public abstract void displayMenu(Library library) throws Exception;

    @Override
    public String toString() {
        return "User ID: " + userId + "\nName: " + name + 
               "\nType: " + (isLibrarian ? "Librarian" : "Student") +
               "\nFines: $" + fines;
    }
}