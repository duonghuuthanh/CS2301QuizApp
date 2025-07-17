module com.dht.quizappv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires lombok;

    opens com.dht.quizappv2 to javafx.fxml;
    exports com.dht.quizappv2;
    exports com.dht.pojo;
}
