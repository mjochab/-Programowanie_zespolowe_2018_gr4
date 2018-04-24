package protolab;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Dominika
 */
public class AddBossController {

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
