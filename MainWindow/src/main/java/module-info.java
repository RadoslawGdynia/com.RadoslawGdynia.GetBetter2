module MainWindow {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires Calendar;
    requires slf4j.api;

    opens GetBetterMain;
}