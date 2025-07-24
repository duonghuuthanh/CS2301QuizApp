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
import com.dht.services.questions.KeywordQuestionServicesDecorator;
import com.dht.services.questions.QuestionServices;
import com.dht.services.questions.UpdateQuestionServices;
import com.dht.utils.MyAlert;
import com.dht.utils.MyConnector;
import com.dht.utils.MyStage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TableView<Question> tbQuestions;
    @FXML private TextField txtSearch;
    
    private final static BaseServices cateService = new CategoryServices();
    private final static BaseServices lvlService = new LevelServices();
    private final static BaseServices quesService = new QuestionServices();
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
            
            this.loadColumns();
            this.tbQuestions.setItems(FXCollections.observableList(quesService.list()));
        } catch (SQLException ex) {
        }
        
        this.txtSearch.textProperty().addListener(p -> {
            BaseServices s = new KeywordQuestionServicesDecorator(this.txtSearch.getText());
            try {
                this.tbQuestions.setItems(FXCollections.observableList(s.list()));
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
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
    
    private void loadColumns() {
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(100);
        
        TableColumn colContent = new TableColumn("Nội dung câu hỏi");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colContent.setPrefWidth(300);
        
        TableColumn colAction = new TableColumn();
        colAction.setCellFactory(p -> {
            TableCell cell = new TableCell();
            
            Button b = new Button("Xóa");
            b.setOnAction(event -> {
                Optional<ButtonType> t = MyAlert.getInstance().showMsg("Xóa câu hỏi thì các lựa chọn cũng bị xóa theo. Bạn chắc chắn xóa không?", Alert.AlertType.CONFIRMATION);
                if (t.isPresent() && t.get().equals(ButtonType.OK)) {
                    Question q = (Question) cell.getTableRow().getItem();
                    
                    try {
                        if (uQService.deleteQuestion(q.getId()) == true) {
                            MyAlert.getInstance().showMsg("Xóa câu hỏi thành công!");
                            
                            this.tbQuestions.getItems().remove(q);
                        } else
                            MyAlert.getInstance().showMsg("Xóa câu hỏi thất bại!", Alert.AlertType.WARNING);
                    } catch (SQLException ex) {
                        MyAlert.getInstance().showMsg("Hệ thống có lỗi, lý do: " + ex.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            
            });
            
            cell.setGraphic(b);
            
            return cell;
        });
        
        this.tbQuestions.getColumns().addAll(colId, colContent, colAction);
    }
}
