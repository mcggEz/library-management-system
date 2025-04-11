module com.slms {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.slms to javafx.fxml;
    exports com.slms;
}
