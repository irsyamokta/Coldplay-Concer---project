package com.project.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.project.User.DataAccount;
import com.project.User.DataResources;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class DatabaseService {

    DataAccount dataAccount = new DataAccount();

    public String getTimeNow() {
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime timeNow = LocalDateTime.now();
        String formatTime = timeNow.format(time);
        return formatTime;
    }

    public Connection getDBConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_concer";
        String username = "root";
        String password = "";

        return DriverManager.getConnection(url, username, password);
    }

    public void insertAccountToDatabase(DataAccount dataAccount) {
        try {
            Connection connect = getDBConnection();
            String query = "INSERT INTO registration_account (name, email, username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connect.prepareStatement(query);
            stmt.setString(1, dataAccount.getAccountName());
            stmt.setString(2, dataAccount.getAccountEmail());
            stmt.setString(3, dataAccount.getAccountUsername());
            stmt.setString(4, dataAccount.getAccountPassword());
            stmt.executeUpdate();
            System.out.println(getTimeNow() + "  DONE  " + " Registrasi Berhasil");
            connect.close();
        } catch (SQLException e) {
            System.out.println(getTimeNow() + "  ERROR " + " Registrasi Gagal " + e.getMessage());
        }
    }

    public boolean getAccountValidation(DataAccount dataAccount) {
        try {
            Connection connect = getDBConnection();
            String query = "SELECT username, password FROM registration_account WHERE username = ? AND password = ?";
            PreparedStatement stmt = connect.prepareStatement(query);
            stmt.setString(1, dataAccount.getAccountUsername());
            stmt.setString(2, dataAccount.getAccountPassword());
            stmt.executeQuery();
            return stmt.getResultSet().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void searchRegDataFromDatabase(String search, TableView<DataResources> tableViewData) {
        try {
            Connection connect = getDBConnection();
            String query = "SELECT * FROM registration_data WHERE name LIKE ?";
            try (PreparedStatement stmt = connect.prepareStatement(query)) {
                stmt.setString(1, "%" + search + "%");
                try (ResultSet viewSet = stmt.executeQuery()) {
                    int searchIndex = -1;
                    List<DataResources> searchFind = new ArrayList<>();
                    while (viewSet.next()) {

                        DataResources searchData = new DataResources();

                        searchData.setId(viewSet.getInt("id"));
                        searchData.setName(viewSet.getString("name"));
                        searchData.setEmail(viewSet.getString("email"));
                        searchData.setGender(viewSet.getString("gender"));
                        searchData.setPhone(viewSet.getString("phone"));
                        searchData.setBirth(viewSet.getString("birth"));
                        searchData.setCategory(viewSet.getString("category"));
                        searchData.setQuantity(viewSet.getInt("quantity"));

                        searchFind.add(searchData);

                        System.out.println(
                                getTimeNow() + "  DONE  " + " Data Ditemukan " + searchData.getName());

                        searchIndex = searchData.getId();
                    }
                    if (searchIndex != -1) {
                        tableViewData.getSelectionModel().select(searchIndex - 1);
                    } else {
                        Alert searchInfo = new Alert(Alert.AlertType.ERROR);
                        searchInfo.setTitle("Not Found");
                        searchInfo.setHeaderText("Data Not Found");
                        searchInfo.setContentText("Data is not available or registration has not been completed");
                        searchInfo.showAndWait();
                        System.out.println(getTimeNow() + "  ERROR " + " Data Tidak Ditemukan ");
                    }
                }
            }
            connect.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void viewRegDataFromDatabase(TableView<DataResources> tableViewData) {
        try {
            Connection connect = getDBConnection();
            String query = "SELECT * FROM registration_data";
            try (PreparedStatement stmt = connect.prepareStatement(query)) {
                try (ResultSet resultSet = stmt.executeQuery()) {
                    tableViewData.getItems().clear();
                    while (resultSet.next()) {

                        DataResources viewData = new DataResources();

                        viewData.setId(resultSet.getInt("id"));
                        viewData.setName(resultSet.getString("name"));
                        viewData.setEmail(resultSet.getString("email"));
                        viewData.setGender(resultSet.getString("gender"));
                        viewData.setPhone(resultSet.getString("phone"));
                        viewData.setBirth(resultSet.getString("birth"));
                        viewData.setCategory(resultSet.getString("category"));
                        viewData.setQuantity(resultSet.getInt("quantity"));

                        tableViewData.getItems().add(viewData);
                    }
                }
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRegDataToDatabase(DataResources insertData) {
        try {
            Connection connect = getDBConnection();
            String query = "INSERT INTO registration_data (name, gender, email, phone, birth, category, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connect.prepareStatement(query);
            stmt.setString(1, insertData.getName());
            stmt.setString(3, insertData.getEmail());
            stmt.setString(2, insertData.getGender());
            stmt.setString(4, insertData.getPhone());
            stmt.setString(5, insertData.getBirth());
            stmt.setString(6, insertData.getCategory());
            stmt.setInt(7, insertData.getQuantity());
            stmt.executeUpdate();
            System.out.println(getTimeNow() + "  DONE  " + " Registrasi Data Berhasil");
            connect.close();
        } catch (SQLException e) {
            System.out.println(getTimeNow() + "  ERROR " + " Registrasi Data Gagal " + e.getMessage());
        }
    }

    public boolean updateRegDataToDatabase(DataResources setEditData) {
        try {
            Connection connect = getDBConnection();
            String query = "UPDATE registration_data SET name = ?, email = ?, gender = ?, phone = ?, birth = ?, category = ?, quantity = ? WHERE id = ?";
            try (PreparedStatement stmt = connect.prepareStatement(query)) {
                stmt.setString(1, setEditData.getName());
                stmt.setString(2, setEditData.getEmail());
                stmt.setString(3, setEditData.getGender());
                stmt.setString(4, setEditData.getPhone());
                stmt.setString(5, setEditData.getBirth());
                stmt.setString(6, setEditData.getCategory());
                stmt.setInt(7, setEditData.getQuantity());
                stmt.setInt(8, setEditData.getId());
                System.out.println(getTimeNow() + "  DONE  " + " Edit Data Berhasil");
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.out.println(getTimeNow() + "  ERROR " + " Edit Data Gagal " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRegDataFromDatabase(DataResources deleteData) {
        try {
            Connection connect = getDBConnection();
            String query = "DELETE FROM registration_data WHERE id = ?";

            try (PreparedStatement stmt = connect.prepareStatement(query)) {
                stmt.setInt(1, deleteData.getId());
                int rowsAffected = stmt.executeUpdate();

                String resetId = "SET @num := 0";
                try (PreparedStatement resetIdStmt = connect.prepareStatement(resetId)) {
                    resetIdStmt.executeUpdate(resetId);
                }

                resetId = "UPDATE registration_data SET id = @num := (@num+1)";
                try (PreparedStatement resetIdStmt = connect.prepareStatement(resetId)) {
                    resetIdStmt.executeUpdate(resetId);
                }

                resetId = "ALTER TABLE registration_data AUTO_INCREMENT = 1";
                try (PreparedStatement resetIdStmt = connect.prepareStatement(resetId)) {
                    resetIdStmt.executeUpdate(resetId);
                }
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
