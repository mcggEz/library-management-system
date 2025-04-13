package com.slms;

public class BorrowedBook {
    private int id;
    private String member;
    private String book;
    private String borrowDate;
    private String returnDate;

    public BorrowedBook(int id, String member, String book, String borrowDate, String returnDate) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public String getMember() {
        return member;
    }

    public String getBook() {
        return book;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}
