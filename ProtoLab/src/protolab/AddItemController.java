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
 * @author Pc
 */
public class AddItemController  {
    
    BaseConnection base = new BaseConnection();

   private FXMLDocumentController mainController;
    @FXML
    private TextField textName;
    @FXML
    private TextField textType;
    @FXML
    private TextField textNumber;
    
    
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }
    @FXML
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
    
    public void addItem() throws ClassNotFoundException, SQLException{
        try{
        Connection conn = base.baseConnection();
        
        String name = textName.getText();
        String type = textType.getText();
        int number = Integer.valueOf(textNumber.getText());
        
        PreparedStatement prstm = conn.prepareStatement("INSERT INTO przedmioty(Nazwa, Rodzaj, Ilosc, Status) VALUES (?, ?, ?, ?)");
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
