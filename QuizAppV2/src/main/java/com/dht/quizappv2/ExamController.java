/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dht.quizappv2;

import com.dht.pojo.Question;
import com.dht.pojo.Choice;
import com.dht.services.exam.ExamStrategy;
import com.dht.services.exam.ExamTypes;
import com.dht.services.exam.FixedStrategy;
import com.dht.services.exam.SpecificExam;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import com.dht.utils.MyAlert;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ExamController implements Initializable {
    @FXML private ComboBox<ExamTypes> cbTypes;
    @FXML private ListView<Question> lvQuestions;
    @FXML private TextField txtNum;
    private List<Question> questions;
    private Map<Integer, Choice> answers = new  HashMap<>();
    private ExamStrategy s;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTypes.setItems(FXCollections.observableArrayList(ExamTypes.values()));
        
        this.txtNum.setVisible(false);
        this.cbTypes.getSelectionModel().selectedIndexProperty().addListener(e -> {
            if (this.cbTypes.getSelectionModel().getSelectedItem() == ExamTypes.SPECIFIC)
                this.txtNum.setVisible(true);
            else
                this.txtNum.setVisible(false);
        });
        
        this.lvQuestions.setCellFactory(param -> new ListCell<Question>() {
            @Override
            protected void updateItem(Question question, boolean empty) {
                super.updateItem(question, empty); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                
                if (question == null || empty == true)
                    setGraphic(null);
                else {
                    VBox v = new VBox(5);
                    
                    v.setStyle("-fx-border-width:1;-fx-border-color:gray;-fx-padding:8;");
                    
                    Text t = new Text(question.getContent());
                    v.getChildren().add(t);
                    
                    ToggleGroup g = new ToggleGroup();
                    for (var c: question.getChoices()) {
                        RadioButton r = new RadioButton(c.getContent());
                        r.setToggleGroup(g);
                        
                        // bổ sung
                        if (answers.get(question.getId()) == c)
                            r.setSelected(true);
                        
                        r.setOnAction(ev -> {
                            if (r.isSelected())
                                answers.put(question.getId(), c);
                        });
                        
                        v.getChildren().add(r);
                    }
                    
                    setGraphic(v);
                }
            }
            
        });
    }    
    
    public void startHandle(ActionEvent e) {
        this.answers.clear();
        
        s = new FixedStrategy();
        
        if (this.cbTypes.getSelectionModel().getSelectedItem() == ExamTypes.SPECIFIC) {
            s = new SpecificExam(Integer.parseInt(this.txtNum.getText()));
        }
        
        try {
            this.questions = s.getQuestions();
            
            Collections.shuffle(this.questions);
            
            this.lvQuestions.setItems(FXCollections.observableList(this.questions));
        } catch (SQLException ex) {
            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void markHandle(ActionEvent e) {
        int count = 0;
        for (var c: answers.values())
            if (c.isCorrect())
                count++;
        
        MyAlert.getInstance().showMsg(String.format("Bạn làm đúng %d/%d!", count, this.questions.size()));
    }
    
    public void saveHandle(ActionEvent e) {
        Optional<ButtonType> t = MyAlert.getInstance().showMsg("Bạn chắc chắn lưu bài thi?", Alert.AlertType.CONFIRMATION);
        if (t.isPresent() && t.get() == ButtonType.OK) {
            s.saveExam(this.questions);
        }
    }
}
