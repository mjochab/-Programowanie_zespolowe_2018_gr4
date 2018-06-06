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
import javax.swing.JOptionPane;
import protolab.exceptions.ComboBoxNotSelectedExeception;
import protolab.exceptions.DatePickerValidDateValueException;
import protolab.exceptions.ItemValueOverMaxException;
import protolab.exceptions.NameInvalidValueException;
import protolab.exceptions.NullorMinusValueException;

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
    private DatePicker dateFrom;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TextField TextNumber;
    @FXML
    private Label labelFrom;
    @FXML
    private Label labelTo;
    String errorMsg = "<html><body width=300><h2>Błąd</h2>";

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

        final Callback<DatePicker, DateCell> dayCellFactory1
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

                        if (item.isAfter(dateFrom.getValue().plusWeeks(3))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #f2b96f");

                        }
                    }
                };
            }
        };
        final Callback<DatePicker, DateCell> dayCellFactory
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
        dateFrom.setDayCellFactory(dayCellFactory);
        dateTo.setDayCellFactory(dayCellFactory1);
        dateTo.setValue(dateFrom.getValue().plusDays(1));

    }

    private boolean checkNullOrMunisItemValue() throws NullorMinusValueException {
        try {
            if (Integer.valueOf(TextNumber.getText()) <= 0) {
                throw new NullorMinusValueException("Liczba ujemna lub zerowa.");
            }
        } catch (NullorMinusValueException ex) {
            errorMsg += "<p>*Ujemna lub zerowa wartość liczby przedmiotów.";
            return false;
        }
        return true;
    }

    private boolean checkNumberisHigherThanMax() throws ClassNotFoundException, SQLException, NameInvalidValueException {
        try {
            int maxItemNumber = 0;
            Connection conn = base.baseConnection();
            String itemName = boxItem.getValue();
            ResultSet rs = conn.createStatement().executeQuery("SELECT przedmioty.ilosc FROM przedmioty WHERE Nazwa = '" + itemName + "'");
            while (rs.next()) {
                maxItemNumber = rs.getInt(1);
            }
            if (Integer.valueOf(TextNumber.getText()) > maxItemNumber) {
                throw new ItemValueOverMaxException("Liczba przedmiotów wieksza niż dostępna w magazynie.");
            }
        } catch (ItemValueOverMaxException ex) {
            errorMsg += "<p>*Wybrana liczba przedmiotów większa niż dostępna w magazynie..";
            return false;
        } catch (SQLException sqle) {
            errorMsg += "<p>*." + sqle;
            return false;
        }
        return true;
    }

    private boolean checkDateFromIfLowerThanCurrent() throws ClassNotFoundException, ItemValueOverMaxException {
        try {
            LocalDate date = dateFrom.getValue();
            LocalDate dateNow = LocalDate.now();
            Connection conn = base.baseConnection();
            if (date.compareTo(dateNow) < 0) {
                throw new DatePickerValidDateValueException("Data mniejsza niż obecna");
            }
        } catch (DatePickerValidDateValueException ex) {
            errorMsg += "<p>*Data początkowa mniejsza od daty obecnej.";
            return false;
        }
        return true;
    }

    private boolean checkDateToIfLowerThanFrom() throws ClassNotFoundException, ItemValueOverMaxException {
        try {
            LocalDate date1 = dateFrom.getValue();
            LocalDate date2 = dateTo.getValue();
            Connection conn = base.baseConnection();
            if (date2.compareTo(date1) < 0) {
                throw new DatePickerValidDateValueException("Data końcowa mniejsza od daty początkowej.");
            }
        } catch (DatePickerValidDateValueException ex) {
            errorMsg += "<p>*Data końcowa mniejsza od daty początkowej.";
            return false;
        }
        return true;
    }

    private boolean checkDateBetweenMoreThanThreeWeeks() throws DatePickerValidDateValueException {
        try {
            LocalDate date1 = dateFrom.getValue();
            LocalDate date2 = dateTo.getValue();
            long daysBetween = DAYS.between(date1, date2);
            if (daysBetween > 21) {
                throw new DatePickerValidDateValueException("Zbyt duży okres rezerwacji.");
            }
        } catch (DatePickerValidDateValueException ex) {
            errorMsg += "<p>*Okres rezerwacji przekracza 3 tygodnie.";
            return false;
        }
        return true;
    }

    private boolean checkIsSelectedItem() throws ComboBoxNotSelectedExeception {
        try {
            if (boxItem.getSelectionModel().getSelectedItem().isEmpty()) {
                throw new ComboBoxNotSelectedExeception("nie wybrano przedmiotu.");
            }
        } catch (ComboBoxNotSelectedExeception re) {
            errorMsg += "<p>*Nie wybrano przdmiotu do zarezerwowania.";
            return false;
        } catch (NullPointerException exc) {
            errorMsg += "<p>*Nie wybrany przedmiot.";
            return false;
        }
        return true;
    }

    public int getIdItem(String nameItem) throws ClassNotFoundException, SQLException {
        Connection conn = base.baseConnection();
        int idItem = 0;
        ResultSet rs = conn.createStatement().executeQuery("SELECT przedmioty.ID_przedmiotu FROM przedmioty WHERE Nazwa = '" + nameItem + "'");
        while (rs.next()) {
            idItem = rs.getInt(1);
        }
        return idItem;
    }

    public int getMaxItemNumber(String itemName) throws ClassNotFoundException, SQLException {
        Connection conn = base.baseConnection();
        int maxItemNumber = 0;
        ResultSet rs = conn.createStatement().executeQuery("SELECT przedmioty.ilosc FROM przedmioty WHERE Nazwa = '" + itemName + "'");
        while (rs.next()) {
            maxItemNumber = rs.getInt(1);
        }
        return maxItemNumber;
    }

    @FXML
    public void makeReservation() throws ClassNotFoundException, SQLException, ParseException, NullorMinusValueException, NameInvalidValueException, ItemValueOverMaxException, DatePickerValidDateValueException, ComboBoxNotSelectedExeception {
        Connection conn = base.baseConnection();
        if (checkDateFromIfLowerThanCurrent() && checkDateToIfLowerThanFrom() && checkDateBetweenMoreThanThreeWeeks() && checkIsSelectedItem()) {
            if (checkNumberOfItems() && checkNumberisHigherThanMax() && checkNullOrMunisItemValue()) {
                if (checkSumItemsInDay(getIdItem(boxItem.getValue()), Integer.valueOf(TextNumber.getText()))) {
                    try {
                        PreparedStatement prstm = conn.prepareStatement("INSERT INTO rezerwacje(ID_uzytkownika, ID_przedmiotu, od_kiedy, do_kiedy, ilosc, rezerwacja_counter) VALUES(?,?,?,?,?,?)");
                        prstm.setInt(1, SessionService.getUserID());
                        prstm.setInt(2, getIdItem(boxItem.getValue()));
                        prstm.setDate(3, Date.valueOf(dateFrom.getValue()));
                        prstm.setDate(4, Date.valueOf(dateTo.getValue()));
                        prstm.setInt(5, Integer.valueOf(TextNumber.getText()));
                        prstm.setBoolean(6, false);
                        prstm.executeUpdate();
                        prstm.close();
                    } catch (Exception sql) {
                        System.out.println(sql);
                        errorMsg += "<p>*" + sql;
                    }
                } else {
                    if (!checkNumberOfItems()) {
                        TextNumber.clear();
                    }
                    JOptionPane.showMessageDialog(null, errorMsg, "", JOptionPane.ERROR_MESSAGE);
                    errorMsg = "<html><body width=300><h2>Błąd</h2>";

                }
            } else {
                JOptionPane.showMessageDialog(null, errorMsg, "", JOptionPane.ERROR_MESSAGE);
                errorMsg = "<html><body width=300><h2>Błąd</h2>";
            }
        } else {
            JOptionPane.showMessageDialog(null, errorMsg, "", JOptionPane.ERROR_MESSAGE);
            errorMsg = "<html><body width=300><h2>Błąd</h2>";
        }
    }

    private boolean checkSumItemsInDay(int itemId, int countItems) throws SQLException, ClassNotFoundException {

        LocalDate dataOd = dateFrom.getValue();
        LocalDate dataDo = dateTo.getValue();
        int daysBetween = (int) DAYS.between(dataOd, dataDo);
        int[] tab = new int[daysBetween];
        boolean canRaservation = true;
        String unAbleDays = "<html><body width=400><h2>Nie można zarezerwować w dniach:</h2>";

        Connection conn = base.baseConnection();

        LocalDate dateTemp = dateFrom.getValue();
        for (int i = 0; i == daysBetween; i++) {
            dateTemp = dateTemp.plusDays(1);
            ResultSet rs = conn.createStatement().executeQuery("SELECT  sum(rezerwacje.ilosc),przedmioty.Ilosc "
                    + "FROM rezerwacje, przedmioty "
                    + "WHERE przedmioty.ID_przedmiotu = rezerwacje.ID_przedmiotu AND rezerwacje.ID_przedmiotu =" + itemId + ""
                    + " AND rezerwacje.od_kiedy <=  '" + Date.valueOf(dateTemp) + "' AND rezerwacje.do_kiedy >= '" + Date.valueOf(dateTemp) + "'");
            rs.first();

            if ((rs.getInt(2) - rs.getInt(1)) >= countItems) {
                if (canRaservation != false) {
                    canRaservation = true;
                }
            } else {
                unAbleDays += "<p>*Dnia: " + dateTemp + " jest tylko " + (rs.getInt(2) - rs.getInt(1) + "przedmiotów");
                canRaservation = false;
            }
        }
        JOptionPane.showMessageDialog(null, unAbleDays, "", JOptionPane.INFORMATION_MESSAGE);
        unAbleDays = "<html><body width=400><h2>Nie można zarezerwować w dniach:</h2>";
        return canRaservation;
    }

    public boolean checkNumberOfItems() {
        int numOfItems;
        try {
            numOfItems = Integer.parseInt(TextNumber.getText());
        } catch (NumberFormatException num) {
            errorMsg += "<p>*Niepoprawna wartość przy wyborze liczby przedmiotów.";
            return false;
        }
        return true;

    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;

    }

}
