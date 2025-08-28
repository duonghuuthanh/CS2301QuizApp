/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dht.quizappv2;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Level;
import com.dht.pojo.Question;
import com.dht.services.questions.BaseQuestionServices;
import com.dht.services.questions.CategoryQuestionServicesDecorator;
import com.dht.services.questions.KeywordQuestionServicesDecorator;
import com.dht.services.questions.LevelQuestionServicesDecorator;
import com.dht.utils.Configs;
import com.dht.utils.FlyweightFactory;
import com.dht.utils.MyAlert;
import java.net.URL;
import java.sql.SQLException;
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
    @FXML private TextArea txtContent;
    @FXML private ComboBox<Category> cbCates;
    @FXML private ComboBox<Category> cbSearchCates;
    @FXML private ComboBox<Level> cbLevels;
    @FXML private ComboBox<Level> cbSearchLevels;
    @FXML private VBox vboxChoices;
    @FXML private ToggleGroup toggleChoice;
    @FXML private TableView<Question> tbQuestions;
    @FXML private TextField txtSearch;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbCates.setItems(FXCollections.observableList(FlyweightFactory.getData(Configs.questionServices, "categories")));
            this.cbSearchCates.setItems(FXCollections.observableList(FlyweightFactory.getData(Configs.questionServices, "categories")));
            this.cbLevels.setItems(FXCollections.observableList(FlyweightFactory.getData(Configs.levelServices, "levels")));
            this.cbSearchLevels.setItems(FXCollections.observableList(FlyweightFactory.getData(Configs.levelServices, "levels")));
            
            this.loadColumns();
            this.tbQuestions.setItems(FXCollections.observableList(Configs.questionServices.list()));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        this.txtSearch.textProperty().addListener(e -> {
            try {
                BaseQuestionServices s = new KeywordQuestionServicesDecorator(Configs.questionServices, this.txtSearch.getText());
                this.tbQuestions.getItems().clear();
                this.tbQuestions.setItems(FXCollections.observableList(s.list()));
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        
        this.cbSearchCates.getSelectionModel().selectedItemProperty().addListener(e -> {
            try {
                BaseQuestionServices s = new CategoryQuestionServicesDecorator(Configs.questionServices, this.cbSearchCates.getSelectionModel().getSelectedItem().getId());
                this.tbQuestions.getItems().clear();
                this.tbQuestions.setItems(FXCollections.observableList(s.list()));
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        
        this.cbSearchLevels.getSelectionModel().selectedItemProperty().addListener(e -> {
            try {
                BaseQuestionServices s = new LevelQuestionServicesDecorator(Configs.questionServices, this.cbSearchLevels.getSelectionModel().getSelectedItem().getId());
                this.tbQuestions.getItems().clear();
                this.tbQuestions.setItems(FXCollections.observableList(s.list()));
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
    }
    
    public void addChoice(ActionEvent event) {
        HBox h = new HBox();
        h.getStyleClass().add("Main");
        
        RadioButton rdo = new RadioButton();
        rdo.setToggleGroup(toggleChoice);
        
        TextField txt = new TextField();
        
        h.getChildren().addAll(rdo, txt);
        
        this.vboxChoices.getChildren().add(h);
    }
    
    public void addQuestion(ActionEvent event) {
        try {
            Question.Builder b = new Question.Builder(this.txtContent.getText(),
                    this.cbCates.getSelectionModel().getSelectedItem(),
                    this.cbLevels.getSelectionModel().getSelectedItem());
            
            for (var c: this.vboxChoices.getChildren()) {
                HBox h = (HBox) c;
                
                Choice choice = new Choice(((TextField)h.getChildren().get(1)).getText(),
                                        ((RadioButton)h.getChildren().get(0)).isSelected());
                
                b.addChoice(choice);
            }
            
            Configs.uQuestionServices.addQuestion(b.build());
            MyAlert.getInstance().showMsg("Thêm câu hỏi thành công!");
        } catch(SQLException ex) {
            MyAlert.getInstance().showMsg("Thêm không thành công, lý do: " + ex.getMessage());
        } catch (Exception ex) {
            MyAlert.getInstance().showMsg("Dữ liệu không hợp lệ!");
        }
    }
    
    private void loadColumns() {
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(100);
        
        TableColumn colContent = new TableColumn("Nội dung câu hỏi");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colContent.setPrefWidth(250);
        
        TableColumn colAction = new TableColumn();
        colAction.setCellFactory(c -> {
            TableCell cell = new TableCell();
            
            Button btn = new Button("Xóa");
            btn.setOnAction(e -> {
                Optional<ButtonType> t = MyAlert.getInstance().showMsg("Xóa câu hỏi thì các đáp án cũng bị xóa theo. Bạn chắc chắn không?", 
                        Alert.AlertType.CONFIRMATION);
                if (t.isPresent() && t.get().equals(ButtonType.OK)) {
                    Question q = (Question) cell.getTableRow().getItem();
                    try {
                        if (Configs.uQuestionServices.deleteQuestioin(q.getId()) == true) {
                            this.tbQuestions.getItems().remove(q);
                            MyAlert.getInstance().showMsg("Xóa câu hỏi thành công!", Alert.AlertType.INFORMATION);
                        } else
                            MyAlert.getInstance().showMsg("Xóa câu hỏi thất bại!", Alert.AlertType.WARNING);
                    } catch (SQLException ex) {
                        MyAlert.getInstance().showMsg("Hệ thống bị lỗi, lý do: " + ex.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            });
            
            cell.setGraphic(btn);
            
            return cell;
        });
        
        this.tbQuestions.getColumns().addAll(colId, colContent, colAction);
    }
}
