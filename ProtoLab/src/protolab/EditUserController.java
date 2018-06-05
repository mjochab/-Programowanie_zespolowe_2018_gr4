/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import protolab.exceptions.EmailInvalidFormatException;
import protolab.exceptions.LoginAlreadyExistsException;
import protolab.exceptions.NameInvalidValueException;
import protolab.exceptions.PeselLengthException;
import protolab.exceptions.PeselSumControlNumberException;
import protolab.exceptions.RightsNotSelectedExeception;

public class EditUserController implements Initializable {

    private FXMLDocumentController mainController;
    ObservableList<String> rights;
    @FXML
    private TextField name;
    @FXML
    private TextField lastName;
    @FXML
    private TextField pesel;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private Button save;
    @FXML
    private Button back;
    @FXML
    private ComboBox<String> boxRights = new ComboBox<>();
    @FXML
    private TextField uprawnienia;

    BaseConnection base = new BaseConnection();

    public Users user;

    String errorMsg = "<html><body width=300><h2>Błąd</h2>";
    @FXML
    private TextField Login_User;

    public void loadRights() throws SQLException, ClassNotFoundException {
        Connection conn = base.baseConnection();
        rights = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT uprawnienia.rodzajUprawnienia FROM uprawnienia");

        while (rs.next()) {
            rights.add(rs.getString(1));
        }

        boxRights.setItems(rights);
    }

    public void loadLoginUser() throws ClassNotFoundException {

        try {
            String query = "SELECT uzytkownicy.pesel, dane_logowania.Login from uzytkownicy,dane_logowania where uzytkownicy.ID_uzytkownika=dane_logowania.ID_konta and uzytkownicy.pesel=" + user.getPesel();
            Connection conn = base.baseConnection();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                Login_User.setText(rs.getString(2));
            }

        } catch (SQLException ex) {

        }

    }

    public void setMainController(FXMLDocumentController mainController) throws SQLException, ClassNotFoundException {
        this.mainController = mainController;
        loadRights();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pesel.setDisable(true);
        Login_User.setDisable(true);
        uprawnienia.setDisable(true);

    }

    @FXML
    private void saveChanges(ActionEvent event) throws ClassNotFoundException, SQLException, NameInvalidValueException, EmailInvalidFormatException, RightsNotSelectedExeception {
        if (checkName() && checkLastName() && checkNumberPhone() && checkEmail() && checkIsSelectedRights() ) {

            try {

                Connection conn = base.baseConnection();

                int id_uprawanienia = getIdRights(boxRights.getSelectionModel().getSelectedItem());

                String query = "UPDATE uzytkownicy set imie = '" + name.getText()
                        + "', nazwisko = '" + lastName.getText()
                        + "', numerTel = '" + phone.getText()
                        + "', email = '" + email.getText()
                        + "', pesel = '" + pesel.getText()
                        + "', ID_uprawnienia= " + id_uprawanienia
                        + " WHERE pesel = '" + this.user.getPesel() + "';";

                PreparedStatement ps = conn.prepareStatement(query);
                Statement stmnt = conn.createStatement();
                int editStudent = stmnt.executeUpdate(query);
                editStudent = ps.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            if (!checkName()) {
                name.clear();
            }
            if (!checkLastName()) {
                lastName.clear();
            }
            if (!checkNumberPhone()) {
                phone.clear();
            }
            if (!checkEmail()) {
                email.clear();
            }
            JOptionPane.showMessageDialog(null, errorMsg, "", JOptionPane.ERROR_MESSAGE);

        }
    }

    @FXML
    public void backMenu() throws ClassNotFoundException, SQLException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ListUsers.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListUsersController userController = loader.getController();
        userController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    public void setUser(Users user) throws ClassNotFoundException {
        this.user = user;
        this.setData();
    }

    public void setData() throws ClassNotFoundException {

        this.name.setText(user.getName());
        this.phone.setText(String.valueOf(user.getTelNumber()));
        this.email.setText(user.getEmail());
        this.lastName.setText(user.getSurname());
        this.pesel.setText(String.valueOf(user.getPesel()));
        this.uprawnienia.setText(user.getRank());
        loadLoginUser();

    }

    public boolean checkName() throws NameInvalidValueException {
        try {
            if (name.getText().matches(".*\\d+.*")) {
                throw new NameInvalidValueException("Liczby w Imieniu");

            }
        } catch (NameInvalidValueException nameE) {
            errorMsg += "<p>*Imię nie może posiadać cyfr";
            return false;
        } catch (NullPointerException nullE) {
            errorMsg += "<p>*Pole Imienia nie może zostać puste";
            return false;
        }
        return true;

    }

    public boolean checkLastName() throws NameInvalidValueException {
        try {
            if (lastName.getText().matches(".*\\d+.*")) {
                throw new NameInvalidValueException("Liczby w Nazwisku");

            }
        } catch (NameInvalidValueException nameE) {
            errorMsg += "<p>*Nazwisko nie może posiadać cyfr";
            return false;
        } catch (NullPointerException nullE) {
            errorMsg += "<p>*Pole Nazwiska nie może zostać puste";
            return false;
        }
        return true;

    }

    public boolean checkNumberPhone() {
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phone.getText());
        } catch (NumberFormatException num) {
            System.out.println(num);
            errorMsg += "<p>*Niepoprawny numer telefonu";
            return false;

            /////
        }
        return true;

    }

    private boolean checkEmail() throws EmailInvalidFormatException {

        try {
            if (!email.getText().matches("^.+@.+\\..+$")) {
                throw new EmailInvalidFormatException("niepoprawny mail");
            }
        } catch (EmailInvalidFormatException ee) {
            errorMsg += "<p>*Niepoprawny adres email";
            return false;
        }
        return true;
    }

    private boolean checkIsSelectedRights() throws RightsNotSelectedExeception {
        try {
            if (boxRights.getSelectionModel().getSelectedItem().isEmpty()) {
                throw new RightsNotSelectedExeception("nie wybrano uprawnień użytkownika");
            }
        } catch (RightsNotSelectedExeception re) {
            errorMsg += "<p>*Nie wybrano uprawnień dla użytkownika";
            return false;
        }
        
        return true;
    }

    public int getIdRights(String nameRights) throws ClassNotFoundException, SQLException {
        Connection conn = base.baseConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT uprawnienia.ID_uprawnienia FROM uprawnienia WHERE uprawnienia.rodzajUprawnienia='" + nameRights + "'");
        rs.first();
        return rs.getInt(1);
    }

}
