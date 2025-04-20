# Library Management System

This project is a simple Library Management System implemented in Java. It allows for the management of books, users (librarians and students), and borrowing/returning transactions. Data is persisted using CSV files.

## Features

* **Book Management:**
    * Add new books with details like ID, title, author, category, total copies, and late fee per day.
    * Remove existing books.
    * Update book information.
    * Search books by title, author, category, or book ID.
    * Display all books in the library.
* **User Management:**
    * Add new users (students and librarians) with specific details.
    * Remove existing users.
    * Display all users in the system.
* **Transaction Management:**
    * Allow registered users to borrow available books.
    * Record borrowing transactions with borrow date and due date.
    * Allow users to return borrowed books.
    * Calculate and record late return fines.
    * Display all transactions.
    * Display transactions for a specific user.
* **Reporting:**
    * Generate a report of currently borrowed books.
    * Generate a report of overdue books.
    * Generate a report of user fines.
* **Authentication:**
    * Basic login system for users (librarians and students) using their User ID and password.
* **Data Persistence:**
    * Data for books, users, and transactions is stored in CSV files (`books.csv`, `users.csv`, `transactions.csv`).

## Getting Started

### Prerequisites

* Java Development Kit (JDK) installed on your system.

### Running the Application

1.  **Clone the Repository (if applicable):**
    ```bash
    git clone (https://github.com/Monki12/BT23CSE012_IOOMAssignement6.git)
    cd LibraryManagementSystem
    ```

2.  **Compile the Java Files:**
    Navigate to the `Library` directory (or the directory containing your `.java` files) and compile the source code:
    ```bash
    javac Library/*.java
    ```

3.  **Create CSV Data Files (if not already present):**
    Ensure you have the `books.csv`, `users.csv`, and `transactions.csv` files in the same directory where you will run the application. You can use the sample data provided or create your own.

4.  **Run the Library Application:**
    Execute the main `Library` class:
    ```bash
    java Library.Library
    ```

    This will start the Library Management System, and you will be prompted to log in.

### Sample User Credentials

The system, by default (if no `users.csv` exists on the first run), creates the following default users:

* **Librarian:**
    * User ID: `admin`
    * Password: `admin123`
* **Student:**
    * User ID: `student`
    * Password: `student123`

If you are using the provided sample `users.csv` file, you can use the following credentials for testing:

* **Librarian:**
    * User ID: `L001`
    * Password: `librarian789`
    * User ID: `L002`
    * Password: `libraryboss`
* **Students:**
    * User ID: `S001`
    * Password: `books123`
    * User ID: `29617`
    * Password: `flowers`
    * User ID: `S003`
    * Password: `reader456`
    * User ID: `29614`
    * Password: `ece2023`
    * User ID: `29618`
    * Password: `cse2023`

## Data Files

The application uses the following CSV files for data persistence:

* **`books.csv`:** Stores book information (bookId, title, author, category, totalCopies, lateFeePerDay).
* **`users.csv`:** Stores user information (userId, name, password, isLibrarian, department, year, position, salary).
* **`transactions.csv`:** Stores transaction details (transactionId, userId, bookId, borrowDate, dueDate, returnDate, fineAmount, returned).

**Note:** Ensure these files are in the same directory as your compiled `.class` files when running the application.

