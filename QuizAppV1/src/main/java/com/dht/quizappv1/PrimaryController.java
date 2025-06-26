package com.dht.quizappv1;

import com.dht.utils.MyAlert;
import com.dht.utils.MyStage;
import java.io.IOException;
import javafx.event.ActionEvent;

public class PrimaryController {

    public void handleQuestionManagement(ActionEvent event) throws IOException {
        MyStage.getInstance().showStage("questions.fxml");
    }

    public void handlePractice(ActionEvent event) {
        MyAlert.getInstance().showMsg("Comming soon...");
    }
}
