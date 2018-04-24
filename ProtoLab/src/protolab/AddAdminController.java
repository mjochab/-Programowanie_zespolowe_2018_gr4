package protolab;

import javafx.application.Platform;
import javafx.fxml.FXML;



public class AddAdminController  {

    private FXMLDocumentController mainController;

    @FXML
    public void backMenu() {
        mainController.loadMenuScreen();
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }
     public void exit() {
        Platform.exit();
    }

}
