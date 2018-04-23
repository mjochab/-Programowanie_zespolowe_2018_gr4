
package protolab;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class AdminController {
    
    private FXMLDocumentController mainController;
    
    @FXML
    public void oknouzyt() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Uzytkownicy.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UzytkownicyController uzytkownicyController = loader.getController();
        uzytkownicyController.setMainController(mainController);
        mainController.setScreen(pane);
    }
    @FXML
    public void DodajDoMagazynu() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Sprzet.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SprzetController sprzetController = loader.getController();
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
