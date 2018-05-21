/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Pc
 */
public class AddStudentController  {

   private FXMLDocumentController mainController;

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
     public void exit() {
        Platform.exit();
    }
}
