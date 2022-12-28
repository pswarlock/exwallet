package com.example.coursework;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class expenseController implements Initializable {

    @FXML
    private Button addEntryBtn;

    @FXML
    private TextField amountBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private ChoiceBox<String> expenseChoiceBox;

    private String[] expenseOptions = {"Food", "Transportation", "Communication", "Clothes", "Other"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        expenseChoiceBox.getItems().addAll(expenseOptions);
        expenseChoiceBox.getSelectionModel().select(0);

    }

    @FXML
    private Button maximizeBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private TextField note;

    @FXML
    private Button goBackHomeBtn;



    @FXML
    void onCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    boolean isMaximized = false;

    @FXML
    void onMaxButtonClick(ActionEvent event) {
        Stage stage=(Stage) maximizeBtn.getScene().getWindow();
        if (isMaximized) {
            stage.setMaximized(false);
            isMaximized = false;

        }else{
            stage.setMaximized(true);
            isMaximized = true;
        }
    }


    @FXML
    void onMinButtonClick(ActionEvent event) {
        Stage stage=(Stage) minimizeBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void onAddEntryBtnClick(ActionEvent event) throws SQLException, IOException {

        dbconn conn = new dbconn();

        String value = amountBtn.getText();
        String type = "Expense";
        String category = expenseChoiceBox.getValue();
        String notes = note.getText();

        Connection db = conn.getdbconnection();
        String addExpense = "INSERT INTO javafx.records (type, category, value, note) VALUES (?, ?, ?, ?)";

        PreparedStatement push = db.prepareStatement(addExpense);
        push.setString(1,type);
        push.setString(2,category);
        push.setString(3,value);
        push.setString(4,notes);
        push.execute();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Record Added");
        alert.setHeaderText("Record Added!");
        alert.setContentText("The Record has been Added. Returning to Dashboard");
        alert.showAndWait();

        Stage stage = (Stage) addEntryBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    @FXML
    void onGoBackHomeBtnClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) goBackHomeBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


}
