/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Pc
 */
public class StudentController  {

    Baza_Conn baza = new Baza_Conn();
    private FXMLDocumentController mainController;
    @FXML
    private TextField WpisywanieProdukt;
    @FXML
    private TableView<przedmioty> tablePrzedmioty;
    @FXML
    private TableColumn<przedmioty, String> tabNazwa;
    @FXML
    private TableColumn<przedmioty, String> tabRodzaj;
    @FXML
    private TableColumn<przedmioty, Integer> tabIlosc;
    @FXML
    private TableColumn<przedmioty, String> tabStatus;

    private ObservableList<przedmioty> listaPrzedmioty;
    
     @FXML
    public void wczytajPrzedmioty() throws ClassNotFoundException, SQLException{
        try{
       
       Connection conn = baza.baza_polacz();
       listaPrzedmioty = FXCollections.observableArrayList();
       ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM przedmioty");
       while(rs.next()){
           listaPrzedmioty.add(new przedmioty(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
       }
     }catch(SQLException ex){
            System.out.println("Error"+ex);
    }
        
        tabNazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        tabRodzaj.setCellValueFactory(new PropertyValueFactory<>("Rodzaj"));
        tabIlosc.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
        tabStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        
        tablePrzedmioty.setItems(null);
        tablePrzedmioty.setItems(listaPrzedmioty);
    }
    @FXML
    public void backMenu() {
        mainController.loadMenuScreen();
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }
    @FXML
     public void exit() {
        Platform.exit();
    }

}
