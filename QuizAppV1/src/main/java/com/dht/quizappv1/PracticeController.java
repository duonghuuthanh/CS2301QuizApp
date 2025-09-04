/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dht.quizappv1;

import com.dht.pojo.Category;
import com.dht.pojo.Level;
import com.dht.pojo.Question;
import com.dht.services.BaseServices;
import com.dht.services.FlyweightFactory;
import com.dht.services.questions.BaseQuestionServices;
import com.dht.services.questions.CategoryQuestionServicesDecorator;
import com.dht.services.questions.LevelQuestionServicesDecorator;
import com.dht.services.questions.LimitQuestionServicesDecorator;
import com.dht.utils.MyConfigs;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
    @FXML private ComboBox<Category> cbSearchCates;
    @FXML private ComboBox<Level> cbSearchLevels;
    
    private List<Question> questions;
    private int position;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbSearchCates.setItems(FXCollections.observableList(FlyweightFactory.getData(MyConfigs.cateService, "categories")));
            this.cbSearchLevels.setItems(FXCollections.observableList(FlyweightFactory.getData(MyConfigs.lvlService, "levels")));
        } catch (SQLException ex) {
            Logger.getLogger(PracticeController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }    
    
    public void handleStart(ActionEvent event) throws SQLException {
        int n =  Integer.parseInt(this.txtNum.getText());
        BaseQuestionServices s = MyConfigs.quesService;
        
        Category c =this.cbSearchCates.getSelectionModel().getSelectedItem();
        if (c != null)
            s = new CategoryQuestionServicesDecorator(s, c.getId());
        
        Level lvl = this.cbSearchLevels.getSelectionModel().getSelectedItem();
        if (lvl != null)
            s = new LevelQuestionServicesDecorator(s, lvl.getId());
                
        s = new LimitQuestionServicesDecorator(s, n);
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
