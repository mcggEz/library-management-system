package com.slms;

import com.slms.util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import java.time.LocalDate;

public class BorrowedBooksController {

    @FXML private TextField memberField;
    @FXML private TextField bookField;
    @FXML private DatePicker borrowedDate;
    @FXML private DatePicker dueDate;

    @FXML private TableView<BorrowedBook> borrowedTable;
    @FXML private TableColumn<BorrowedBook, String> memberColumn;
    @FXML private TableColumn<BorrowedBook, String> bookColumn;
    @FXML private TableColumn<BorrowedBook, String> borrowedColumn;
    @FXML private TableColumn<BorrowedBook, String> dueColumn;

    private final ObservableList<BorrowedBook> borrowedBooks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        memberColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMember()));
        bookColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBook()));
        borrowedColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBorrowDate()));
        dueColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getReturnDate()));

        loadBorrowedBooks();
    }

    private void loadBorrowedBooks() {
        borrowedBooks.clear();
        String sql = "SELECT b.id, m.name as member, bo.title as book, b.borrow_date, b.return_date " +
                     "FROM borrowed_books b " +
                     "JOIN members m ON b.member_id = m.id " +
                     "JOIN books bo ON b.book_id = bo.id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                borrowedBooks.add(new BorrowedBook(
                    rs.getInt("id"),
                    rs.getString("member"),
                    rs.getString("book"),
                    rs.getString("borrow_date"),
                    rs.getString("return_date")
                ));
            }
            borrowedTable.setItems(borrowedBooks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddBorrowedBook() {
        String memberKeyword = memberField.getText().trim();
        String bookTitle = bookField.getText().trim();
        LocalDate borrow = borrowedDate.getValue();
        LocalDate due = dueDate.getValue();

        if (memberKeyword.isEmpty() || bookTitle.isEmpty() || borrow == null || due == null) {
            showAlert("Missing Fields", "Please fill all fields.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            // Find member ID
            PreparedStatement findMember = conn.prepareStatement(
                "SELECT id FROM members WHERE name LIKE ? OR id_number = ?");
            findMember.setString(1, "%" + memberKeyword + "%");
            findMember.setString(2, memberKeyword);
            ResultSet memberRs = findMember.executeQuery();

            if (!memberRs.next()) {
                showAlert("Error", "Member not found.");
                return;
            }
            int memberId = memberRs.getInt("id");

            // Find book ID
            PreparedStatement findBook = conn.prepareStatement("SELECT id FROM books WHERE title LIKE ?");
            findBook.setString(1, "%" + bookTitle + "%");
            ResultSet bookRs = findBook.executeQuery();

            if (!bookRs.next()) {
                showAlert("Error", "Book not found.");
                return;
            }
            int bookId = bookRs.getInt("id");

            // Insert record
            PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO borrowed_books (member_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)");
            insert.setInt(1, memberId);
            insert.setInt(2, bookId);
            insert.setString(3, borrow.toString());
            insert.setString(4, due.toString());

            insert.executeUpdate();

            showAlert("Success", "Borrowed book record added!");
            clearForm();
            loadBorrowedBooks();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add borrowed book.");
        }
    }

    private void clearForm() {
        memberField.clear();
        bookField.clear();
        borrowedDate.setValue(null);
        dueDate.setValue(null);
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
