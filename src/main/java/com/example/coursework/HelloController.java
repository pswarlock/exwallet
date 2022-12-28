package com.example.coursework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

import javafx.scene.control.ChoiceBox;

public class HelloController {

    @FXML
    private Button closeBtn;

    @FXML
    private Button maximizeBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private Button newAcc;

    @FXML
    private PasswordField passwordSignIn;

    @FXML
    private Button signInBtn;

    @FXML
    private TextField usernameSignIn;

    @FXML
    private Button oldAcc;

    @FXML
    private PasswordField passwordSignUp;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField usernameSignup;


    @FXML
    private Label welcomeLabel;


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
    void onNewAccountClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) newAcc.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    void onSignInBtnClick(ActionEvent event) throws SQLException, IOException, InterruptedException {
        if (usernameSignIn.getText().isBlank() == false && passwordSignIn.getText().isBlank() == false) {
            try{
                validateLogin();
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("DATABASE CONNECTION ERROR");
                alert.setContentText("Please Check Database Connection!");
                alert.showAndWait();
            }

        }else {
            welcomeLabel.setText("Pls Enter username and password");
            signInBtn.setStyle("-fx-background-color: #FF0000");

        }

    }

    public void validateLogin() throws SQLException, IOException {
        dbconn conn = new dbconn();
        Connection db = conn.getdbconnection();

        String username = usernameSignIn.getText();
        String password = passwordSignIn.getText();

        String login = "SELECT username, password from javafx.credentials where username = '" + username + "'";
        Statement statement = db.createStatement();
        ResultSet resultSet = statement.executeQuery(login);

        while (resultSet.next()) {
            String actualPassword = resultSet.getString(2);
            String actualUsername = resultSet.getString(1);

            if (Objects.equals(username, actualUsername)) {
                if (Objects.equals(password, actualPassword)) {
                    signInBtn.setStyle("-fx-background-color: #028f46");
                    signInBtn.setText("Yay!");

//                    Stage stage = (Stage) signInBtn.getScene().getWindow();
//                    stage.close();
//                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
//                    Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
//                    Scene scene = new Scene(root);
//                    scene.setFill(Color.TRANSPARENT);
//                    primaryStage.setScene(scene);
//                    primaryStage.show();

                    Stage stage = (Stage) signInBtn.getScene().getWindow();
                    stage.close();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
                    Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    dashboardController.getDetails(username);


                    } else {
                        welcomeLabel.setText("Incorrect Password");
                        signInBtn.setText("Try Again");
                        signInBtn.setStyle("-fx-background-color: #FF0000");
                    }

                } else {
                    welcomeLabel.setText("Incorrect Username Bro");
                    signInBtn.setText("Try Again");
                    signInBtn.setStyle("-fx-background-color: #FF0000");
                }
            }

        }

    @FXML
    void onOldAccountClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) oldAcc.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    void onSignUpBtnClick(ActionEvent event) throws SQLException, IOException {
        dbconn conn = new dbconn();

        String username = usernameSignup.getText();
        String password = passwordSignUp.getText();

        Connection db = conn.getdbconnection();
        String signUp = "INSERT INTO javafx.credentials (username, password) VALUES (?,?)";

        PreparedStatement push = db.prepareStatement(signUp);
        push.setString(1,username);
        push.setString(2,password);
        push.execute();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account Created");
        alert.setHeaderText("Account Created!");
        alert.setContentText("Your Account has been Created. Please log in from the login screen");
        alert.showAndWait();

        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Stage primaryStage = new Stage(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();



    }



}
