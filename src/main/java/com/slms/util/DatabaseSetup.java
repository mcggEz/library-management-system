package com.slms.util;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void init() {
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            // Users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL)");

            // Members table
            stmt.execute("CREATE TABLE IF NOT EXISTS members (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "school TEXT NOT NULL, " +
                "gender TEXT NOT NULL, " +
                "id_number TEXT NOT NULL UNIQUE)");

            // Books table
         // In books table creation
stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
"title TEXT NOT NULL, " +
"author TEXT, " +
"isbn TEXT UNIQUE, " +
"genre TEXT, " +                     // ← Add this
"year INTEGER, " +
"available INTEGER DEFAULT 1)");    // ← Add this

            // Borrowed books table
            stmt.execute("CREATE TABLE IF NOT EXISTS borrowed_books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "member_id INTEGER NOT NULL, " +
                "book_id INTEGER NOT NULL, " +
                "borrow_date TEXT NOT NULL, " +
                "return_date TEXT, " +
                "FOREIGN KEY(member_id) REFERENCES members(id), " +
                "FOREIGN KEY(book_id) REFERENCES books(id))");

            // Attendance table
            stmt.execute("CREATE TABLE IF NOT EXISTS attendance (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "member_id INTEGER NOT NULL, " +
                "date TEXT NOT NULL, " +
                "status TEXT NOT NULL, " +
                "FOREIGN KEY(member_id) REFERENCES members(id))");

            System.out.println("✅ All tables created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
