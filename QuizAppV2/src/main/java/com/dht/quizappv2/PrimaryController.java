package com.dht.quizappv2;

import com.dht.utils.MyAlert;
import com.dht.utils.MyStage;
import com.dht.utils.themes.DarkThemeFactory;
import com.dht.utils.themes.Theme;
import com.dht.utils.themes.ThemeManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class PrimaryController implements Initializable {
    @FXML ComboBox<Theme> cbThemes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbThemes.setItems(FXCollections.observableArrayList(Theme.values()));
    }
    
    public void handleChangeThemes(ActionEvent event) {
        this.cbThemes.getSelectionModel().getSelectedItem().updateTheme(this.cbThemes.getScene());
    }
    
    public void handleQuestionManagement(ActionEvent event) throws IOException {
        MyStage.getInstance().showStage("questions.fxml");
    }
    
    public void handlePractice(ActionEvent event) throws IOException {
        MyStage.getInstance().showStage("practice.fxml");
    }
    
    public void handleExam(ActionEvent event) {
        MyAlert.getInstance().showMsg("Comming soon...");
    }
    
    public void handleRegister(ActionEvent event) {
        MyAlert.getInstance().showMsg("Comming soon...");
    }
    
    public void handleLogin(ActionEvent event) {
        MyAlert.getInstance().showMsg("Comming soon...");
    }
}
