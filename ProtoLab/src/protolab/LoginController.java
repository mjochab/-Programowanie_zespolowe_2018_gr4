package protolab;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class LoginController {

    private FXMLDocumentController mainController;

    @FXML
    public void signIn() throws IOException, ClassNotFoundException, SQLException {
        
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
    
    

    @FXML
    public void signInBoss() throws IOException, ClassNotFoundException, SQLException {

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
    
    @FXML
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
    
    

    public void exit() {
        Platform.exit();
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

}
