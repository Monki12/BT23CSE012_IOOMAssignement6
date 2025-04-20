package Library;
import java.util.Scanner;

public class Student extends User {
    private static final long serialVersionUID = 5L;

    private String department;
    private int year;

    // Constructors
    public Student() {
        super();
        this.department = "";
        this.year = 1;
    }

    public Student(String userId, String name, String password, String department, int year) {
        super(userId, name, password, false);
        this.department = department;
        this.year = year;
    }

    // Copy constructor
    public Student(Student other) {
        super(other);
        this.department = other.department;
        this.year = other.year;
    }

    // Getters and setters
    public String getDepartment() { return department; }
    public int getYear() { return year; }

    // Methods
    @Override
    public void displayMenu(Library library) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        do {
            System.out.println("\n========= STUDENT MENU =========");
            System.out.println("1. Search for a Book");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. View Transaction History");
            System.out.println("5. Pay Fines");
            System.out.println("6. View Profile");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        library.searchBooks();
                        break;
                    case 2:
                        System.out.print("Enter Book ID to borrow: ");
                        String borrowId = scanner.nextLine();
                        library.borrowBook(this, borrowId);
                        break;
                    case 3:
                        System.out.print("Enter Book ID to return: ");
                        String returnId = scanner.nextLine();
                        library.returnBook(this, returnId);
                        break;
                    case 4:
                        this.displayTransactions();
                        break;
                    case 5:
                        System.out.print("Enter amount to pay: $");
                        double amount = Double.parseDouble(scanner.nextLine());
                        this.payFine(amount);
                        System.out.println("Payment successful. Remaining fines: $" + this.getFines());
                        break;
                    case 6:
                        System.out.println(this);
                        System.out.println("Department: " + department);
                        System.out.println("Year: " + year);
                        break;
                    case 0:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                choice = -1;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    @Override
    public String toString() {
        return super.toString() + "\nDepartment: " + department + "\nYear: " + year;
    }
}