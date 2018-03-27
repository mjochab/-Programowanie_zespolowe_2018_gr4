package protolab;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class LogowanieController {

    private FXMLDocumentController mainController;

    @FXML
    public void Loguj() throws IOException {
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Admin.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminController adminController = loader.getController();
        adminController.setMainController(mainController);
        mainController.setScreen(pane);
    }
    
    

    @FXML
    public void LogujSzefa() throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Szef.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SzefController szefController = loader.getController();
        szefController.setMainController(mainController);
        mainController.setScreen(pane);

    }
    
    @FXML
    public void LogujStudenta() throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Student.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StudentController studentController = loader.getController();
        studentController.setMainController(mainController);
        mainController.setScreen(pane);

    }

    public void exit() {
        Platform.exit();
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

}
