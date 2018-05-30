package protolab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;



public class AddAdminController  {

    
    private FXMLDocumentController mainController;
/**
 * metoda powracajaca do listy studentów
 */
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
    private TextField login;
    @FXML
    private TextField haslo;
    @FXML
    private Button save;
    @FXML
    private Button back;
    BaseConnection base = new BaseConnection();
    public Users user;
    
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

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }
    /**
     * metoda zamykajaca aplikacje
     */
    
    public void addNewUser(){
         try{
        
        Connection conn = base.baseConnection();
       
        
         PreparedStatement prstm = conn.prepareStatement("INSERT INTO uzytkownicy(imie, nazwisko, numerTel, email, pesel, ID_uprawnienia) VALUES (?, ?, ?, ?, ?, ?)");
        /**kolejno przekazywane parametry do zapytania */
        prstm.setString(1, name.getText());
        prstm.setString(2, lastName.getText());
        prstm.setInt(3, Integer.parseInt(phone.getText()));
        prstm.setString(4, email.getText());
        prstm.setInt(5, Integer.parseInt(pesel.getText()));
        prstm.setInt(6, 2);
        prstm.executeUpdate();
        prstm.close();

        } 
        catch(Exception ex){
              System.out.println(ex.getMessage());
        }

    }
    
     public void exit() {
        Platform.exit();
    }

}
