/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dht.quizappv1;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Level;
import com.dht.pojo.Question;
import com.dht.services.BaseServices;
import com.dht.services.CategoryServices;
import com.dht.services.LevelServices;
import com.dht.services.UpdateQuestionServices;
import com.dht.utils.MyAlert;
import com.dht.utils.MyConnector;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuestionsController implements Initializable {
    @FXML private ComboBox<Category> cbCates;
    @FXML private ComboBox<Level> cbLevels;
    @FXML private TextArea txtContent;
    @FXML private VBox vboxChoices;
    @FXML private ToggleGroup toggleChoices;
    
    private final static BaseServices cateService = new CategoryServices();
    private final static BaseServices lvlService = new LevelServices();
    private final static UpdateQuestionServices uQService = new UpdateQuestionServices();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbCates.setItems(FXCollections.observableList(cateService.list()));
            this.cbLevels.setItems(FXCollections.observableList(lvlService.list()));
            
        } catch (SQLException ex) {
        }
    }   
    
    public void handleAddChoice(ActionEvent event) {
        HBox h = new HBox();
        h.getStyleClass().add("Main");
        
        RadioButton rdo = new RadioButton();
        rdo.setToggleGroup(toggleChoices);
        TextField txt = new TextField();
        txt.getStyleClass().add("Input");
        txt.setPromptText("Nội dung lựa chọn.");
        
        h.getChildren().addAll(rdo, txt);
        
        this.vboxChoices.getChildren().add(h);
    }
    
    public void handleAddQuestion(ActionEvent event) {
        try {
            Question.Builder b = new Question.Builder(this.txtContent.getText(),
                    this.cbCates.getSelectionModel().getSelectedItem(),
                    this.cbLevels.getSelectionModel().getSelectedItem());
            for (var c: this.vboxChoices.getChildren()) {
                HBox h = (HBox) c;
                
                b.addChoice(new Choice(((TextField)h.getChildren().get(1)).getText(),
                            ((RadioButton)h.getChildren().get(0)).isSelected()));
            }
            
            uQService.addQuestion(b.build());
            
            MyAlert.getInstance().showMsg("Thêm câu hỏi thành công!");
        } catch (SQLException ex) {
            MyAlert.getInstance().showMsg("Thêm câu hỏi thất bị, lý do: " + ex.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception ex) {
            MyAlert.getInstance().showMsg("Dữ liệu bị lỗi!", Alert.AlertType.ERROR);
        }
        
    }
}
