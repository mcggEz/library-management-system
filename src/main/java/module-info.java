module com.slms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.slms to javafx.fxml;


    exports com.slms;

}
