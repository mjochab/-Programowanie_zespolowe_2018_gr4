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
 * @author Wojciech Kozyra
 */
public class StudentPanelController  {

    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    @FXML
    private TextField searchItem;
    @FXML
    private TableView<Items> tablePrzedmioty;
    @FXML
    private TableColumn<Items, String> tabName;
    @FXML
    private TableColumn<Items, String> tabType;
    @FXML
    private TableColumn<Items, Integer> tabQuantity;
    @FXML
    private TableColumn<Items, String> tabStatus;

    private ObservableList<Items> itemList;
    
     @FXML
    public void loadItems() throws ClassNotFoundException, SQLException{
        try{
       
       Connection conn = base.baseConnection();
       itemList = FXCollections.observableArrayList();
       ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM przedmioty");
       while(rs.next()){
           itemList.add(new Items(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
       }
     }catch(SQLException ex){
            System.out.println("Error"+ex);
    }
        
        tabName.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        tabType.setCellValueFactory(new PropertyValueFactory<>("Rodzaj"));
        tabQuantity.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
        tabStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        
        tablePrzedmioty.setItems(null);
        tablePrzedmioty.setItems(itemList);
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