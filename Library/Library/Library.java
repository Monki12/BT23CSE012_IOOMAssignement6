package Library;
import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<Transaction> transactions;
    private Scanner scanner;
    private static final String BOOKS_FILE = "books.csv";
    private static final String USERS_FILE = "users.csv";
    private static final String TRANSACTIONS_FILE = "transactions.csv";

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.scanner = new Scanner(System.in);
         loadData();
         // Add a default admin if no users exist
         if (users.isEmpty()) {
            users.add(new Librarian("admin", "Admin", "admin123", "Head Librarian", 50000.0));
            users.add(new Student("student", "Student", "student123", "Computer Science", 2));
            saveData(); // Save to file
        }
       
    }

    // File handling methods
   private void loadData() {
        loadBooksFromCSV();
        loadUsersFromCSV();
        loadTransactionsFromCSV();
    }

    private void loadBooksFromCSV() {
        books.clear(); // Clear existing data before loading
        try (Scanner scanner = new Scanner(new File(BOOKS_FILE))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        String bookId = parts[0];
                        String title = parts[1];
                        String author = parts[2];
                        String category = parts[3];
                        int totalCopies = Integer.parseInt(parts[4]);
                        double lateFeePerDay = Double.parseDouble(parts[5]);
                        books.add(new Book(bookId, title, author, category, totalCopies, lateFeePerDay));
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing book data: " + line);
                    }
                } else {
                    System.out.println("Invalid book data format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No books CSV file found. Starting with empty books.");
        }
    }

    private void loadUsersFromCSV() {
        users.clear(); // Clear existing data before loading
        try (Scanner scanner = new Scanner(new File(USERS_FILE))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    String userId = parts[0];
                    String name = parts[1];
                    String password = parts[2];
                    boolean isLibrarian = Boolean.parseBoolean(parts[3]);
                    String department = parts[4];
                    int year = 0;
                    if (!department.isEmpty()) {
                        try {
                            year = Integer.parseInt(parts[5]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing user year: " + line);
                        }
                    }
                    String position = parts[6];
                    double salary = 0.0;
                    if (!position.isEmpty()) {
                        try {
                            salary = Double.parseDouble(parts[7]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error parsing user salary: " + line);
                        }
                    }

                    if (isLibrarian) {
                        users.add(new Librarian(userId, name, password, position, salary));
                    } else {
                        users.add(new Student(userId, name, password, department, year));
                    }
                } else {
                    System.out.println("Invalid user data format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No users CSV file found. Starting with empty users.");
        }
    }

    private void loadTransactionsFromCSV() {
        transactions.clear(); // Clear existing data before loading
        try (Scanner scanner = new Scanner(new File(TRANSACTIONS_FILE))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    try {
                        String transactionId = parts[0];
                        String userId = parts[1];
                        String bookId = parts[2];
                        Date borrowDate = sdf.parse(parts[3]);
                        Date dueDate = sdf.parse(parts[4]);
                        Date returnDate = parts[5].isEmpty() ? null : sdf.parse(parts[5]);
                        double fineAmount = Double.parseDouble(parts[6]);
                        boolean returned = Boolean.parseBoolean(parts[7]);
                        Transaction transaction = new Transaction(transactionId, userId, bookId, borrowDate, dueDate);
                        transaction.setReturnDate(returnDate);
                        transaction.setFineAmount(fineAmount);
                        transaction.setReturned(returned);
                        transactions.add(transaction);
                    } catch (ParseException | NumberFormatException e) {
                        System.out.println("Error parsing transaction data: " + line);
                    }
                } else {
                    System.out.println("Invalid transaction data format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No transactions CSV file found. Starting with empty transactions.");
        }
    }
    private void saveData() {
        saveBooksToCSV();
        saveUsersToCSV();
        saveTransactionsToCSV();
    }

    private void saveBooksToCSV() {
        try (PrintWriter writer = new PrintWriter(new File(BOOKS_FILE))) {
            // Write header
            writer.println("bookId,title,author,category,totalCopies,lateFeePerDay");
            for (Book book : books) {
                writer.println(String.format("%s,%s,%s,%s,%d,%.2f",
                        book.getBookId(), book.getTitle(), book.getAuthor(), book.getCategory(),
                        book.getTotalCopies(), book.getLateFeePerDay()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving books to CSV: " + e.getMessage());
        }
    }

    private void saveUsersToCSV() {
        try (PrintWriter writer = new PrintWriter(new File(USERS_FILE))) {
            // Write header
            writer.println("userId,name,password,isLibrarian,department,year,position,salary");
            for (User user : users) {
                if (user instanceof Librarian) {
                    Librarian librarian = (Librarian) user;
                    writer.println(String.format("%s,%s,%s,%b,%s,%d,%s,%.2f",
                            librarian.getUserId(), librarian.getName(), librarian.getPassword(),
                            librarian.isLibrarian(), "", 0, librarian.getPosition(), librarian.getSalary()));
                } else if (user instanceof Student) {
                    Student student = (Student) user;
                    writer.println(String.format("%s,%s,%s,%b,%s,%d,%s,%.2f",
                            student.getUserId(), student.getName(), student.getPassword(),
                            student.isLibrarian(), student.getDepartment(), student.getYear(), "", 0.0));
                } else {
                    writer.println(String.format("%s,%s,%s,%b,%s,%d,%s,%.2f",
                            user.getUserId(), user.getName(), user.getPassword(),
                            user.isLibrarian(), "", 0, "", 0.0)); // Basic User
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving users to CSV: " + e.getMessage());
        }
    }

    private void saveTransactionsToCSV() {
        try (PrintWriter writer = new PrintWriter(new File(TRANSACTIONS_FILE))) {
            // Write header
            writer.println("transactionId,userId,bookId,borrowDate,dueDate,returnDate,fineAmount,returned");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Transaction transaction : transactions) {
                writer.println(String.format("%s,%s,%s,%s,%s,%s,%.2f,%b",
                        transaction.getTransactionId(), transaction.getUserId(), transaction.getBookId(),
                        sdf.format(transaction.getBorrowDate()), sdf.format(transaction.getDueDate()),
                        transaction.getReturnDate() != null ? sdf.format(transaction.getReturnDate()) : "",
                        transaction.getFineAmount(), transaction.isReturned()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving transactions to CSV: " + e.getMessage());
        }
    }

    // Book management methods
    public void addBook() {
        try {
            System.out.println("\n=== Add New Book ===");
            System.out.print("Enter Book ID: ");
            String bookId = scanner.nextLine();
            
            // Check if book already exists
            for (Book b : books) {
                if (b.getBookId().equals(bookId)) {
                    throw new Exception("Book with this ID already exists.");
                }
            }
            
            System.out.print("Enter Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Author: ");
            String author = scanner.nextLine();
            System.out.print("Enter Category: ");
            String category = scanner.nextLine();
            System.out.print("Enter Total Copies: ");
            int totalCopies = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Late Fee per Day: ");
            double lateFee = Double.parseDouble(scanner.nextLine());
            
            Book newBook = new Book(bookId, title, author, category, totalCopies, lateFee);
            books.add(newBook);
            saveData();
            System.out.println("Book added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeBook(String bookId) {
        try {
            Book bookToRemove = null;
            for (Book b : books) {
                if (b.getBookId().equals(bookId)) {
                    bookToRemove = b;
                    break;
                }
            }
            
            if (bookToRemove == null) {
                throw new Exception("Book not found.");
            }
            
            // Check if any copies are currently borrowed
            for (Transaction t : transactions) {
                if (t.getBookId().equals(bookId) && !t.isReturned()) {
                    throw new Exception("Cannot remove book. Some copies are still borrowed.");
                }
            }
            
            books.remove(bookToRemove);
            saveData();
            System.out.println("Book removed successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateBook(String bookId) {
        try {
            Book bookToUpdate = null;
            for (Book b : books) {
                if (b.getBookId().equals(bookId)) {
                    bookToUpdate = b;
                    break;
                }
            }
            
            if (bookToUpdate == null) {
                throw new Exception("Book not found.");
            }
            
            System.out.println("\nCurrent Book Details:");
            System.out.println(bookToUpdate);
            
            System.out.println("\n=== Update Book ===");
            System.out.print("Enter new Title (leave blank to keep current): ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) bookToUpdate.setTitle(title);
            
            System.out.print("Enter new Author (leave blank to keep current): ");
            String author = scanner.nextLine();
            if (!author.isEmpty()) bookToUpdate.setAuthor(author);
            
            System.out.print("Enter new Category (leave blank to keep current): ");
            String category = scanner.nextLine();
            if (!category.isEmpty()) bookToUpdate.setCategory(category);
            
            System.out.print("Enter new Total Copies (leave blank to keep current): ");
            String copiesInput = scanner.nextLine();
            if (!copiesInput.isEmpty()) {
                int totalCopies = Integer.parseInt(copiesInput);
                bookToUpdate.setTotalCopies(totalCopies);
            }
            
            System.out.print("Enter new Late Fee per Day (leave blank to keep current): ");
            String feeInput = scanner.nextLine();
            if (!feeInput.isEmpty()) {
                double lateFee = Double.parseDouble(feeInput);
                bookToUpdate.setLateFeePerDay(lateFee);
            }
            
            saveData();
            System.out.println("Book updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchBooks() {
        System.out.println("\n=== Search Books ===");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Category");
        System.out.println("4. Search by Book ID");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter search term: ");
            String searchTerm = scanner.nextLine().toLowerCase();
            
            boolean found = false;
            for (Book b : books) {
                boolean match = false;
                switch (choice) {
                    case 1:
                        match = b.getTitle().toLowerCase().contains(searchTerm);
                        break;
                    case 2:
                        match = b.getAuthor().toLowerCase().contains(searchTerm);
                        break;
                    case 3:
                        match = b.getCategory().toLowerCase().contains(searchTerm);
                        break;
                    case 4:
                        match = b.getBookId().toLowerCase().contains(searchTerm);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        return;
                }
                
                if (match) {
                    System.out.println("\n" + b);
                    found = true;
                }
            }
            
            if (!found) {
                System.out.println("No books found matching your search.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    public void displayAllBooks() {
        System.out.println("\n=== All Books ===");
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        
        for (Book b : books) {
            System.out.println("\n" + b);
        }
    }

    // User management methods
    public void addUser() {
        try {
            System.out.println("\n=== Add New User ===");
            System.out.println("1. Add Student");
            System.out.println("2. Add Librarian");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            
            // Check if user already exists
            for (User u : users) {
                if (u.getUserId().equals(userId)) {
                    throw new Exception("User with this ID already exists.");
                }
            }
            
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            
            if (choice == 1) {
                System.out.print("Enter Department: ");
                String department = scanner.nextLine();
                System.out.print("Enter Year: ");
                int year = Integer.parseInt(scanner.nextLine());
                
                Student newStudent = new Student(userId, name, password, department, year);
                users.add(newStudent);
                System.out.println("Student added successfully!");
            } else if (choice == 2) {
                System.out.print("Enter Position: ");
                String position = scanner.nextLine();
                System.out.print("Enter Salary: ");
                double salary = Double.parseDouble(scanner.nextLine());
                
                Librarian newLibrarian = new Librarian(userId, name, password, position, salary);
                users.add(newLibrarian);
                System.out.println("Librarian added successfully!");
            } else {
                System.out.println("Invalid choice.");
                return;
            }
            
            saveData();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeUser(String userId) {
        try {
            User userToRemove = null;
            for (User u : users) {
                if (u.getUserId().equals(userId)) {
                    userToRemove = u;
                    break;
                }
            }
            
            if (userToRemove == null) {
                throw new Exception("User not found.");
            }
            
            // Check if user has any borrowed books
            for (Transaction t : transactions) {
                if (t.getUserId().equals(userId) && !t.isReturned()) {
                    throw new Exception("Cannot remove user. They have borrowed books.");
                }
            }
            
            users.remove(userToRemove);
            saveData();
            System.out.println("User removed successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayAllUsers() {
        System.out.println("\n=== All Users ===");
        if (users.isEmpty()) {
            System.out.println("No users in the system.");
            return;
        }
        
        for (User u : users) {
            System.out.println("\n" + u);
        }
    }

    // Transaction methods
    public void borrowBook(User user, String bookId) {
        try {
            // Find the book
            Book bookToBorrow = null;
            for (Book b : books) {
                if (b.getBookId().equals(bookId)) {
                    bookToBorrow = b;
                    break;
                }
            }
            
            if (bookToBorrow == null) {
                throw new Exception("Book not found.");
            }
            
            if (!bookToBorrow.isAvailable()) {
                throw new Exception("Book is not available for borrowing.");
            }
            
            // Check if user has already borrowed this book and not returned it
            for (Transaction t : transactions) {
                if (t.getUserId().equals(user.getUserId()) && 
                    t.getBookId().equals(bookId) && 
                    !t.isReturned()) {
                    throw new Exception("You have already borrowed this book.");
                }
            }
            
            // Borrow the book
            bookToBorrow.borrowCopy();
            
            // Create transaction
            String transactionId = "TXN" + System.currentTimeMillis();
            Transaction newTransaction = new Transaction(transactionId, user.getUserId(), bookId);
            transactions.add(newTransaction);
            user.addTransaction(newTransaction);
            
            saveData();
            System.out.println("Book borrowed successfully!");
            System.out.println("Due Date: " + new SimpleDateFormat("yyyy-MM-dd").format(newTransaction.getDueDate()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void returnBook(User user, String bookId) {
        try {
            // Find the book
            Book bookToReturn = null;
            for (Book b : books) {
                if (b.getBookId().equals(bookId)) {
                    bookToReturn = b;
                    break;
                }
            }
            
            if (bookToReturn == null) {
                throw new Exception("Book not found.");
            }
            
            // Find the transaction
            Transaction transactionToUpdate = null;
            for (Transaction t : transactions) {
                if (t.getUserId().equals(user.getUserId()) && 
                    t.getBookId().equals(bookId) && 
                    !t.isReturned()) {
                    transactionToUpdate = t;
                    break;
                }
            }
            
            if (transactionToUpdate == null) {
                throw new Exception("No active borrowing record found for this book.");
            }
            
            // Return the book
            bookToReturn.returnCopy();
            transactionToUpdate.returnBook(bookToReturn.getLateFeePerDay());
            
            if (transactionToUpdate.getFineAmount() > 0) {
                user.addFine(transactionToUpdate.getFineAmount());
                System.out.println("Late return! Fine of $" + transactionToUpdate.getFineAmount() + " has been added to your account.");
            }
            
            saveData();
            System.out.println("Book returned successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayAllTransactions() {
        System.out.println("\n=== All Transactions ===");
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }
        
        for (Transaction t : transactions) {
            System.out.println("\n" + t);
        }
    }

    public void displayUserTransactions(String userId) {
        System.out.println("\n=== Transactions for User " + userId + " ===");
        boolean found = false;
        
        for (Transaction t : transactions) {
            if (t.getUserId().equals(userId)) {
                System.out.println("\n" + t);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No transactions found for this user.");
        }
    }

    // Reporting methods
    public void generateReports() {
        System.out.println("\n=== Library Reports ===");
        System.out.println("1. Books Currently Borrowed");
        System.out.println("2. Overdue Books");
        System.out.println("3. User Fines");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            Date currentDate = new Date();
            
            switch (choice) {
                case 1:
                    System.out.println("\n=== Currently Borrowed Books ===");
                    boolean foundBorrowed = false;
                    for (Transaction t : transactions) {
                        if (!t.isReturned()) {
                            System.out.println("\nBook ID: " + t.getBookId());
                            System.out.println("Borrowed by: " + t.getUserId());
                            System.out.println("Borrow Date: " + t.getBorrowDate());
                            System.out.println("Due Date: " + t.getDueDate());
                            foundBorrowed = true;
                        }
                    }
                    if (!foundBorrowed) {
                        System.out.println("No books are currently borrowed.");
                    }
                    break;
                    
                case 2:
                    System.out.println("\n=== Overdue Books ===");
                    boolean foundOverdue = false;
                    for (Transaction t : transactions) {
                        if (!t.isReturned() && currentDate.after(t.getDueDate())) {
                            long daysOverdue = (currentDate.getTime() - t.getDueDate().getTime()) / (1000 * 60 * 60 * 24);
                            System.out.println("\nBook ID: " + t.getBookId());
                            System.out.println("Borrowed by: " + t.getUserId());
                            System.out.println("Due Date: " + t.getDueDate());
                            System.out.println("Days Overdue: " + daysOverdue);
                            foundOverdue = true;
                        }
                    }
                    if (!foundOverdue) {
                        System.out.println("No books are currently overdue.");
                    }
                    break;
                    
                case 3:
                    System.out.println("\n=== User Fines ===");
                    HashMap<String, Double> userFines = new HashMap<>();
                    
                    for (User u : users) {
                        if (u.getFines() > 0) {
                            userFines.put(u.getUserId(), u.getFines());
                        }
                    }
                    
                    if (userFines.isEmpty()) {
                        System.out.println("No users have outstanding fines.");
                    } else {
                        for (Map.Entry<String, Double> entry : userFines.entrySet()) {
                            System.out.println("User ID: " + entry.getKey() + " - Fine: $" + entry.getValue());
                        }
                    }
                    break;
                    
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // Authentication methods
    public User authenticateUser() {
        System.out.println("\n=== Library Management System Login ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        for (User u : users) {
            if (u.getUserId().equals(userId) && u.getPassword().equals(password)) {
                return u;
            }
        }
        
        return null;
    }

    // Main method to run the system
    public void run() {
        System.out.println("Welcome to the Library Management System!");
        
        while (true) {
            User currentUser = authenticateUser();
            if (currentUser == null) {
                System.out.println("Invalid credentials. Please try again.");
                continue;
            }
            
            System.out.println("\nLogin successful! Welcome, " + currentUser.getName() + "!");
            
            try {
                currentUser.displayMenu(this);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            System.out.print("\nDo you want to exit the system? (yes/no): ");
            String exitChoice = scanner.nextLine().toLowerCase();
            if (exitChoice.equals("yes")) {
                break;
            }
        }
        
        scanner.close();
        System.out.println("Thank you for using the Library Management System!");
    }

    public static void main(String[] args) {
        Library library = new Library();
        library.run();
    }
}