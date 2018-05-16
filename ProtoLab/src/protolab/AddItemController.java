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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
/**
 * FXML Controller class
 *
 * @author DominikMaga
 * 
 * Klasa dopowiadająca za dodawanie przedmiotów do bazy
 */
public class AddItemController  {
    /**połącznieie z bazą danych*/
    BaseConnection base = new BaseConnection();

   private FXMLDocumentController mainController;
    @FXML
    private TextField textName;
    @FXML
    private TextField textType;
    @FXML
    private TextField textNumber;
    
    /** metoda odpowiadająca za wywołanie i ustawienie tego okna jako okno aktywne*/
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }
    @FXML
    /** metoda odpowaiadając za powrót do poprzedniego okna */
    public void back() throws IOException {
        
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
        /** 
         * metoda odpowiadająca za dodawanie przedmiotu do bazy danych 
         * 
         */
    public void addItem() throws ClassNotFoundException, SQLException{
        try{
        /** połączenie z bazą */       
        Connection conn = base.baseConnection();
        /** przechwycenie z pola tekstowego o id=textName i przypisanie do String name*/
        String name = textName.getText();
        /** przechwycenie z pola tekstowego o id=textType i przypisanie do String type*/
        String type = textType.getText();
        /** przechwycenie z pola tekstowego wartośc numeryczną o id=textNumber i przypisanie do int number*/
        int number = Integer.valueOf(textNumber.getText());
        /** przypisanie do obiektu zapytania SQL do dodania przedmiotu do bazy */ 
        PreparedStatement prstm = conn.prepareStatement("INSERT INTO przedmioty(Nazwa, Rodzaj, Ilosc, Status) VALUES (?, ?, ?, ?)");
        /**kolejno przekazywane parametry do zapytania */
        prstm.setString(1, name);
        prstm.setString(2, type);
        prstm.setInt(3, number);
        prstm.setString(4, "w magazynie");
        prstm.executeUpdate();
        prstm.close();
                
        
        }catch(SQLException ex){
            System.out.println("Error"+ex);
        }
       
        textName.clear();
        textType.clear();
        textNumber.clear();
        }
    
    
}
