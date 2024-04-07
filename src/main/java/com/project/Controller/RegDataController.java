package com.project.Controller;

import java.io.IOException;
import java.util.Optional;

import com.project.Database.DatabaseService;
import com.project.User.DataResources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegDataController {
    private DatabaseService databaseService = new DatabaseService();

    @FXML
    private ImageView btnBack;

    @FXML
    private Button formSubmit;

    @FXML
    private TextField fieldNameRegData;

    @FXML
    private TextField fieldEmailRegData;

    @FXML
    private ChoiceBox<String> choiceGenderRegData;

    @FXML
    private TextField fieldPhoneRegData;

    @FXML
    private TextField fieldBirthRegData;

    @FXML
    private ChoiceBox<String> choiceCategoryRegData;

    @FXML
    private TextField fieldQuantityRegData;

    @FXML
    void backToHomepage(MouseEvent event) {
        try {
            Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("HomepageUser.fxml"));
            Scene newScene = new Scene(source);
            Stage meinStage = (Stage) btnBack.getScene().getWindow();
            meinStage.setTitle("Homepage");
            meinStage.setScene(newScene);
            meinStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void submitData(ActionEvent event) {
        DataResources setRegData = new DataResources();

        setRegData.setName(fieldNameRegData.getText());
        setRegData.setEmail(fieldEmailRegData.getText());
        setRegData.setGender(choiceGenderRegData.getValue());
        setRegData.setPhone(fieldPhoneRegData.getText());
        setRegData.setBirth(fieldBirthRegData.getText());
        setRegData.setCategory(choiceCategoryRegData.getValue());
        setRegData.setQuantity(Integer.parseInt(fieldQuantityRegData.getText()));

        databaseService.insertRegDataToDatabase(setRegData);

        fieldNameRegData.clear();
        fieldEmailRegData.clear();
        choiceGenderRegData.setValue("Select Gender");
        fieldPhoneRegData.clear();
        fieldBirthRegData.clear();
        choiceCategoryRegData.setValue("Select Category");
        fieldQuantityRegData.clear();

        Alert registerInfo = new Alert(Alert.AlertType.INFORMATION);
        registerInfo.setTitle("Register");
        registerInfo.setHeaderText("Registration Data Success");
        registerInfo.setContentText("You can check your data in View Data Page");

        Optional<ButtonType> confirm = registerInfo.showAndWait();
        if (confirm.get() == ButtonType.OK) {
            registerInfo.close();
        }
    }

    @FXML
    void initialize() {
        choiceGenderRegData.setValue("Select Gender");
        choiceCategoryRegData.setValue("Select Category");
        choiceGenderRegData.getItems().addAll("Male", "Female");
        choiceCategoryRegData.getItems().addAll("Platinum", "Gold", "Diamond", "VVIP", "Mega VVIP");
    }
}
