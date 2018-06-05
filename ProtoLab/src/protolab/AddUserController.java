package protolab;

import java.awt.Font;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import protolab.exceptions.EmailInvalidFormatException;
import protolab.exceptions.LoginAlreadyExistsException;
import protolab.exceptions.NameInvalidValueException;
import protolab.exceptions.PeselLengthException;
import protolab.exceptions.PeselSumControlNumberException;
import protolab.exceptions.RightsNotSelectedExeception;

public class AddUserController {

    private FXMLDocumentController mainController;
    /**
     * metoda powracajaca do listy studentów
     */
    ObservableList<String> rights;
    @FXML
    private TextField name;
    @FXML
    private TextField lastName;
    @FXML
    private TextField pesel;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField login;
    @FXML
    private TextField passwd;
    @FXML
    private Button save;
    @FXML
    private Button back;
    @FXML
    private Label loginLabel;
    @FXML
    private ComboBox<String> boxRights = new ComboBox<>();
     
    BaseConnection base = new BaseConnection();
    public Users user;
    
    String errorMsg = "<html><body width=300><h2>Błąd</h2>";
    
    public void loadRights() throws SQLException, ClassNotFoundException{
        Connection conn = base.baseConnection();
        rights = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery("SELECT uprawnienia.rodzajUprawnienia FROM uprawnienia");

        while (rs.next()) {
            rights.add(rs.getString(1));
        }

        boxRights.setItems(rights);
    }

    @FXML
    public void backMenu() throws ClassNotFoundException, SQLException {
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

    public void setMainController(FXMLDocumentController mainController) throws SQLException, ClassNotFoundException {
        this.mainController = mainController;
        loadRights();

    }

    /**
     * metoda zamykajaca aplikacje
     */
    @FXML
    public void checkAvailableLogin() throws ClassNotFoundException, SQLException, LoginAlreadyExistsException {

        if (checkLogin(false)) {
            loginLabel.setText("login jest dostępny");
        } else {
            loginLabel.setText("Taki login już istnieje w bazie danych");
        }
    }

    public void addNewUser() throws ClassNotFoundException, SQLException, LoginAlreadyExistsException, NameInvalidValueException, EmailInvalidFormatException, PeselLengthException, PeselSumControlNumberException, RightsNotSelectedExeception {

        if (checkLogin(true) && checkName() && checkLastName() && checkNumberPhone() && checkEmail() && checkNumberPesel() && checkIsSelectedRights()) {

            try {
                ////

                ////
                Connection conn = base.baseConnection();
                String query = "INSERT INTO uzytkownicy(imie, nazwisko, numerTel, email, pesel, ID_uprawnienia) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement prstm = conn.prepareStatement(query);
                /**
                 * kolejno przekazywane parametry do zapytania
                 */
                prstm.setString(1, name.getText());
                prstm.setString(2, lastName.getText());
                prstm.setLong(3, Long.parseLong(phone.getText()));
                prstm.setString(4, email.getText());
                prstm.setLong(5, Long.parseLong(pesel.getText()));
                prstm.setInt(6, getIdRights(boxRights.getSelectionModel().getSelectedItem()));
                prstm.executeUpdate();
                prstm.close();
                query = "INSERT INTO `dane_logowania` (`Login`, `Haslo`, `Pass_Counter`) VALUES ( ?, ?, '1');";
                prstm = conn.prepareStatement(query);
                prstm.setString(1, login.getText());
                prstm.setString(2, passwd.getText());
                prstm.executeUpdate();
                prstm.close();
                JOptionPane.showMessageDialog(null, "Poprawnie dodano użytkownika: " + name.getText() + " Pypisano mu tymczasowe hasło: " + passwd.getText(), "", JOptionPane.INFORMATION_MESSAGE);
                backMenu();
            } catch (Exception sql) {
                System.out.println(sql);
                errorMsg += "<p>*" + sql;
            }
        } else {
            if (!checkLogin(false)) {
                login.clear();
            }
            if (!checkName()) {
                name.clear();
            }
            if (!checkLastName()) {
                lastName.clear();
            }
            if (!checkNumberPhone()) {
                phone.clear();
            }
            if (!checkEmail()) {
                email.clear();
            }
            if (!checkNumberPesel()) {
                pesel.clear();
            }
            JOptionPane.showMessageDialog(null, errorMsg, "", JOptionPane.ERROR_MESSAGE);
        }
//////////////////////////////

    }

    public boolean checkLogin(boolean writeErr) throws ClassNotFoundException, SQLException, LoginAlreadyExistsException {
        try {
            Connection conn = base.baseConnection();
            ResultSet rs = conn.createStatement().executeQuery("Select * from dane_logowania");
            rs.first();
            do {
                if (login.getText().equals(rs.getString(2))) {
                    throw new LoginAlreadyExistsException("Taki login już istnieje w bazie");
                }
            } while (rs.next());
            if(login.getText().equals(new String(""))){
                throw new NullPointerException("puste pole Login");
            }
        }catch(NullPointerException nullE){
            if (writeErr) {
                 errorMsg += "<p>*Puste pole Login";
            }  
            return false;
        } catch (LoginAlreadyExistsException loginE) {
            if (writeErr) {
                errorMsg += "<p>*Taki login znajduje się już w bazie";
            }
            return false;
        } catch (SQLException sqlE) {
            if (writeErr) {
                errorMsg += "<p>*p=Problem w zapytaniu SQL";
            }
            return false;
        }
        return true;
    }

    public boolean checkName() throws NameInvalidValueException {
        try {
            if (name.getText().matches(".*\\d+.*")) {
                throw new NameInvalidValueException("Liczby w Imieniu");

            }
        } catch (NameInvalidValueException nameE) {
            errorMsg += "<p>*Imię nie może posiadać cyfr";
            return false;
        } catch (NullPointerException nullE) {
            errorMsg += "<p>*Pole Imienia nie może zostać puste";
            return false;
        }
        return true;

    }

    public boolean checkLastName() throws NameInvalidValueException {
        try {
            if (lastName.getText().matches(".*\\d+.*")) {
                throw new NameInvalidValueException("Liczby w Nazwisku");

            }
        } catch (NameInvalidValueException nameE) {
            errorMsg += "<p>*Nazwisko nie może posiadać cyfr";
            return false;
        } catch (NullPointerException nullE) {
            errorMsg += "<p>*Pole Nazwiska nie może zostać puste";
            return false;
        }
        return true;

    }

    public boolean checkNumberPhone() {
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phone.getText());
        } catch (NumberFormatException num) {
            System.out.println(num);
            errorMsg += "<p>*Niepoprawny numer telefonu";
            return false;

            /////
        }
        return true;

    }

    public boolean checkNumberPesel() throws PeselLengthException, PeselSumControlNumberException {
        long peselNumber;

        try {
            peselNumber = Long.parseLong(pesel.getText());

            if (pesel.getText().length() != 11) {
                throw new PeselLengthException(pesel.getText().length() + "");
            }

            if (!checkSumControlPesel()) {
                throw new PeselSumControlNumberException("Podany pesel nie jest prawidłowy-nie zgadza się suma kontrolna");
            }
        } catch (PeselLengthException pLen) {
            errorMsg += "<p>*Numer pesel jest zbyt krótki lub zbyt długi";
            pesel.clear();
        } catch (NumberFormatException num) {
            errorMsg += "<p>*Podany wartość w polu pesel nie jest peselem";
            pesel.clear();
        } catch (PeselSumControlNumberException pSum) {
            errorMsg += "<p>*Nie zgadza sie suma kontrolna numeru pesel";
            pesel.clear();
        }

        return true;
    }

    private boolean checkSumControlPesel() {
        String peselString = pesel.getText();
        long sum = 1 * Long.parseLong(peselString.charAt(0) + "")
                + 3 * Long.parseLong(peselString.charAt(1) + "")
                + 7 * Long.parseLong(peselString.charAt(2) + "")
                + 9 * Long.parseLong(peselString.charAt(3) + "")
                + 1 * Long.parseLong(peselString.charAt(4) + "")
                + 3 * Long.parseLong(peselString.charAt(5) + "")
                + 7 * Long.parseLong(peselString.charAt(6) + "")
                + 9 * Long.parseLong(peselString.charAt(7) + "")
                + 1 * Long.parseLong(peselString.charAt(8) + "")
                + 3 * Long.parseLong(peselString.charAt(9) + "");
        sum %= 10;

        sum = 10 - sum;
        sum %= 10;

        if (sum == Long.parseLong(peselString.charAt(10) + "")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEmail() throws EmailInvalidFormatException {

        try {
            if (!email.getText().matches("^.+@.+\\..+$")) {
                throw new EmailInvalidFormatException("niepoprawny mail");
            }
        } catch (EmailInvalidFormatException ee) {
            errorMsg += "<p>*Niepoprawny adres email";
            return false;
        }
        return true;
    }
   private boolean checkIsSelectedRights()throws RightsNotSelectedExeception{
       try{
           if(boxRights.getSelectionModel().getSelectedItem().isEmpty()){
               throw new RightsNotSelectedExeception("nie wybrano uprawnień użytkownika");
           }
       }catch(RightsNotSelectedExeception re){
           errorMsg += "<p>*Nie wybrano uprawnień dla użytkownika";
           return false;
       }
       return true;
   }
   public int getIdRights(String nameRights) throws ClassNotFoundException, SQLException{
        Connection conn = base.baseConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT uprawnienia.ID_uprawnienia FROM uprawnienia WHERE uprawnienia.rodzajUprawnienia='"+nameRights+"'");
        rs.first();
        return rs.getInt(1);
   }

}
