package com.project.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.Optional;

import com.project.Database.DatabaseService;
import com.project.User.DataAccount;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class AppController {

    @FXML
    private TextField fieldUsernameLogin;

    @FXML
    private TextField fieldPasswordLogin;

    @FXML
    private TextField fieldNameRegister;

    @FXML
    private TextField fieldEmailRegister;

    @FXML
    private TextField fieldUsernameRegister;

    @FXML
    private TextField fieldPasswordRegister;

    @FXML
    private Button btnRegisterAccount;

    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnRegisterData;

    @FXML
    private Button btnView;

    private DatabaseService databaseService = new DatabaseService();
    private DataAccount dataAccount = new DataAccount();

    @FXML
    void submitUserLogin(ActionEvent event) {
        dataAccount.setAccountUsername(fieldUsernameLogin.getText());
        dataAccount.setAccountPassword(fieldPasswordLogin.getText());

        if (databaseService.getAccountValidation(dataAccount)) {
            try {
                Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("HomepageUser.fxml"));
                Scene newScene = new Scene(source);
                Stage mainStage = (Stage) btnLogin.getScene().getWindow();
                mainStage.setTitle("Homepage");
                mainStage.setScene(newScene);
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(databaseService.getTimeNow() + "  DONE  " + " User Login Berhasil");
        } else {
            Alert loginInfo = new Alert(Alert.AlertType.ERROR);
            loginInfo.setTitle("Failed");
            loginInfo.setHeaderText("Login Failed");
            loginInfo.setContentText("Username or Password Wrong");
            loginInfo.showAndWait();
            System.out.println(databaseService.getTimeNow() + "  ERROR " + " User Login Gagal");
        }
        fieldUsernameLogin.clear();
        fieldPasswordLogin.clear();
    }

    @FXML
    void backToLogin(MouseEvent event) {
        try {
            Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
            Scene newScene = new Scene(source);
            Stage mainStage = (Stage) btnBack.getScene().getWindow();
            mainStage.setTitle("Login");
            mainStage.setScene(newScene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openRegisterAccount(ActionEvent event) {
        try {
            Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("Register.fxml"));
            Scene newScene = new Scene(source);
            Stage mainStage = (Stage) btnRegister.getScene().getWindow();
            mainStage.setTitle("Registration Account");
            mainStage.setScene(newScene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void submitRegisterAccount(ActionEvent event) {

        dataAccount.setAccountName(fieldNameRegister.getText());
        dataAccount.setAccountEmail(fieldEmailRegister.getText());
        dataAccount.setAccountUsername(fieldUsernameRegister.getText());
        dataAccount.setAccountPassword(fieldPasswordRegister.getText());

        databaseService.insertAccountToDatabase(dataAccount);

        fieldNameRegister.clear();
        fieldEmailRegister.clear();
        fieldUsernameRegister.clear();
        fieldPasswordRegister.clear();

        Alert registerInfo = new Alert(Alert.AlertType.INFORMATION);
        registerInfo.setTitle("Register");
        registerInfo.setHeaderText("Registration Account Success");
        registerInfo.setContentText("Please Login Your Account");

        Optional<ButtonType> confirm = registerInfo.showAndWait();
        if (confirm.get() == ButtonType.OK) {
            backToLogin(null);
        }
    }

    @FXML
    void openRegisterMenu(ActionEvent event) {
        try {
            Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("Reg-Data.fxml"));
            Scene newScene = new Scene(source);
            Stage mainStage = (Stage) btnRegisterData.getScene().getWindow();
            mainStage.setTitle("Registration Data");
            mainStage.setScene(newScene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openViewMenu(ActionEvent event) {
        try {
            Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("View-Data.fxml"));
            Scene newScene = new Scene(source);
            Stage mainStage = (Stage) btnView.getScene().getWindow();
            mainStage.setTitle("View Data");
            mainStage.setScene(newScene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        Alert closeConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        closeConfirm.setTitle("Logout");
        closeConfirm.setContentText("Are you sure to logout??");
        Optional<ButtonType> confirm = closeConfirm.showAndWait();

        if (confirm.get() == ButtonType.OK) {
            Platform.exit();
            System.out.println(databaseService.getTimeNow() + "  DONE  " + " Keluar Aplikasi");
        } else {
            closeConfirm.close();
            System.out.println(databaseService.getTimeNow() + "  CANCEL " + " Gagal Keluar Aplikasi");
        }
    }
}
