package protolab;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import protolab.exceptions.TypeItemNotSelectedException;

/**
 * FXML Controller class
 *
 * @author Wojciech Kozłowski
 *
 * Klasa dopowiadająca za edytowanie przedmiotów w bazie
 */


public class EditItemController implements Initializable {
    ObservableList<String> types;
    private FXMLDocumentController mainController;
    @FXML
    private TextField textName;
    @FXML
    private ComboBox<String> boxType = new ComboBox<>();
    @FXML
    private TextField textNumber;
    @FXML
    private TextField textType;
    
    String errorMsg = "<html><body width=300><h2>Błąd</h2>";
    
    BaseConnection base = new BaseConnection();
    public Items item;
    
    
    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadTypes();
    }

//    @FXML
//    public void newType() throws SQLException, ClassNotFoundException {
//        String typeName = JOptionPane.showInputDialog("Podaj nowy rodzaj przedmiotów: ");
//        if(typeName!=null){
//        if (checkTypeAlreadyExtist(typeName) || checkTypeAlreadyExtist(typeName.toLowerCase())) {
//            JOptionPane.showMessageDialog(null, "Taki typ przedmiotu już istnieje", "", JOptionPane.ERROR_MESSAGE);
//        } else {
//            Connection conn = base.baseConnection();
//            PreparedStatement prstm = conn.prepareStatement("INSERT INTO `rodzaj_przedmiotu` (`nazwa_typu`) VALUES (?);");
//
//            prstm.setString(1, typeName);
//            prstm.executeUpdate();
//            prstm.close();
//            loadEditItem();
//            JOptionPane.showMessageDialog(null, "Poprawnie dodano nowy typ przedmiotu", "", JOptionPane.INFORMATION_MESSAGE);
//        }
//        }
//    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    textType.setDisable(true);
    }
      
    @FXML
    private void saveChanges(ActionEvent event) throws ClassNotFoundException, SQLException, TypeItemNotSelectedException {
        if (checkValueHowManyItems() && checkIsSelectedRights()){
            try{
        
            Connection conn = base.baseConnection();
            int id_rodzaj = getIdTypes(boxType.getSelectionModel().getSelectedItem());
            String query ="UPDATE przedmioty set Nazwa = '" + textName.getText()
                    + "', Ilosc = '" + textNumber.getText()
                    + "', id_rodzaj = '" + id_rodzaj
                    + "' WHERE Nazwa = '" + this.item.getNazwa() + "';";
            PreparedStatement ps = conn.prepareStatement(query);
            Statement stmnt = conn.createStatement();
            int editItem = stmnt.executeUpdate(query);
            editItem = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pomyślnie zaktualizowano informacje o użytkowniku", "", JOptionPane.INFORMATION_MESSAGE);
            }
         
            catch(Exception ex){
              System.out.println(ex.getMessage());
            }
            } else {
                if (!checkValueHowManyItems()) {
                    textNumber.clear();
                }
                JOptionPane.showMessageDialog(null, errorMsg, "", JOptionPane.ERROR_MESSAGE);
            }
        
        
    }
        
    public void loadTypes() throws ClassNotFoundException, SQLException {
        Connection conn = base.baseConnection();
        types = FXCollections.observableArrayList(); 
        ResultSet rs = conn.createStatement().executeQuery("SELECT rodzaj_przedmiotu.nazwa_typu FROM rodzaj_przedmiotu");

        while (rs.next()) {
            types.add(rs.getString(1));
        }
        boxType.setItems(types);
        rs.first();
    }
    
    public int getIdTypes(String nameTypes) throws ClassNotFoundException, SQLException {
        Connection conn = base.baseConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT rodzaj_przedmiotu.id_rodzaj FROM rodzaj_przedmiotu WHERE rodzaj_przedmiotu.nazwa_typu='" + nameTypes + "'");
        rs.first();
        return rs.getInt(1);
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

    public void setItem(Items item){
        this.item = item;
        this.setData();
    }
    public void setData(){
        this.textName.setText(item.getNazwa());
        this.boxType.setValue(String.valueOf(item.getRodzaj()));
        this.textNumber.setText(String.valueOf(item.getIlosc()));
        this.textType.setText(item.getRodzaj());
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
    
}

    

