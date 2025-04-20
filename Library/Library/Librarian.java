package Library;
import java.util.Scanner;

public class Librarian extends User {
    private static final long serialVersionUID = 4L;

    private String position;
    private double salary;

    // Constructors
    public Librarian() {
        super();
        this.position = "";
        this.salary = 0.0;
        this.isLibrarian = true;
    }

    public Librarian(String userId, String name, String password, String position, double salary) {
        super(userId, name, password, true);
        this.position = position;
        this.salary = salary;
    }

    // Copy constructor
    public Librarian(Librarian other) {
        super(other);
        this.position = other.position;
        this.salary = other.salary;
    }

    // Getters and setters
    public String getPosition() { return position; }
    public double getSalary() { return salary; }

    // Methods
    @Override
    public void displayMenu(Library library) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        do {
            System.out.println("\n========= LIBRARIAN MENU =========");
            System.out.println("1. Add a New Book");
            System.out.println("2. Remove a Book");
            System.out.println("3. Update Book Details");
            System.out.println("4. Search for a Book");
            System.out.println("5. View All Books");
            System.out.println("6. Add a New User");
            System.out.println("7. Remove a User");
            System.out.println("8. View All Users");
            System.out.println("9. View Transaction History");
            System.out.println("10. Generate Reports");
            System.out.println("11. View Profile");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        library.addBook();
                        break;
                    case 2:
                        System.out.print("Enter Book ID to remove: ");
                        String removeId = scanner.nextLine();
                        library.removeBook(removeId);
                        break;
                    case 3:
                        System.out.print("Enter Book ID to update: ");
                        String updateId = scanner.nextLine();
                        library.updateBook(updateId);
                        break;
                    case 4:
                        library.searchBooks();
                        break;
                    case 5:
                        library.displayAllBooks();
                        break;
                    case 6:
                        library.addUser();
                        break;
                    case 7:
                        System.out.print("Enter User ID to remove: ");
                        String userId = scanner.nextLine();
                        library.removeUser(userId);
                        break;
                    case 8:
                        library.displayAllUsers();
                        break;
                    case 9:
                        System.out.print("Enter User ID to view transactions (leave blank for all): ");
                        String transId = scanner.nextLine();
                        if (transId.isEmpty()) {
                            library.displayAllTransactions();
                        } else {
                            library.displayUserTransactions(transId);
                        }
                        break;
                    case 10:
                        library.generateReports();
                        break;
                    case 11:
                        System.out.println(this);
                        System.out.println("Position: " + position);
                        System.out.println("Salary: $" + salary);
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
        return super.toString() + "\nPosition: " + position + "\nSalary: $" + salary;
    }
}