package protolab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class BossPanelController {

    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    @FXML
    private TextField WpisywanieProdukt;
    @FXML
    private TableView<Items> tablePrzedmioty;
    @FXML
    private TableColumn<Items, String> tabName;
    @FXML
    private TableColumn<Items, String> tabType;
    @FXML
    private TableColumn<Items, Integer> tabQuantity;
    @FXML
    private Label lblWelcome;
//    @FXML
//    private TableColumn<Items, String> tabStatus;

    private ObservableList<Items> itemList;

    @FXML
    public void loadItems() throws ClassNotFoundException, SQLException {

        try {
            Connection conn = base.baseConnection();
            itemList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(""
                    + "SELECT przedmioty.ID_przedmiotu,przedmioty.Nazwa, rodzaj_przedmiotu.nazwa_typu, przedmioty.Ilosc "
                    + "FROM przedmioty, rodzaj_przedmiotu "
                    + "WHERE rodzaj_przedmiotu.id_rodzaj=przedmioty.id_rodzaj");
            while (rs.next()) {
                itemList.add(new Items(rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }

        tabName.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        tabType.setCellValueFactory(new PropertyValueFactory<>("Rodzaj"));
        tabQuantity.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
//        tabStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        tablePrzedmioty.setItems(null);
        tablePrzedmioty.setItems(itemList);
        lblWelcome.setText("Witamy " + SessionService.getUsername());
    }

    /**
     * metoda otwierająca okno listy studentów
     *
     * @throws IOException
     */
    @FXML
    public void loadStudents() throws IOException, ClassNotFoundException, SQLException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ListStudents.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListStudentsController studentsController = loader.getController();
        studentsController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    /**
     * metoda otwierajaca okno listy rezerwacji
     *
     * @throws IOException
     */
    @FXML
    public void listReservations() throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ListReservation.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListReservationController listReservationController = loader.getController();
        listReservationController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    /**
     * metoda odpowiadajaca za wylogowanie
     */
    @FXML
    public void backMenu() {
        SessionService.resetSession();
        mainController.loadMenuScreen();
    }

    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadItems();

    }

    @FXML
    public void changeMyPasswd() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ChangePasswd.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChangePasswdController changePass = loader.getController();
        changePass.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void searchItem2() throws ClassNotFoundException {
        try {

            String szuk = WpisywanieProdukt.getText().toLowerCase();
            Connection conn = base.baseConnection();
            itemList = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(""
                    + "SELECT przedmioty.ID_przedmiotu,przedmioty.Nazwa, rodzaj_przedmiotu.nazwa_typu, przedmioty.Ilosc "
                    + "FROM przedmioty, rodzaj_przedmiotu "
                    + "WHERE rodzaj_przedmiotu.id_rodzaj=przedmioty.id_rodzaj");
            while (rs.next()) {
                if (rs.getString(2).toLowerCase().startsWith(szuk)) {
                    itemList.add(new Items(rs.getString(2), rs.getString(3), rs.getInt(4)));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }

        tabName.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        tabType.setCellValueFactory(new PropertyValueFactory<>("Rodzaj"));
        tabQuantity.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
//        tabStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        tablePrzedmioty.setItems(null);
        tablePrzedmioty.setItems(itemList);
    }

    /**
     * metoda zamykajaca aplikacje
     */
    @FXML
    public void exit() {
        Platform.exit();
    }

}
