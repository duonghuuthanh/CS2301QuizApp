/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dht.quizappv1;

import com.dht.pojo.Question;
import com.dht.services.BaseServices;
import com.dht.services.questions.LimitQuestionServicesDecorator;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class PracticeController implements Initializable {
    @FXML private TextField txtNum;
    @FXML private VBox vboxChoices;
    @FXML private Text txtContent;
    @FXML private Text txtResult;
    
    private List<Question> questions;
    private int position;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void handleStart(ActionEvent event) throws SQLException {
        int n =  Integer.parseInt(this.txtNum.getText());
        BaseServices s = new LimitQuestionServicesDecorator(n);
        questions = s.list();
        
        this.position = 0;
        this.loadQuestion();
    }
    
    public void handleCheck(ActionEvent event) {
        Question q = this.questions.get(this.position);
        
        for (int i = 0; i < q.getChoices().size(); i++)
            if (q.getChoices().get(i).isCorrect() == true) {
                RadioButton r = (RadioButton) this.vboxChoices.getChildren().get(i);
                if (r.isSelected()) {
                    this.txtResult.setText("CHÍNH XÁC!");
                    this.txtResult.setStyle("-fx-fill: green"); 
                } else {
                    this.txtResult.setText("SAI RỒI!");
                    this.txtResult.setStyle("-fx-fill: red");
                }
                
                break;
            }
    }
    
    public void handleNext(ActionEvent event) {
        if (this.position < this.questions.size() - 1) {
            this.position++;
            this.loadQuestion();
        }
    }
    
    private void loadQuestion() {
        Question q = this.questions.get(this.position);
        this.txtContent.setText(q.getContent());
        
        ToggleGroup g = new ToggleGroup();
        
        this.vboxChoices.getChildren().clear();
        for (var c: q.getChoices()) {
            RadioButton r = new RadioButton(c.getContent());
            r.setToggleGroup(g);
            
            this.vboxChoices.getChildren().add(r);
        }
    }
}
