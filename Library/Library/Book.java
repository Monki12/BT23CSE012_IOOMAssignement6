package Library;
import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookId;
    private String title;
    private String author;
    private String category;
    private boolean isAvailable;
    private int totalCopies;
    private int availableCopies;
    private double lateFeePerDay;

    // Constructors
    public Book() {
        this.bookId = "";
        this.title = "";
        this.author = "";
        this.category = "";
        this.isAvailable = false;
        this.totalCopies = 0;
        this.availableCopies = 0;
        this.lateFeePerDay = 0.5;
    }

    public Book(String bookId, String title, String author, String category, 
                int totalCopies, double lateFeePerDay) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.isAvailable = (totalCopies > 0);
        this.lateFeePerDay = lateFeePerDay;
    }

    // Copy constructor
    public Book(Book other) {
        this.bookId = other.bookId;
        this.title = other.title;
        this.author = other.author;
        this.category = other.category;
        this.isAvailable = other.isAvailable;
        this.totalCopies = other.totalCopies;
        this.availableCopies = other.availableCopies;
        this.lateFeePerDay = other.lateFeePerDay;
    }

    // Getters and setters
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public double getLateFeePerDay() { return lateFeePerDay; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setTotalCopies(int totalCopies) { 
        this.totalCopies = totalCopies; 
        this.availableCopies += (totalCopies - this.totalCopies);
        this.isAvailable = (this.availableCopies > 0);
    }
    public void setLateFeePerDay(double lateFeePerDay) { this.lateFeePerDay = lateFeePerDay; }

    // Methods
    public void borrowCopy() throws Exception {
        if (availableCopies <= 0) {
            throw new Exception("No available copies of this book to borrow.");
        }
        availableCopies--;
        isAvailable = (availableCopies > 0);
    }

    public void returnCopy() {
        availableCopies++;
        if (availableCopies > totalCopies) {
            availableCopies = totalCopies;
        }
        isAvailable = true;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + 
               "\nTitle: " + title + 
               "\nAuthor: " + author + 
               "\nCategory: " + category + 
               "\nAvailable: " + (isAvailable ? "Yes" : "No") + 
               "\nCopies: " + availableCopies + "/" + totalCopies + 
               "\nLate Fee: $" + lateFeePerDay + " per day";
    }
}