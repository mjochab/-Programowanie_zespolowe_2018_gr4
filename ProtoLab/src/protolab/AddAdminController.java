package protolab;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;



public class AddAdminController  {

    private FXMLDocumentController mainController;
/**
 * metoda powracajaca do listy studentów
 */
    @FXML
    public void backMenu() {
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
    /**
     * metoda zamykajaca aplikacje
     */
     public void exit() {
        Platform.exit();
    }

}
