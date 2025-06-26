module com.dht.quizappv1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.dht.quizappv1 to javafx.fxml;
    exports com.dht.quizappv1;
}
