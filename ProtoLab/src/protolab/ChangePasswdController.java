/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik Maga
 */
public class ChangePasswdController {

    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;

    @FXML
    private PasswordField passwdField;
    @FXML
    private PasswordField passwdField2;
    @FXML
    private PasswordField oldPasswdField;

    // Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)
    @FXML
    public void checkPasswd() throws ClassNotFoundException, SQLException, IOException {
        Connection conn = base.baseConnection();
        Statement stmnt = conn.createStatement();
         ResultSet rs = conn.createStatement().executeQuery("SELECT dane_logowania.Haslo FROM dane_logowania WHERE dane_logowania.ID_konta="+SessionService.getUserID());
         rs.first();
        String oldPasswd =oldPasswdField.getText();
        String newPasswd = passwdField.getText();
        String newPasswdConfirm = passwdField2.getText();
        String errorMsg = "<html><body width=300><h2>Błąd</h2>";
        boolean correctSecondPasswd = true;
        boolean correctCase = true;
        boolean correctNumber = true;
        boolean correctLength = true;
        boolean correctPreviousPass= true;
        boolean correctOldPasswd=true;
        boolean correctNewOldPasswd=true;
        if (newPasswdConfirm.equals(newPasswd)) {
            correctSecondPasswd = true;
            if(!oldPasswd.equals(newPasswd)){
                correctNewOldPasswd=true;
            }else{
                correctNewOldPasswd=false;
                errorMsg += "<p>*Stare hasło i nowe hasło są takie same";
            }
            if(oldPasswd.equals(rs.getString(1))){
                correctOldPasswd=true;
            }else{
                correctOldPasswd=false;
                errorMsg += "<p>*Wprowadzone stare hasło nie jest poprawne";
            }
            if(!newPasswd.equals(rs.getString(1))){
                correctPreviousPass=true;
            }else{
                correctPreviousPass=false;
                errorMsg += "<p>*Nowe hasło nie moze być takie same jak stare hasło";
            }
            if (!newPasswd.equals(newPasswd.toLowerCase()) && !newPasswd.equals(newPasswd.toUpperCase())) {
                correctCase = true;
            } else {
                correctCase = false;
                errorMsg += "<p>*Hasło powinno zawierać duże i małe litery!";
            }
            if (newPasswd.matches(".*\\d+.*")) {
                correctNumber = true;
            } else {
                correctNumber = false;
                errorMsg += "<p>*Hasło powinno zawierać przynajmniej jedną cyfrę!";
            }
            if (newPasswd.length() > 8) {
                correctLength = true;
            } else {
                correctLength = false;
                errorMsg += "<p>*Hasło jest za krótkie- minimum 8 znaków!";
            }
        } else {
            passwdField.clear();
            passwdField2.clear();
            correctSecondPasswd = false;
            errorMsg += "<p>*Podane hasła nie są identyczne!";
        }
        if (correctCase && correctLength && correctNumber && correctSecondPasswd && correctPreviousPass && correctOldPasswd && correctNewOldPasswd) {
            setPasswd(newPasswd);
            setWindow(SessionService.getUserRights());
        } else {
            passwdField.clear();
            passwdField2.clear();
            JOptionPane.showMessageDialog(null, errorMsg, "Password", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setPasswd(String passwd) throws ClassNotFoundException, SQLException {
        Connection conn = base.baseConnection();
        Statement stmnt = conn.createStatement();      
        String query = "UPDATE `dane_logowania` SET `Haslo` ='" + passwd + "',"
                + " `Pass_Counter` = '0' "
                + "WHERE `dane_logowania`.`ID_konta` =" + SessionService.getUserID() + ";";
        PreparedStatement ps = conn.prepareStatement(query);
        int editStudent = stmnt.executeUpdate(query);
        editStudent = ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Poprawnie zaktualizowano hasło", "Password", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void cancel() throws IOException, ClassNotFoundException, SQLException {
        int idRights=SessionService.getUserRights();
        try{
        setWindow(idRights);
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }
    public void setWindow(int idRights) throws IOException, ClassNotFoundException, SQLException {
        if (idRights == 1) {

            signInStudent();
        }
        if (idRights == 2) {

            signInAdmin();
        }
        if (idRights == 3) {
            signInBoss();
        }

    }

    private void signInAdmin() throws IOException, ClassNotFoundException, SQLException {
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

    private void signInBoss() throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("BossPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BossPanelController szefController = loader.getController();
        szefController.setMainController(mainController);
        mainController.setScreen(pane);

    }

    public void signInStudent() throws IOException, ClassNotFoundException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StudentPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StudentPanelController studentController = loader.getController();
        try {
            studentController.setMainController(mainController);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainController.setScreen(pane);

    }
}
