package com.slms;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.slms.util.DBUtil;
import java.sql.*;

public class BooksController {

    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, Boolean> availableColumn;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;
    @FXML private TextField genreField;
    @FXML private TextField yearField;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        loadBooks();
        booksTable.setItems(bookList);
    }

    private void loadBooks() {
        bookList.clear();
        String sql = "SELECT * FROM books";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookList.add(new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getString("genre"),
                    rs.getInt("year"),
                    rs.getInt("available") == 1
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        String genre = genreField.getText();
        String yearStr = yearField.getText();

        if (title.isEmpty() || isbn.isEmpty() || yearStr.isEmpty()) {
            showAlert("Missing Fields", "Title, ISBN, and Year are required.");
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);

            String sql = "INSERT INTO books (title, author, isbn, genre, year, available) VALUES (?, ?, ?, ?, ?, 1)";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setString(3, isbn);
                pstmt.setString(4, genre);
                pstmt.setInt(5, year);
                pstmt.executeUpdate();

                loadBooks();
                clearForm();
            }

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Year must be a number.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add book. ISBN might already exist.");
        }
    }

    private void clearForm() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        genreField.clear();
        yearField.clear();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
