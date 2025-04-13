package com.slms;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private int year;
    private boolean available;

    public Book(int id, String title, String author, String isbn, String genre, int year, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
        this.available = available;
    }

    // Getters only for now
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public boolean isAvailable() { return available; }
}
