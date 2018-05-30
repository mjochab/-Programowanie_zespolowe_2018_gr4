/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Wojtek
 */
public class MakeReservationController implements Initializable {

    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    ObservableList<String> names;
    ObservableList<String> surnames;
    ObservableList<String> pesels;
    ObservableList<String> items;
    ObservableList<String> amount;
    @FXML
    private ComboBox<String> boxItem = new ComboBox<>();;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField TextNumber;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadItems();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MakeReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MakeReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    @FXML
    private void toStudentPanel(ActionEvent event) throws ClassNotFoundException, SQLException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StudentPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentPanelController studentPanelController = loader.getController();
        studentPanelController.setMainController(mainController);
        mainController.setScreen(pane);
    }

   public void loadItems() throws ClassNotFoundException, SQLException{
       String item;
       Connection conn = base.baseConnection(); 
       items = FXCollections.observableArrayList();
       ResultSet rs = conn.createStatement().executeQuery("SELECT Nazwa FROM przedmioty where Ilosc > 0");
       
       while(rs.next()){
            items.add(rs.getString(1));
        }
       
       boxItem.setItems(items);
       
       
   }
    public void setMainController(FXMLDocumentController mainController){
        this.mainController = mainController;
        
    }
    
}
