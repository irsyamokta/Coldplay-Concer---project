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

public class EditDataController {

    private DatabaseService databaseService = new DatabaseService();

    @FXML
    private ImageView btnBack;

    @FXML
    private Button btnSubmitNow;

    @FXML
    private TextField fileIdEditData;

    @FXML
    private TextField fieldNameEditData;

    @FXML
    private TextField fieldEmailEditData;

    @FXML
    private ChoiceBox<String> choiceGenderEditData;

    @FXML
    private TextField fieldPhoneEditData;

    @FXML
    private TextField fieldBirthEditData;

    @FXML
    private ChoiceBox<String> choiceCategoryEditData;

    @FXML
    private TextField fieldQuantityEditData;

    @FXML
    void backToViewMenu(MouseEvent event) {
        try {
            Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("View-Data.fxml"));
            Scene newScene = new Scene(source);
            Stage mainStage = (Stage) btnBack.getScene().getWindow();
            mainStage.setTitle("View Data");
            mainStage.setScene(newScene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySelectedData(DataResources selectedData) {
        if (selectedData != null) {
            fileIdEditData.setText(Integer.toString(selectedData.getId()));
            fieldNameEditData.setText(selectedData.getName());
            fieldEmailEditData.setText(selectedData.getEmail());
            choiceGenderEditData.setValue(selectedData.getGender());
            fieldPhoneEditData.setText(selectedData.getPhone());
            fieldBirthEditData.setText(selectedData.getBirth());
            choiceCategoryEditData.setValue(selectedData.getCategory());
            fieldQuantityEditData.setText(Integer.toString(selectedData.getQuantity()));
        }
    }

    @FXML
    void submitEditNow(ActionEvent event) {

        DataResources setEditData = new DataResources();

        setEditData.setId(Integer.parseInt(fileIdEditData.getText()));
        setEditData.setName(fieldNameEditData.getText());
        setEditData.setEmail(fieldEmailEditData.getText());
        setEditData.setGender(choiceGenderEditData.getValue());
        setEditData.setPhone(fieldPhoneEditData.getText());
        setEditData.setBirth(fieldBirthEditData.getText());
        setEditData.setCategory(choiceCategoryEditData.getValue());
        setEditData.setQuantity(Integer.parseInt(fieldQuantityEditData.getText()));

        databaseService.updateRegDataToDatabase(setEditData);

        fieldNameEditData.clear();
        fieldEmailEditData.clear();
        choiceGenderEditData.setValue("Select Gender");
        fieldPhoneEditData.clear();
        fieldBirthEditData.clear();
        choiceCategoryEditData.setValue("Select Category");
        fieldQuantityEditData.clear();

        Alert editInfo = new Alert(Alert.AlertType.INFORMATION);
        editInfo.setTitle("Edit Data");
        editInfo.setHeaderText("Edit Data Success");
        editInfo.setContentText("You can check your data in View Data Page");

        Optional<ButtonType> confirm = editInfo.showAndWait();
        if (confirm.get() == ButtonType.OK) {
            editInfo.close();
        }
    }

    @FXML
    void initialize() {
        choiceGenderEditData.setValue("Select Gender");
        choiceCategoryEditData.setValue("Select Category");
        choiceGenderEditData.getItems().addAll("Male", "Female");
        choiceCategoryEditData.getItems().addAll("Platinum", "Gold", "Diamond", "VVIP", "Mega VVIP");
    }
}
