package protolab;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MSI GL62M 7RD
 */
public class EditUserController implements Initializable {
    private FXMLDocumentController mainController;
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
    private Button save;
    @FXML
    private Button back;
   
    BaseConnection base = new BaseConnection();
    public Users user;
    
    
      public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveChanges(ActionEvent event) throws ClassNotFoundException, SQLException {
         try{
        
        Connection conn = base.baseConnection();
        Statement stmnt = conn.createStatement();
        String query ="UPDATE uzytkownicy set imie = '" + name.getText()
                    + "', nazwisko = '" + lastName.getText()
                    + "', numerTel = '" + phone.getText()
                    + "', email = '" + email.getText()
                    + "', pesel = '" + pesel.getText()
                    + "' WHERE pesel = '" + this.user.getPesel() + "';";
        PreparedStatement ps = conn.prepareStatement(query);
        int deleteStudent = stmnt.executeUpdate(query);
        deleteStudent = ps.executeUpdate();

        } 
        catch(Exception ex){
              System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void back(ActionEvent event) {
    } 
    public void setUser(Users user){
        this.user = user;
        this.setData();
    }
    public void setData(){
        this.name.setText(user.getName());
        this.phone.setText(String.valueOf(user.getTelNumber()));
        this.email.setText(user.getEmail());
        this.lastName.setText(user.getSurname());
        this.pesel.setText(String.valueOf(user.getPesel()));
    }
}
