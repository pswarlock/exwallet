package com.example.coursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private Text balance;

    @FXML
    private Button expenseBtn;

    @FXML
    private Text expensetxt;

    @FXML
    private Button incomeBtn;

    @FXML
    private Text income;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button maximizeBtn;

    @FXML
    private Button reloadBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private Text name;

    @FXML
    private ListView<String> overviewList;

    @FXML
    private Button reportBtn;

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
    void onExpenseBtnClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) expenseBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("expense.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    void onIncomeBtnClick(ActionEvent event) throws IOException {

        Stage stage = (Stage) incomeBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("income.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @FXML
    void onLogOutBtnClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) logOutBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully Logged out");
        alert.setHeaderText("Successfully Logged out");
        alert.setContentText("Successfully Logged out");
        alert.showAndWait();


    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    static String newName;
    public static void getDetails(String a) {
        newName = a;
    }

    public void setName() {
        this.name.setText("Hello, " + newName);
    }

    public void refresh() throws SQLException {
        setName();
        int intIncome = 0;
        int intExpense = 0;
        int intBalance = 0;

        dbconn conn = new dbconn();

        Connection db = conn.getdbconnection();
        String strIncome = "SELECT sum(value) FROM javafx.records WHERE type = 'income'";
        PreparedStatement push = db.prepareStatement(strIncome);
        ResultSet incomeResult = push.executeQuery();
        while (incomeResult.next()) {
            String actualIncome = incomeResult.getString(1);
            try{
                intIncome = Integer.parseInt(actualIncome);
            }catch (Exception a){
                intIncome = 0;
            }

        }

        String expenseStr = "Expense";
        String strExpense = "select sum(value) from javafx.records where type = 'expense'";
        push = db.prepareStatement(strExpense);
        ResultSet expenseResult = push.executeQuery();
        while (expenseResult.next()) {
            String actualExpense = expenseResult.getString(1);
            try{
                intExpense = Integer.parseInt(actualExpense);
            }catch (Exception b){
                intExpense = 0;
            }

        }
//        intIncome = Integer.parseInt(strIncome);
        intBalance = intIncome - intExpense;

        balance.setText(String.valueOf(intBalance));
        income.setText(String.valueOf(intIncome));
        expensetxt.setText(String.valueOf(intExpense));

        if(intIncome < intExpense){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert!");
            alert.setHeaderText("Expenditure Exceeded");
            alert.setContentText("You have spent more than your earned! Chill bro!");
            alert.showAndWait();
        }

    }


    @FXML
    void onReportBtnClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) reportBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("report.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    @FXML
    void onReloadBtnClick(ActionEvent event) throws SQLException {
        setName();

        int intIncome = 0;
        int intExpense = 0;
        int intBalance = 0;

        dbconn conn = new dbconn();

        Connection db = conn.getdbconnection();
        String strIncome = "SELECT sum(value) FROM javafx.records WHERE type = 'income'";
        PreparedStatement push = db.prepareStatement(strIncome);
        ResultSet incomeResult = push.executeQuery();
        while (incomeResult.next()) {
            String actualIncome = incomeResult.getString(1);
            intIncome = Integer.parseInt(actualIncome);
        }

        String expenseStr = "Expense";
        String strExpense = "select sum(value) from javafx.records where type = 'expense'";
        push = db.prepareStatement(strExpense);
        ResultSet expenseResult = push.executeQuery();
        while (expenseResult.next()) {
            String actualExpense = expenseResult.getString(1);
            intExpense = Integer.parseInt(actualExpense);
        }
//        intIncome = Integer.parseInt(strIncome);
        intBalance = intIncome - intExpense;

        balance.setText(String.valueOf(intBalance));
        income.setText(String.valueOf(intIncome));
        expensetxt.setText(String.valueOf(intExpense));

    }

}
