/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Wojtek
 */
public class MakeReservationController implements Initializable {

    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    ObservableList<String> names;
    ObservableList<String> surnames;
    ObservableList<String> pesels;
    ObservableList<String> items;
    ObservableList<String> amount;
    @FXML
    private ComboBox<String> boxItem = new ComboBox<>();
    
    @FXML
    private  DatePicker dateFrom;
    @FXML
    private  DatePicker dateTo;
    @FXML
    private TextField TextNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadItems();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MakeReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MakeReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        checkDate();
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

    public void loadItems() throws ClassNotFoundException, SQLException {
        String item;
        Connection conn = base.baseConnection();
        items = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT Nazwa FROM przedmioty where Ilosc > 0");

        while (rs.next()) {
            items.add(rs.getString(1));
        }

        boxItem.setItems(items);
        

    }

    public void checkDate() {
        dateFrom.setValue(LocalDate.now());
        
        
        final Callback<DatePicker, DateCell> dayCellfack
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(
                                dateFrom.getValue().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                       
                        long p = ChronoUnit.DAYS.between(
                                dateFrom.getValue(), item
                        );
                        
                         if (item.isAfter(dateFrom.getValue().plusWeeks(3))){
                             setDisable(true);
                             setStyle("-fx-background-color: #f2b96f");
                             
                         }
                    }
                };
            }
        };
            final Callback<DatePicker, DateCell> dayCellfuck
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(
                                LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        
                    }
                };
            }
        };
        dateFrom.setDayCellFactory(dayCellfuck);
        dateTo.setDayCellFactory(dayCellfack);
        dateTo.setValue(dateFrom.getValue().plusDays(1));

    }
    @FXML
    public void makeReservation() throws ClassNotFoundException, SQLException{
        int idItem = 0;
        String itemName = boxItem.getValue();
        int maxItemNumber =0;
        
        Connection conn = base.baseConnection();
        ResultSet rs1 = conn.createStatement().executeQuery("SELECT przedmioty.ID_przedmiotu FROM przedmioty WHERE Nazwa = '"+itemName+"'");
       
        while(rs1.next()){
            idItem = rs1.getInt(1);
        }
        ResultSet rs2 = conn.createStatement().executeQuery("SELECT przedmioty.ilosc FROM przedmioty WHERE Nazwa = '"+itemName+"'");
        while(rs2.next()){
            maxItemNumber = rs2.getInt(1);
        }
        if(Integer.valueOf(TextNumber.getText()) < 0 || Integer.valueOf(TextNumber.getText()) > maxItemNumber){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Niepoprawna ilość przedmiotów. Maksymalna ilość wybranych przedmiotów do wypożyczenia to: "+maxItemNumber+"");
                alert.showAndWait();
        }else{
        PreparedStatement prstm = conn.prepareStatement("INSERT INTO rezerwacje(ID_uzytkownika, ID_przedmiotu, od_kiedy, do_kiedy, ilosc) VALUES(?,?,?,?,?)");
        prstm.setInt(1, SessionService.getUserID());
        prstm.setInt(2, idItem);
        prstm.setDate(3, Date.valueOf(dateFrom.getValue()));
        prstm.setDate(4, Date.valueOf(dateTo.getValue()));
        prstm.setInt(5, Integer.valueOf(TextNumber.getText()));
        prstm.executeUpdate();
        prstm.close();
        }
        
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;

    }

}
