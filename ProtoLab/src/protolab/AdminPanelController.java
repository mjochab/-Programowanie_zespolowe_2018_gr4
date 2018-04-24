
package protolab;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class AdminPanelController {
    
    private FXMLDocumentController mainController;
    
    @FXML
    public void windowListUsers() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ListUsers.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListUsersController uzytkownicyController = loader.getController();
        uzytkownicyController.setMainController(mainController);
        mainController.setScreen(pane);
    }
    @FXML
    public void addToTheWarehouse() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AddItem.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddItemController sprzetController = loader.getController();
        sprzetController.setMainController(mainController);
        mainController.setScreen(pane);
    }
    
   @FXML
   public void backMenu(){
       mainController.loadMenuScreen();
   }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }
    public void exit() {
        Platform.exit();
    }
   
   
    
}
