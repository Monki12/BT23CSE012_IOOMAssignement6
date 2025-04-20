package Library;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String userId;
    private String bookId;
    private Date borrowDate;
    private Date returnDate;
    private Date dueDate;
    private double fineAmount;
    private boolean isReturned;

    // Constructors
    public Transaction() {
        this.transactionId = "";
        this.userId = "";
        this.bookId = "";
        this.borrowDate = new Date();
        this.returnDate = null;
        this.dueDate = new Date(borrowDate.getTime() + (14 * 24 * 60 * 60 * 1000)); // 14 days later
        this.fineAmount = 0.0;
        this.isReturned = false;
    }

    public Transaction(String transactionId, String userId, String bookId) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = new Date();
        this.returnDate = null;
        this.dueDate = new Date(borrowDate.getTime() + (14 * 24 * 60 * 60 * 1000)); // 14 days later
        this.fineAmount = 0.0;
        this.isReturned = false;
    }

    // Adding the required constructor to the Transaction class
    public Transaction(String transactionId, String userId, String bookId, Date borrowDate, Date dueDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.fineAmount = 0.0;
        this.isReturned = false;
    }

    // Copy constructor
    public Transaction(Transaction other) {
        this.transactionId = other.transactionId;
        this.userId = other.userId;
        this.bookId = other.bookId;
        this.borrowDate = other.borrowDate;
        this.returnDate = other.returnDate;
        this.dueDate = other.dueDate;
        this.fineAmount = other.fineAmount;
        this.isReturned = other.isReturned;
    }

    // Getters and setters
    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBookId() {
        return bookId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public void setReturned(boolean isReturned) {
        this.isReturned = isReturned;
    }

    // Method to mark the book as returned and calculate fines if applicable
    public void returnBook(double lateFeePerDay) {
        this.returnDate = new Date();
        this.isReturned = true;

        if (returnDate.after(dueDate)) {
            long daysLate = (returnDate.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
            this.fineAmount = daysLate * lateFeePerDay;
        }
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId +
                "\nUser ID: " + userId +
                "\nBook ID: " + bookId +
                "\nBorrow Date: " + borrowDate +
                "\nReturn Date: " + (returnDate != null ? returnDate : "Not returned yet") +
                "\nDue Date: " + dueDate +
                "\nFine Amount: $" + fineAmount +
                "\nStatus: " + (isReturned ? "Returned" : "Borrowed");
    }
}