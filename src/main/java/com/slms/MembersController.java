package com.slms;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import com.slms.util.DBUtil;

public class MembersController {

    @FXML private TextField nameField;
    @FXML private TextField schoolField;
    @FXML private TextField idNumberField;
    @FXML private ComboBox<String> genderBox;
    @FXML private TableView<Member> membersTable;
    @FXML private TableColumn<Member, String> nameColumn;
    @FXML private TableColumn<Member, String> schoolColumn;
    @FXML private TableColumn<Member, String> genderColumn;
    @FXML private TableColumn<Member, String> idNumberColumn;

    private ObservableList<Member> memberList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        schoolColumn.setCellValueFactory(new PropertyValueFactory<>("school"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        idNumberColumn.setCellValueFactory(new PropertyValueFactory<>("idNumber"));

        loadMembers();
        membersTable.setItems(memberList);
    }

    private void loadMembers() {
        memberList.clear();
        String sql = "SELECT * FROM members";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Member member = new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("school"),
                        rs.getString("gender"),
                        rs.getString("id_number")
                );
                memberList.add(member);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddMember() {
        String name = nameField.getText();
        String school = schoolField.getText();
        String gender = genderBox.getValue();
        String idNumber = idNumberField.getText();

        if (name.isEmpty() || school.isEmpty() || gender == null || idNumber.isEmpty()) {
            showAlert("Missing Input", "Please fill in all member fields.");
            return;
        }

        String sql = "INSERT INTO members (name, school, gender, id_number) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, school);
            stmt.setString(3, gender);
            stmt.setString(4, idNumber);
            stmt.executeUpdate();

            loadMembers(); // Reload the updated list
            clearFields();

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                showAlert("Duplicate ID", "A member with this ID number already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        nameField.clear();
        schoolField.clear();
        genderBox.setValue(null);
        idNumberField.clear();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
