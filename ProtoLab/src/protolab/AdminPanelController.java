package protolab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import javax.xml.validation.Validator;

/**
 *
 * @author Winnicki Kamil
 */
public class AdminPanelController extends DatabaseTestingAbstractClass{

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
    @FXML
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
@FXML
public void makeDumpDB() throws ClassNotFoundException, SQLException, FileNotFoundException{
     AdminPanelController ad= new AdminPanelController();
     ad.createDb();
}
    /**
     * metoda uruchamiajaca okno listy użytkowników
     *
     * @throws IOException
     */

    @FXML
    public void windowListUsers() throws IOException, ClassNotFoundException, SQLException {

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
     * metoda otwierająca okno dodawania urządzeń
     *
     * @throws IOException
     */
    @FXML
    public void addToTheWarehouse() throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AddItem.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddItemController addItemController = loader.getController();
        addItemController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    /**
     * metoda powrotu do logowania
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
    public void bazaImport() throws JAXBException, ClassNotFoundException, SQLException, SAXException, IOException {
        System.out.println("import bazy ");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj plik kopią bazy danych");
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(ProtoLab.getpStage());

        JAXBContext jaxbContext = JAXBContext.newInstance(KopiaBazy.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("kopiaBazy.xsd"));

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        KopiaBazy dump = jaxbUnmarshaller.unmarshal(new StreamSource(file), KopiaBazy.class).getValue();
        Validator validator = schema.newValidator();
        validator.setErrorHandler(companyErrorHandler);
        JAXBSource source = new JAXBSource(jaxbContext, dump);
        errorCount = 0;
        validator.validate(source);

        if (errorCount == 0) {
            int rodzaje = zapiszRodzajePrzedmiotow(dump.getRodzajePrzedmiotow());
            int przedmioty = zapiszPrzedmioty(dump.getPrzedmioty());
            int uzytkownicy = zapiszUzytkownikow(dump.getUzytkownicy());
            int rezerwacje = zapiszRezerwacje(dump.getRezerwacje());
            int daneLogowania = zapiszDaneLogowania(dump.getDaneLogowania());
            System.out.println(rodzaje + " " + przedmioty + " " + uzytkownicy + " " + rezerwacje + " " + daneLogowania);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Statystyki importu");
            alert.setHeaderText(null);
            alert.setContentText("Import zakończył się następującym rezultatem:\n"
                    + "Rodzaje przedmiotów: " + (dump.getRodzajePrzedmiotow().size() - rodzaje) + " z " + dump.getRodzajePrzedmiotow().size() + "\n"
                    + "Przedmioty: " + (dump.getPrzedmioty().size() - przedmioty) + " z " + dump.getPrzedmioty().size() + "\n"
                    + "Użytkownicy: " + (dump.getUzytkownicy().size() - uzytkownicy) + " z " + dump.getUzytkownicy().size() + "\n"
                    + "Rezerwacje: " + (dump.getRezerwacje().size() - rezerwacje) + " z " + dump.getRezerwacje().size() + "\n"
                    + "Dane logowania: " + (dump.getDaneLogowania().size() - daneLogowania) + " z " + dump.getDaneLogowania().size() + ""
            );

            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Błąd walidacji XSD");
            alert.setHeaderText(null);
            alert.setContentText("Wybrany plik posiada nieprawidłową struktórę lub niedozwolone wartości.");
            alert.showAndWait();
        }
    }

    @FXML
    public void bazaExport() throws ClassNotFoundException, SQLException, JAXBException {
        System.out.println("export bazy");

        KopiaBazy dump = new KopiaBazy();
        dump.setRodzajePrzedmiotow(przygotujRodzajePrzedmiotow());
        dump.setPrzedmioty(przygotujPrzedmioty());
        dump.setUzytkownicy(przygotujUzytkownikow());
        dump.setRezerwacje(przygotujRezerwacje());
        dump.setDaneLogowania(przygotujDaneLogowania());

        JAXBContext jaxbContext = JAXBContext.newInstance(KopiaBazy.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz plik z kopią bazy danych");
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(ProtoLab.getpStage());
        if (file != null) {
            jaxbMarshaller.marshal(dump, file);
        }
    }

    /**
     * metoda zamkniecia aplikacji
     */
    public void exit() {
        Platform.exit();
    }

    private List<KopiaBazy.przedmioty> przygotujPrzedmioty() throws ClassNotFoundException, SQLException {
        List<KopiaBazy.przedmioty> result = new LinkedList<>();
        ResultSet rs = base.baseConnection().createStatement().executeQuery(""
                + "SELECT p.ID_przedmiotu, p.Id_rodzaj, p.Ilosc, p.Nazwa, p.Status "
                + "FROM przedmioty p");

        while (rs.next()) {
            KopiaBazy.przedmioty przedmiot = new KopiaBazy.przedmioty();
            przedmiot.setIdPrzedmiotu(rs.getInt(1));
            przedmiot.setIdRodzaj(rs.getInt(2));
            przedmiot.setIlosc(rs.getInt(3));
            przedmiot.setNazwa(rs.getString(4));
            przedmiot.setStatus(rs.getString(5));
            result.add(przedmiot);
        }
        return result;
    }

    private List<KopiaBazy.uzytkownicy> przygotujUzytkownikow() throws ClassNotFoundException, SQLException {
        List<KopiaBazy.uzytkownicy> result = new LinkedList<>();
        ResultSet rs = base.baseConnection().createStatement().executeQuery(""
                + "SELECT u.ID_uzytkownika, u.ID_uprawnienia, u.imie, u.nazwisko, u.numerTel, u.email, u.pesel "
                + "FROM uzytkownicy u");

        while (rs.next()) {
            KopiaBazy.uzytkownicy uzytkownik = new KopiaBazy.uzytkownicy();
            uzytkownik.setIdUzytkownika(rs.getInt(1));
            uzytkownik.setIdUprawnienia(rs.getInt(2));
            uzytkownik.setImie(rs.getString(3));
            uzytkownik.setNazwisko(rs.getString(4));
            uzytkownik.setNumerTel(rs.getString(5));
            uzytkownik.setEmail(rs.getString(6));
            uzytkownik.setPesel(rs.getString(7));
            result.add(uzytkownik);
        }
        return result;
    }

    private List<KopiaBazy.rezerwacje> przygotujRezerwacje() throws ClassNotFoundException, SQLException {
        List<KopiaBazy.rezerwacje> result = new LinkedList<>();
        ResultSet rs = base.baseConnection().createStatement().executeQuery(""
                + "SELECT r.idRezerwacji, r.ID_uzytkownika, r.ID_przedmiotu, r.od_kiedy, r.do_kiedy, r.ilosc "
                + "FROM rezerwacje r");

        while (rs.next()) {
            KopiaBazy.rezerwacje uzytkownik = new KopiaBazy.rezerwacje();
            uzytkownik.setIdRezerwacji(rs.getInt(1));
            uzytkownik.setIdUzytkownika(rs.getInt(2));
            uzytkownik.setIdPrzedmiotu(rs.getInt(3));
            uzytkownik.setOdKiedy(rs.getDate(4));
            uzytkownik.setDoKiedy(rs.getDate(5));
            uzytkownik.setIlosc(rs.getInt(6));
            result.add(uzytkownik);
        }
        return result;
    }

    private List<KopiaBazy.rodzaj_przedmiotu> przygotujRodzajePrzedmiotow() throws ClassNotFoundException, SQLException {
        List<KopiaBazy.rodzaj_przedmiotu> result = new LinkedList<>();
        ResultSet rs = base.baseConnection().createStatement().executeQuery(""
                + "SELECT rp.id_rodzaj, rp.nazwa_typu "
                + "FROM rodzaj_przedmiotu rp");

        while (rs.next()) {
            KopiaBazy.rodzaj_przedmiotu rodzaj = new KopiaBazy.rodzaj_przedmiotu();
            rodzaj.setIdRodzaj(rs.getInt(1));
            rodzaj.setNazwaTypu(rs.getString(2));
            result.add(rodzaj);
        }
        return result;
    }

    private List<KopiaBazy.dane_logowania> przygotujDaneLogowania() throws ClassNotFoundException, SQLException {
        List<KopiaBazy.dane_logowania> result = new LinkedList<>();
        ResultSet rs = base.baseConnection().createStatement().executeQuery(""
                + "SELECT dl.ID_konta, dl.Login, dl.Haslo, dl.Pass_Counter "
                + "FROM dane_logowania dl");

        while (rs.next()) {
            KopiaBazy.dane_logowania konto = new KopiaBazy.dane_logowania();
            konto.setIdKonta(rs.getInt(1));
            konto.setLogin(rs.getString(2));
            konto.setHaslo(rs.getString(3));
            konto.setPassCounter(rs.getInt(4));
            result.add(konto);
        }
        return result;
    }

    private int zapiszRodzajePrzedmiotow(List<KopiaBazy.rodzaj_przedmiotu> rodzaje_przedmiotow) {
        int result = 0;
        for (KopiaBazy.rodzaj_przedmiotu przedmiot : rodzaje_przedmiotow) {
            try {
                base.baseConnection().createStatement().execute("INSERT INTO rodzaj_przedmiotu VALUES('" + przedmiot.getIdRodzaj() + "', '" + przedmiot.getNazwaTypu() + "')");
            } catch (Exception e) {
                result++;
            }
        }
        return result;
    }

    private int zapiszPrzedmioty(List<KopiaBazy.przedmioty> przedmioty) {
        int result = 0;
        for (KopiaBazy.przedmioty przedmiot : przedmioty) {
            try {
                base.baseConnection().createStatement().execute("INSERT INTO przedmioty VALUES('" + przedmiot.getIdPrzedmiotu() + "', '" + przedmiot.getNazwa() + "', '" + przedmiot.getIdRodzaj() + "', '" + przedmiot.getIlosc() + "', '" + przedmiot.getStatus() + "')");
            } catch (Exception e) {
                result++;
            }
        }
        return result;
    }

    private int zapiszUzytkownikow(List<KopiaBazy.uzytkownicy> uzytkownicy) {
        int result = 0;
        for (KopiaBazy.uzytkownicy uzytkownik : uzytkownicy) {
            try {
                base.baseConnection().createStatement().execute("INSERT INTO uzytkownicy VALUES('" + uzytkownik.getIdUzytkownika() + "', '" + uzytkownik.getIdUprawnienia() + "', '" + uzytkownik.getImie() + "', '" + uzytkownik.getNazwisko() + "', '" + uzytkownik.getNumerTel() + "', '" + uzytkownik.getEmail() + "', '" + uzytkownik.getPesel() + "')");
            } catch (Exception e) {
                result++;
            }
        }
        return result;
    }

    private int zapiszRezerwacje(List<KopiaBazy.rezerwacje> rezerwacje) {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (KopiaBazy.rezerwacje rezerwacja : rezerwacje) {
            try {
                base.baseConnection().createStatement().execute("INSERT INTO rezerwacje VALUES('" + rezerwacja.getIdRezerwacji() + "', '" + rezerwacja.getIdUzytkownika() + "', '" + rezerwacja.getIdPrzedmiotu() + "', '" + sdf.format(rezerwacja.getOdKiedy()) + "', '" + sdf.format(rezerwacja.getDoKiedy()) + "', '" + rezerwacja.getIlosc() + "')");
            } catch (Exception e) {
                result++;
            }
        }
        return result;
    }

    private int zapiszDaneLogowania(List<KopiaBazy.dane_logowania> dane_logowania) {
        int result = 0;
        for (KopiaBazy.dane_logowania konto : dane_logowania) {
            try {
                base.baseConnection().createStatement().execute("INSERT INTO dane_logowania VALUES('" + konto.getIdKonta() + "', '" + konto.getLogin() + "', '" + konto.getHaslo() + "', '" + konto.getPassCounter() + "')");
            } catch (Exception e) {
                result++;
            }
        }
        return result;
    }

    private static int errorCount = 0;

    public static final ErrorHandler companyErrorHandler = new ErrorHandler() {
        @Override
        public void warning(SAXParseException exception) throws SAXException {
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            System.err.println(String.format("ERROR: %s", exception));
            errorCount++;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            errorCount++;
        }
    };
}
