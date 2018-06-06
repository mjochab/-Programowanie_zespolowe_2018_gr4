package protolab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import protolab.SessionService;

public class LoginController {

    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    @FXML
    private TextField textLogin;
    @FXML
    private TextField textPass;

    @FXML
    private void signIn() throws IOException, ClassNotFoundException, SQLException {
        BaseConnection base = new BaseConnection();
        Connection conn = base.baseConnection();
        String login = textLogin.getText();
        String pass = textPass.getText();
        int id = 0;
        boolean newPasswdCounter = false;
        ResultSet rs = conn.createStatement().executeQuery("Select * from dane_logowania");
        rs.first();
        do {

            if (login.equals(rs.getString(2)) && pass.equals(rs.getString(3))) {
                if (rs.getBoolean(4) == true) {
                    newPasswdCounter = true;
                }
                id = rs.getInt(1);
                break;
            }

        } while (rs.next());
        if (id > 0 && newPasswdCounter == true) {
            setSession(id);
            setNewPasswd();
        } else {
            if (id > 0) {
                setSession(id);
                isReservationOn();
                isReservationOff();
                setWindow(SessionService.getUserRights());
            } else {
                JOptionPane.showMessageDialog(null, "nie ma takiego użytkownika o takim loginie", "Exception", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public void setSession(int id) throws ClassNotFoundException, SQLException {
        BaseConnection base = new BaseConnection();
        Connection conn = base.baseConnection();
        ResultSet rx = null;
        try {
            //                                            1                               2               3                       4               5
            rx = conn.createStatement().executeQuery("SELECT uzytkownicy.ID_uzytkownika,uzytkownicy.imie,uzytkownicy.nazwisko,uzytkownicy.pesel, uzytkownicy.ID_uprawnienia "
                    + "FROM uzytkownicy "
                    + "WHERE uzytkownicy.ID_uzytkownika=" + id);
            rx.first();
            System.out.println(rx.getInt(1) + " " + rx.getString(2) + " " + rx.getString(3) + " " + rx.getInt(5));
            SessionService.setUserID(rx.getInt(1));
            SessionService.setUsername(rx.getString(2));
            SessionService.setUserSurname(rx.getString(3));
            SessionService.setUserRights(rx.getInt(5));
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle);
            JOptionPane.showMessageDialog(null, "zły login/hasło lub nie ma takiego użytkownika", "SQLException", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.out.println(e);
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

    public void setNewPasswd() throws IOException, ClassNotFoundException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("NewPasswd.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NewPasswdController npc = loader.getController();
        npc.setMainController(mainController);
        mainController.setScreen(pane);

    }

    public void exit() {
        Platform.exit();
    }

    public void isReservationOn() throws ClassNotFoundException, SQLException {
        
        Connection conn = base.baseConnection();
        String querry = "SELECT rezerwacje.od_kiedy, rezerwacje.idRezerwacji, rezerwacje.ID_przedmiotu, przedmioty.Ilosc, rezerwacje.ilosc, rezerwacje.rezerwacja_counter "
                + "FROM `rezerwacje`, przedmioty "
                + "WHERE rezerwacje.do_kiedy <='" + Date.valueOf(LocalDate.now()) + "' "
                + "AND przedmioty.ID_przedmiotu = rezerwacje.ID_przedmiotu";
        ResultSet rs = conn.createStatement().executeQuery(querry);
        PreparedStatement prstm1;
        PreparedStatement prstm2;
        try{
        if(rs.isBeforeFirst()){
        rs.first();
        int itemNumberWarehouse;
        do {
            itemNumberWarehouse = rs.getInt(4) - rs.getInt(5);
            if (rs.getBoolean(6) == false) {
                prstm1 = conn.prepareStatement("UPDATE `przedmioty` SET `Ilosc` = '" + itemNumberWarehouse + "' WHERE przedmioty.ID_przedmiotu = " + rs.getInt(3) + " AND rezerwacje.rezerwacja_counter = 0");
                prstm1.executeUpdate();
                prstm1.close();
            }
            
                prstm2 = conn.prepareStatement("UPDATE rezerwacje SET rezerwacja_counter = 1 WHERE rezerwacje.idRezerwacji = " + rs.getInt(2) + "");
                prstm2.executeUpdate();
                prstm2.close();
            
            itemNumberWarehouse = 0;
        } while (rs.next());
        }
        }catch(NullPointerException ex){
            System.out.println(ex);
        }
    }

    public void isReservationOff() throws ClassNotFoundException, SQLException {
        
        Connection conn = base.baseConnection();
        try{
        String querry = "SELECT rezerwacje.do_kiedy, rezerwacje.idRezerwacji FROM rezerwacje WHERE rezerwacje.do_kiedy <='" + Date.valueOf(LocalDate.now().plusDays(1)) + "'";
        ResultSet rs = conn.createStatement().executeQuery(querry);
        PreparedStatement prstm;
        if(rs.isBeforeFirst()){
            
        
        while (rs.next()){
            prstm = conn.prepareStatement("DELETE FROM rezerwacje WHERE rezerwacje.idRezerwacji = " + rs.getInt(2) + "");
            prstm.executeUpdate();
            prstm.close();
        } 
        }
        }catch(NullPointerException ex){
            System.out.println(ex);
        }
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

}
