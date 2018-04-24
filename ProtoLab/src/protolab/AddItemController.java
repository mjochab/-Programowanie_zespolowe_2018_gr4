/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
/**
 * FXML Controller class
 *
 * @author Pc
 */
public class AddItemController  {

   private FXMLDocumentController mainController;
    
    
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
    
    
}
