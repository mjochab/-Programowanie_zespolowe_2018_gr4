package protolab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import protolab.exceptions.TypeItemNotSelectedException;
import protolab.exceptions.ValueItemZeroOrMinusException;

/**
 * FXML Controller class
 *
 * @author DominikMaga
 *
 * Klasa dopowiadająca za dodawanie przedmiotów do bazy
 */
public class AddItemController {

    /**
     * połącznieie z bazą danych
     */
    BaseConnection base = new BaseConnection();
    ObservableList<String> types;
    private FXMLDocumentController mainController;
    @FXML
    private TextField textName;
    @FXML
    private ComboBox<String> boxType = new ComboBox<>();

    @FXML
    private TextField textNumber;
    String errorMsg = "<html><body width=300><h2>Błąd</h2>";

    /**
     * metoda odpowiadająca za wywołanie i ustawienie tego okna jako okno
     * aktywne
     */
    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadTypes();
    }

    @FXML
    /**
     * metoda odpowaiadając za powrót do poprzedniego okna
     */
    public void loadTypes() throws ClassNotFoundException, SQLException {
        Connection conn = base.baseConnection();
        types = FXCollections.observableArrayList();
        try{
        ResultSet rs = conn.createStatement().executeQuery("SELECT rodzaj_przedmiotu.nazwa_typu FROM rodzaj_przedmiotu");

        while (rs.next()) {
            types.add(rs.getString(1));
        }
        }catch(SQLException sqlE){
            JOptionPane.showMessageDialog(null, sqlE, "", JOptionPane.ERROR_MESSAGE);
        }
                

        boxType.setItems(types);
    }

    @FXML
    public void newType() throws SQLException, ClassNotFoundException {
        String typeName = JOptionPane.showInputDialog("Podaj nowy rodzaj przedmiotów: ");
        if(typeName!=null){
        if (checkTypeAlreadyExtist(typeName) || checkTypeAlreadyExtist(typeName.toLowerCase())) {
            JOptionPane.showMessageDialog(null, "Taki typ przedmiotu już istnieje", "", JOptionPane.ERROR_MESSAGE);
        } else {
            Connection conn = base.baseConnection();
            PreparedStatement prstm = conn.prepareStatement("INSERT INTO `rodzaj_przedmiotu` (`nazwa_typu`) VALUES (?);");

            prstm.setString(1, typeName);
            prstm.executeUpdate();
            prstm.close();
            loadTypes();
            JOptionPane.showMessageDialog(null, "Poprawnie dodano nowy typ przedmiotu", "", JOptionPane.INFORMATION_MESSAGE);
        }
        }
    }

    public void back() throws IOException, ClassNotFoundException, SQLException {

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

    /**
     * metoda odpowiadająca za dodawanie przedmiotu do bazy danych
     *
     */
    public void addItem() throws ClassNotFoundException, SQLException, TypeItemNotSelectedException, ValueItemZeroOrMinusException, IOException {
        try {
            /**
             * połączenie z bazą
             */
            Connection conn = base.baseConnection();
            /**
             * przechwycenie z pola tekstowego o id=textName i przypisanie do
             * String name
             */
            String name = textName.getText();

            if (checkValueHowManyItems() && checkIsSelectedRights()) {
                /**
                 * przypisanie do obiektu zapytania SQL do dodania przedmiotu do
                 * bazy
                 */
                int number = Integer.valueOf(textNumber.getText());
                if (checkItemAlreadyExtist()) {
                    PreparedStatement prstm = conn.prepareStatement("UPDATE przedmioty SET Ilosc = Ilosc + ? WHERE Nazwa = ?");
                    prstm.setInt(1, number);
                    prstm.setString(2, name);
                    prstm.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText("Dodano " + number + " przedmiotów: " + name + ".");
                    alert.showAndWait();
                    prstm.close();
                    back();
                } else {
                    PreparedStatement prstm = conn.prepareStatement("INSERT INTO przedmioty(Nazwa, id_rodzaj, Ilosc, Status) VALUES (?, ?, ?, ?)");
                    /**
                     * kolejno przekazywane parametry do zapytania
                     */
                    prstm.setString(1, name);
                    prstm.setInt(2, getIdType(boxType.getSelectionModel().getSelectedItem()));
                    prstm.setInt(3, number);
                    prstm.setString(4, "w magazynie");
                    prstm.executeUpdate();
                    prstm.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("");
                    alert.setHeaderText("Dodano " + number + " przedmiotów: " + name + ".");
                    alert.showAndWait();
                    prstm.close();
                    back();
                }

            } else {
                if (!checkValueHowManyItems()) {
                    textNumber.clear();
                }
                JOptionPane.showMessageDialog(null, errorMsg, "", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            System.out.println("Error" + ex);
        }
        
    }

    private boolean checkValueHowManyItems() {
        int number;
        try {
            number = Integer.parseInt(textNumber.getText());
            if (number <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException num) {
            System.out.println(num);
            errorMsg += "<p>*Niepoprawny wartość w polu ilość";
            return false;
        }
        return true;

    }

    private boolean checkItemAlreadyExtist() throws SQLException, ClassNotFoundException, ValueItemZeroOrMinusException {
        Connection conn = base.baseConnection();
        String name = textName.getText();
        int result = 0;
        ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(Nazwa) FROM przedmioty WHERE Nazwa= '" + name + "'");
        rs.first();
        do {
            result = rs.getInt(1);
        }while (rs.next());
        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIsSelectedRights() throws TypeItemNotSelectedException {
        try {
            if (boxType.getSelectionModel().getSelectedItem().isEmpty()) {
                throw new TypeItemNotSelectedException("nie wybrano typu przedmiotu");
            }
        } catch (TypeItemNotSelectedException re) {
            errorMsg += "<p>*Nie wybrano typu przedmiotu";
            return false;
        }
        return true;
    }

    private boolean checkTypeAlreadyExtist(String type) throws SQLException, ClassNotFoundException {
        Connection conn = base.baseConnection();
        int result = 0;
        ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(nazwa_typu) FROM rodzaj_przedmiotu WHERE rodzaj_przedmiotu.nazwa_typu='" + type + "'");
        rs.first();
        do {
            result = rs.getInt(1);
        }while (rs.next());
        if (result != 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getIdType(String nameType) throws ClassNotFoundException, SQLException {
        Connection conn = base.baseConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT rodzaj_przedmiotu.id_rodzaj FROM rodzaj_przedmiotu WHERE rodzaj_przedmiotu.nazwa_typu='" + nameType + "'");
        rs.first();
        return rs.getInt(1);
    }

}
