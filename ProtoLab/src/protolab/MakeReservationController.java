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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Wojtek
 */
public class MakeReservationController implements Initializable {

    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    ObservableList<String> options;
    @FXML
    private ComboBox<String> boxName;
    @FXML
    private ComboBox<String> boxSurname;
    @FXML
    private ComboBox<String> boxItem;
    @FXML
    private ComboBox<Integer> boxNumber;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    

    @FXML
    private void toStudentPanel(ActionEvent event) throws ClassNotFoundException, SQLException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StudentPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentPanelController studentPanelController = loader.getController();
        studentPanelController.setMainController(mainController);
        mainController.setScreen(pane);
    }
    
    public void loadNames(){
        options = FXCollections.observableArrayList(
        "Option 1",
        "Option 2",
        "Option 3"
    );
       boxItem = new ComboBox<String>();
       boxItem.getItems().addAll("sdasdasd","dasdasdasder");
       boxItem.setEditable(true);
    }
    public void setMainController(FXMLDocumentController mainController){
        this.mainController = mainController;
        loadNames();
    }
    
}
