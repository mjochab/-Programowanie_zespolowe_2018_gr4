/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Winnicki Kamil
 */
public class ListUsersController {

    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    @FXML
    private Button addStudent;
    @FXML
    private Button delUser;
    @FXML
    private TableView<Users> tableUsers;
    @FXML
    private TableColumn<Users, String> userName;
    @FXML
    private TableColumn<Users, String> userSurname;
    @FXML
    private TableColumn<Users, Integer> userTel;
    @FXML
    private TableColumn<Users, String> userEmail;
    @FXML
    private TableColumn<Users, Integer> userPesel;
    @FXML
    private TableColumn<Users, String> userRank;

    private ObservableList<Users> usersList;
    @FXML
    private Button editUser;

    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadUsers();
    }

    @FXML
    public void registrationUser() throws IOException, SQLException, ClassNotFoundException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AddUser.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddUserController addUserController = loader.getController();
        addUserController.setMainController(mainController);
        mainController.setScreen(pane);

    }

   


    @FXML
    public void Back() throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AdminPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminPanelController adminController = loader.getController();
        adminController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void loadUsers() throws ClassNotFoundException, SQLException {
        try {

            Connection conn = base.baseConnection();
            usersList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(""
                    + "SELECT uzytkownicy.ID_uzytkownika, uzytkownicy.ID_uprawnienia, uzytkownicy.imie, uzytkownicy.nazwisko,uzytkownicy.numerTel, uzytkownicy.email, uzytkownicy.pesel, uprawnienia.rodzajUprawnienia "
                    + "FROM uzytkownicy,uprawnienia "
                    + "WHERE uzytkownicy.ID_uprawnienia= uprawnienia.ID_uprawnienia");
            while (rs.next()) {
                if (rs.getInt(1) != SessionService.getUserID()) {
                    usersList.add(new Users(rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getLong(7), rs.getString(8)));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }catch(Exception e){
            System.err.println("Error"+e);
        }

        userName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        userSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        userTel.setCellValueFactory(new PropertyValueFactory<>("TelNumber"));
        userEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        userPesel.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
        userRank.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        tableUsers.setItems(null);
        tableUsers.setItems(usersList);
    }

    @FXML
    private void editUser(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        FXMLLoader loader = new FXMLLoader();

        Users user = null;
        loader.setLocation(getClass().getResource("EditUser.fxml"));
        try {
            user = tableUsers.getSelectionModel().getSelectedItem();
            loader.load();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (user != null) {

            EditUserController editUser = loader.getController();
            editUser.setUser(user);
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.showAndWait();

        } else {
            JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika do edycji", "info", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    @FXML
    public void passwdReset() throws ClassNotFoundException, SQLException {
        try {
            Connection conn = base.baseConnection();
            Statement stmnt = conn.createStatement();
            long pesel = tableUsers.getSelectionModel().getSelectedItem().getPesel();
            String query = "select dane_logowania.ID_konta from dane_logowania,uzytkownicy WHERE dane_logowania.ID_konta=uzytkownicy.ID_uzytkownika AND uzytkownicy.pesel=" + pesel;
            ResultSet rs = conn.createStatement().executeQuery(query);
            rs.first();
            String passwd = JOptionPane.showInputDialog("Podaj tymczasowe hasło: ");
            int id = rs.getInt(1);
//            System.out.println(rs.getString(1));
            if (passwd != null) {
                query = "UPDATE `dane_logowania` SET `Haslo` = '" + passwd
                        + "', `Pass_Counter` = '1' WHERE `dane_logowania`.`ID_konta` ='" + id + "';";

                PreparedStatement ps = conn.prepareStatement(query);
                int editStudent = stmnt.executeUpdate(query);
                editStudent = ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Udało się przypisać nowe tymczasowe hasło użytkownikowi:" + passwd, "Powodzenie", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika", "info", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    public void deleteUser() throws IOException, ClassNotFoundException {
        try {

            Connection conn = base.baseConnection();
            Statement stmnt = conn.createStatement();
            long pesel = tableUsers.getSelectionModel().getSelectedItem().getPesel();
//            int selectedIndex = tableUsers.getSelectionModel().getSelectedIndex();
            String querry = "select uzytkownicy.ID_uzytkownika from uzytkownicy WHERE uzytkownicy.pesel=" + pesel;
            ResultSet rs = conn.createStatement().executeQuery(querry);
            rs.first();
            int id = rs.getInt(1);
            String query = "delete from uzytkownicy where uzytkownicy.ID_uzytkownika =" + id;
            PreparedStatement ps = conn.prepareStatement(query);
            int deleteUser = stmnt.executeUpdate(query);
            deleteUser = ps.executeUpdate();
            query = "delete from dane_logowania where dane_logowania.ID_konta =" + id;
            ps = conn.prepareStatement(query);
            deleteUser = stmnt.executeUpdate(query);
            deleteUser = ps.executeUpdate();

            this.loadUsers();
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika do usunięcia", "info", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
