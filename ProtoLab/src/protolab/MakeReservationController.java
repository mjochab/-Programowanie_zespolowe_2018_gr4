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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML
    private Label labelFrom;
    @FXML
    private Label labelTo;

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
                                dateFrom.getValue())) {
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
    public void makeReservation() throws ClassNotFoundException, SQLException, ParseException{
        LocalDate date1 = dateFrom.getValue();
        LocalDate dateNow = LocalDate.now();
        LocalDate date2 = dateTo.getValue();
        long daysBetween = DAYS.between(date1, date2);
        
        int idItem = 0;
        String itemName = boxItem.getValue();
        int maxItemNumber =0;
        int howManyItems = Integer.valueOf(TextNumber.getText());
        
        
        Connection conn = base.baseConnection();
        
        ResultSet rs1 = conn.createStatement().executeQuery("SELECT przedmioty.ID_przedmiotu FROM przedmioty WHERE Nazwa = '"+itemName+"'");
        while(rs1.next()){
            idItem = rs1.getInt(1);
        }
        
        ResultSet rs2 = conn.createStatement().executeQuery("SELECT przedmioty.ilosc FROM przedmioty WHERE Nazwa = '"+itemName+"'");
        while(rs2.next()){
            maxItemNumber = rs2.getInt(1);
        }
        
        if(howManyItems <= 0 || howManyItems > maxItemNumber){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Niepoprawna ilość przedmiotów. Maksymalna ilość wybranych przedmiotów do wypożyczenia to: "+maxItemNumber+"");
                alert.showAndWait();
        }else if(date1.compareTo(dateNow) < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("BŁAD. Data początkowa mniejsza od daty obecnej.");
                alert.showAndWait();
        }else if(date2.compareTo(date1) < 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("BŁAD. Data końcowa mniejsza od daty początkowej.");
                alert.showAndWait();
        }else if(daysBetween > 21){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("BŁAD. Podana data końcowa przekracza limit 3 tygodni wypożyczenia.");
                alert.showAndWait();
        }else{
        PreparedStatement prstm = conn.prepareStatement("INSERT INTO rezerwacje(ID_uzytkownika, ID_przedmiotu, od_kiedy, do_kiedy, ilosc) VALUES(?,?,?,?,?)");
        prstm.setInt(1, SessionService.getUserID());
        prstm.setInt(2, idItem);
        prstm.setDate(3, Date.valueOf(dateFrom.getValue()));
        prstm.setDate(4, Date.valueOf(dateTo.getValue()));
        prstm.setInt(5, Integer.valueOf(TextNumber.getText()));
        prstm.executeUpdate();    
        
        PreparedStatement prstm1 = conn.prepareStatement("UPDATE przedmioty SET Ilosc = "+(maxItemNumber - howManyItems)+" WHERE Nazwa = '"+itemName+"'");
        prstm1.executeUpdate();
        prstm.close();
        prstm1.close();
        }
        
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;

    }

}
