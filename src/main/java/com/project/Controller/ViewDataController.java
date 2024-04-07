package com.project.Controller;

import java.io.IOException;
import java.util.Optional;

import com.project.Database.DatabaseService;
import com.project.User.DataResources;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewDataController {

    private DatabaseService databaseService = new DatabaseService();

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnPrint;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField fieldSearchData;

    @FXML
    private TableColumn<DataResources, String> categoryColumn;

    @FXML
    private TableColumn<DataResources, String> emailColumn;

    @FXML
    private TableColumn<DataResources, String> birthColumn;

    @FXML
    private TableColumn<DataResources, String> genderColumn;

    @FXML
    private TableColumn<DataResources, String> nameColumn;

    @FXML
    private TableColumn<DataResources, String> phoneColumn;

    @FXML
    private TableColumn<DataResources, String> quantityColumn;

    @FXML
    private TableColumn<DataResources, Integer> numberColumn;

    @FXML
    public TableView<DataResources> tableViewData;

    @FXML
    void selectedData(ActionEvent event) {
        DataResources selectData = tableViewData.getSelectionModel().getSelectedItem();
        if (selectData != null) {
            System.out.println(selectData.getId());
        } else {
            System.out.println(databaseService.getTimeNow() + "  ERROR " + " Tidak Ada Data Terseleksi");
        }
    }

    @FXML
    void searchData(ActionEvent event) {
        String search = fieldSearchData.getText();
        fieldSearchData.clear();
        if (!search.isEmpty()) {
            databaseService.searchRegDataFromDatabase(search, tableViewData);
        } else {
            Alert searchInfo = new Alert(Alert.AlertType.ERROR);
            searchInfo.setTitle("Not Found");
            searchInfo.setHeaderText("Field is empty");
            searchInfo.setContentText("Please enter the name data you want to search for");
            searchInfo.showAndWait();
            System.out.println(databaseService.getTimeNow() + "  ERROR " + " Data Tidak Ditemukan ");
        }
    }

    @FXML
    void viewDataFromDatabase(ActionEvent event) {
    }

    @FXML
    void editData(ActionEvent event) {
        DataResources editData = tableViewData.getSelectionModel().getSelectedItem();
        if (editData != null) {
            System.out.println(databaseService.getTimeNow() + "  DONE  " + " Data Terseleksi "
                    + editData.getName());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Edit-Data.fxml"));
                Parent source = loader.load();

                EditDataController editDataController = loader.getController();
                editDataController.displaySelectedData(editData);

                Scene newScene = new Scene(source);
                Stage mainStage = (Stage) btnEdit.getScene().getWindow();
                mainStage.setTitle("Edit Data");
                mainStage.setScene(newScene);
                mainStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert editInfo = new Alert(Alert.AlertType.ERROR);
            editInfo.setTitle("Edit Data");
            editInfo.setHeaderText("No selected data");
            editInfo.setContentText("Please select data");
            editInfo.showAndWait();
            System.out.println(databaseService.getTimeNow() + "  ERROR " + " Tidak Ada Data Terseleksi");
        }
    }

    @FXML
    void deleteData(ActionEvent event) {
        DataResources deleteData = tableViewData.getSelectionModel().getSelectedItem();
        if (deleteData != null) {
            Alert deleteConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirm.setTitle("Delete Confirmation");
            deleteConfirm.setHeaderText("Are you sure?");
            Optional<ButtonType> confirm = deleteConfirm.showAndWait();
            if (confirm.get() == ButtonType.OK) {
                if (databaseService.deleteRegDataFromDatabase(deleteData)) {
                    ObservableList<DataResources> dataList = tableViewData.getItems();
                    dataList.remove(deleteData);
                    databaseService.viewRegDataFromDatabase(tableViewData);
                    System.out.println(databaseService.getTimeNow() + "  DONE  " + " Data "
                            + deleteData.getName() + " Berhasil Dihapus ");
                } else {
                    System.out.println(databaseService.getTimeNow() + "  ERROR " + " Data Gagal Dihapus ");
                }
            } else {
                deleteConfirm.close();
                System.out.println(databaseService.getTimeNow() + "  CANCEL " + " Data Gagal Dihapus ");
            }
        } else {
            Alert deleteInfo = new Alert(Alert.AlertType.ERROR);
            deleteInfo.setTitle("Delete Data");
            deleteInfo.setHeaderText("No selected data");
            deleteInfo.setContentText("Please select data");
            deleteInfo.showAndWait();
            System.out.println(databaseService.getTimeNow() + "  ERROR " + " Tidak Ada Data Terseleksi");
        }
    }

    @FXML
    void printData(ActionEvent event) {
        PrinterJob myPrinter = PrinterJob.createPrinterJob();

        if (myPrinter != null) {
            boolean logPrint = myPrinter.showPrintDialog(tableViewData.getScene().getWindow());
            if (logPrint) {
                boolean configPrint = myPrinter.printPage(tableViewData);
                if (configPrint) {
                    myPrinter.endJob();
                    System.out.println(databaseService.getTimeNow() + "  DONE  " + " Data Berhasil Diprint");
                } else {
                    System.out.println(databaseService.getTimeNow() + "  ERROR " + " Datat Gagaal Diprint");
                }
            }
        } else {
            System.out.println(databaseService.getTimeNow() + "  ERROR " + " Printer Tidak Ditemukan");
        }
    }

    @FXML
    void backToHomepage(ActionEvent event) {
        try {
            Parent source = FXMLLoader.load(getClass().getClassLoader().getResource("HomepageUser.fxml"));
            Scene newScene = new Scene(source);
            Stage mainStage = (Stage) btnBack.getScene().getWindow();
            mainStage.setTitle("Homepage");
            mainStage.setScene(newScene);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

        numberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        genderColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        birthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirth()));
        categoryColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        quantityColumn.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        databaseService.viewRegDataFromDatabase(tableViewData);
    }
}