package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class reportController implements Initializable {

    @FXML
    private Button addEntryBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private Button goBackHomeBtn;

    @FXML
    private Button maximizeBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private TableView<Data> reportTable;

    private List<Data> data;

    @FXML
    private TableColumn<Data, String> note;

    @FXML
    private TableColumn<Data, Integer> recID;

    @FXML
    private TableColumn<Data, String> type;

    @FXML
    private TableColumn<Data, Integer> value;

    @FXML
    private TableColumn<Data, String> category;

    @FXML
    void onAddEntryBtnClick(ActionEvent event) {

    }
    Connection connection;
    PreparedStatement preparedStatement;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {


        try {
//            getData();
//            TableView<Data> reportTable = new TableView<>();
            data = getData();
            final ObservableList<Data> listData = FXCollections.observableList(data);
//            reportTable.setItems(data);

            TableColumn idCol = new TableColumn("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn typeCol = new TableColumn("Type");
            typeCol.setCellValueFactory(new PropertyValueFactory("type"));
            TableColumn categoryCol = new TableColumn("Category");
            categoryCol.setCellValueFactory(new PropertyValueFactory("category"));
            TableColumn valueCol = new TableColumn("Value");
            valueCol.setCellValueFactory(new PropertyValueFactory("value"));
            TableColumn noteCol = new TableColumn("Note");
            noteCol.setCellValueFactory(new PropertyValueFactory("note"));

            ObservableList<String> abc = FXCollections.observableArrayList();
//            reportTable.setItems(listData);

            reportTable.getColumns().setAll(idCol, typeCol, categoryCol, valueCol, noteCol) ;
            reportTable.setPrefWidth(720);
            reportTable.setPrefHeight(300);
            reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            reportTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

//            reportTable.setItems(listData);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<Data> getData() throws SQLException {

        dbconn conn = new dbconn();

        Connection db = conn.getdbconnection();
        String dbData = "SELECT * FROM javafx.records";

        Statement statement = db.createStatement();
        ResultSet resultSet = statement.executeQuery(dbData);

        List <Data> list = new ArrayList<>();
        while (resultSet.next()) {
            Data testdata = new Data();
            testdata.setId(resultSet.getInt("id"));
            testdata.setType(resultSet.getString("type"));
            testdata.setCategory(resultSet.getString("category"));
            testdata.setValue(resultSet.getInt("value"));
            testdata.setNote(resultSet.getString("note"));

            list.add(testdata);
        }
//        TableColumn idCol = new TableColumn("ID");
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        TableColumn typeCol = new TableColumn("Type");
//        typeCol.setCellValueFactory(new PropertyValueFactory("type"));
//        TableColumn categoryCol = new TableColumn("Category");
//        categoryCol.setCellValueFactory(new PropertyValueFactory("category"));
//        TableColumn valueCol = new TableColumn("Value");
//        valueCol.setCellValueFactory(new PropertyValueFactory("value"));
//        TableColumn noteCol = new TableColumn("Note");
//        noteCol.setCellValueFactory(new PropertyValueFactory("note"));

        ObservableList<Data> listData = FXCollections.observableList(list);

        reportTable.setItems(listData);
        return list;
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

}
