package protolab;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import protolabpdf.*;

import com.itextpdf.text.*;
import java.awt.Desktop;
import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import protolab.exceptions.DatePickerValidDateValueException;
import protolab.exceptions.ReservationDatePickerIsEmptyException;

/**
 * @ -20,16 +29,62 @@ import javafx.scene.layout.Pane;
 * @author Pc
 */
public class ListMyReservationController {

//    public ListReservationController(int idItem) {
//        
//    }
    BaseConnection base = new BaseConnection();

    private FXMLDocumentController mainController;
    @FXML
    private Button delStudent;
    @FXML
    private TableView<Reservations> tableReservations;
    @FXML
    private TableColumn<Reservations, String> resName;
    @FXML
    private TableColumn<Reservations, String> resSurname;
    @FXML
    private TableColumn<Reservations, String> resItem;
    @FXML
    private TableColumn<Reservations, Integer> resNumber;
    @FXML
    private TableColumn<Reservations, String> resFrom;
    @FXML
    private TableColumn<Reservations, String> resTo;

    private ObservableList<Reservations> ResList;
    @FXML
    private TableColumn<Reservations, Integer> resID;
    @FXML
    private DatePicker dateCurrentPicker;
    @FXML
    private Button BTgeneratePDF;
    @FXML
    private Button BTgeneratePdfStudent;

    

    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadReservations();
        setDatePicker();
        disableButtons(SessionService.getUserRights());
    }

    @FXML
    public void loadReservations() throws ClassNotFoundException, SQLException {
        try {
            String querry = "SELECT uzytkownicy.imie, uzytkownicy.nazwisko, przedmioty.Nazwa, rezerwacje.ilosc, rezerwacje.od_kiedy, rezerwacje.do_kiedy, rezerwacje.idRezerwacji "
                    + "FROM rezerwacje, uzytkownicy, przedmioty "
                    + "WHERE uzytkownicy.ID_uzytkownika = rezerwacje.ID_uzytkownika and przedmioty.ID_przedmiotu = rezerwacje.ID_przedmiotu"
                    + "and rezerwacje.ID_uzytkownika="+SessionService.getUserID();
            Connection conn = base.baseConnection();
            ResList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(querry);
            while (rs.next()) {
                ResList.add(new Reservations(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7)));
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
        resName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        resSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        resItem.setCellValueFactory(new PropertyValueFactory<>("Item"));
        resNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
        resFrom.setCellValueFactory(new PropertyValueFactory<>("From"));
        resTo.setCellValueFactory(new PropertyValueFactory<>("To"));
        resID.setCellValueFactory(new PropertyValueFactory<>("IdRez"));

        tableReservations.setItems(null);
        tableReservations.setItems(ResList);

    }

    @FXML
    public void Back() throws ClassNotFoundException, SQLException, IOException {
        getWindow(SessionService.getUserRights());
    }

    public void getWindow(int idRights) throws IOException, ClassNotFoundException, SQLException {
        if (idRights == 1) {
            BackStudentReservation();

        }
        if (idRights == 2) {

            BackAdminReservation();
        }
        if (idRights == 3) {

            BackBossReservation();

        }
    }

    public void BackAdminReservation() throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AdminPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminPanelController adminController = loader.getController();
        adminController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    public void BackBossReservation() throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("BossPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BossPanelController bossController = loader.getController();
        bossController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    public void BackStudentReservation() throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StudentPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentPanelController studentController = loader.getController();
        studentController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void deleteReservation() throws ClassNotFoundException, SQLException {

        Connection conn = base.baseConnection();
        Statement stmnt = conn.createStatement();
        int idRes = tableReservations.getSelectionModel().getSelectedItem().getIdRez();
        int itemNumber = 0;
        int itemNumberRes = tableReservations.getSelectionModel().getSelectedItem().getNumber();
        ResultSet rs =conn.createStatement().executeQuery("SELECT rezerwacje.rezerwacja_counter FROM rezerwacje WHERE rezerwacje.idRezerwacji ="+idRes);
        rs.first();
        ResultSet rs1 = conn.createStatement().executeQuery("SELECT przedmioty.Ilosc FROM przedmioty WHERE Nazwa = '" + tableReservations.getSelectionModel().getSelectedItem().getItem() + "'");
        while (rs1.next()) {
            itemNumber = rs1.getInt(1);
        }
        String query = "delete from rezerwacje where idRezerwacji ='" + idRes + "';";
        String query2 = "UPDATE przedmioty SET Ilosc = " + (itemNumber + itemNumberRes) + " WHERE Nazwa = '" + tableReservations.getSelectionModel().getSelectedItem().getItem() + "'";

        PreparedStatement ps = conn.prepareStatement(query);
        int deleteReservation = stmnt.executeUpdate(query);
        deleteReservation = ps.executeUpdate();

        if(rs.getInt(1) == 1){
            
        
        PreparedStatement ps1 = conn.prepareStatement(query2);
        int updateItems = stmnt.executeUpdate(query2);
        updateItems = ps1.executeUpdate();
        }
        this.loadReservations();

    }

    @FXML
    public void showCurrentReservations() throws ClassNotFoundException, SQLException, ReservationDatePickerIsEmptyException {

        Connection conn = base.baseConnection();
        if (checkDatePickerIsDate()) {
            if (checkDatePickerIsBetween(dateCurrentPicker.getValue())) {

                try {

                    ResList = FXCollections.observableArrayList();
                    LocalDate datePicker = dateCurrentPicker.getValue();
                    ResultSet rs = conn.createStatement().executeQuery("SELECT uzytkownicy.imie, uzytkownicy.nazwisko, przedmioty.Nazwa, rezerwacje.ilosc, rezerwacje.od_kiedy, rezerwacje.do_kiedy, rezerwacje.idRezerwacji "
                            + "FROM rezerwacje, uzytkownicy, przedmioty "
                            + "WHERE uzytkownicy.ID_uzytkownika = rezerwacje.ID_uzytkownika "
                            + "and przedmioty.ID_przedmiotu = rezerwacje.ID_przedmiotu and rezerwacje.ID_uzytkownika="+SessionService.getUserID()
                            + " AND rezerwacje.od_kiedy <=  '" + Date.valueOf(dateCurrentPicker.getValue()) + "' AND rezerwacje.do_kiedy >= '" + Date.valueOf(dateCurrentPicker.getValue()) + "'");
                    while (rs.next()) {
                        ResList.add(new Reservations(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7)));
                    }

                } catch (SQLException ex) {
                    System.out.println("Error" + ex);
                }
            }
        }
        resName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        resSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        resItem.setCellValueFactory(new PropertyValueFactory<>("Item"));
        resNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
        resFrom.setCellValueFactory(new PropertyValueFactory<>("From"));
        resTo.setCellValueFactory(new PropertyValueFactory<>("To"));
        resID.setCellValueFactory(new PropertyValueFactory<>("IdRez"));

        tableReservations.setItems(null);
        tableReservations.setItems(ResList);

    }

    public void setDatePicker() {
        dateCurrentPicker.setValue(LocalDate.now());
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
        dateCurrentPicker.setDayCellFactory(dayCellFactory);
    }

    private boolean checkDatePickerIsDate() throws ReservationDatePickerIsEmptyException, ClassNotFoundException, SQLException {
        try {
            if (dateCurrentPicker.getValue() == null) {
                throw new ReservationDatePickerIsEmptyException("Brak wartości w DatePicker.");
            }
        } catch (ReservationDatePickerIsEmptyException ee) {
            loadReservations();
            JOptionPane.showMessageDialog(null, "Brak wartości w DatePickera", "", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;

    }

    private boolean checkDatePickerIsBetween(LocalDate data) throws ClassNotFoundException, SQLException {
        LocalDate dateNowPick = LocalDate.now();
        try {
            if (dateNowPick.compareTo(data) > 0) {
                throw new DatePickerValidDateValueException("Błąd daty.");
            }
        } catch (DatePickerValidDateValueException ex) {
            loadReservations();
            JOptionPane.showMessageDialog(null, "Brak wartości w DatePickera", "", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void disableButtons(int idRights) {
        if (idRights != 2) {
            BTgeneratePDF.setDisable(true);
            BTgeneratePdfStudent.setDisable(true);
        }
    }

    public void checkRightOnReservation() throws ClassNotFoundException, SQLException {
        int idRes = 0;
        boolean canDeleteRaservation = true;
        try {
            idRes = tableReservations.getSelectionModel().getSelectedItem().getIdRez();
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Nie wybrano rezerwacji do usunięcia", "info", JOptionPane.INFORMATION_MESSAGE);
        }
        Connection conn = base.baseConnection();
        String querry = ""
                + "SELECT rezerwacje.ID_uzytkownika, uzytkownicy.ID_uprawnienia "
                + "FROM uzytkownicy, rezerwacje "
                + "WHERE rezerwacje.idRezerwacji = " + idRes + " "
                + "AND rezerwacje.ID_uzytkownika = uzytkownicy.ID_uzytkownika";
        ResultSet rs = conn.createStatement().executeQuery(querry);
        rs.first();
        // usuwanie własnych rezerwacji
        if (rs.getInt(1) == SessionService.getUserID()) {
            deleteReservation();
        } else {
            
            canDeleteRaservation = false;
        }
        // usuwanie rezerwacji studenta przez admina lub szefa
        if (rs.getInt(2) == 1 && SessionService.getUserRights() != 1) {
            deleteReservation();
        } else {
            canDeleteRaservation = false;
        }
        //usuwanie rezerwacji szefa przez admina
        if (rs.getInt(2) == 3 && SessionService.getUserRights() == 2) {
            deleteReservation();
        }
        if (canDeleteRaservation == false) {
            JOptionPane.showMessageDialog(null, "Nie można usunąć danej rezerwacji. Zbyt małe uprawnienia.", "info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void exit() {
        Platform.exit();
    }
}
