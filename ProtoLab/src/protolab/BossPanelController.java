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
    private TableView<Users> tableUsers;
    @FXML
    private TableColumn<Users, String> tabName;
    @FXML
    private TableColumn<Users, String> tabSurname;
    @FXML
    private TableColumn<Users, Integer> tabTel;
    @FXML
    private TableColumn<Users, String> tabEmail;
    @FXML
    private TableColumn<Users, Long> tabPesel;
    
    private ObservableList<Users> UsersList;

    
    @FXML
    public void loadStudents() throws IOException{
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
    
//    @FXML
//    public void loadUsers() throws ClassNotFoundException, SQLException{
//        try{
//       
//       Connection conn = base.baseConnection();
//       UsersList = FXCollections.observableArrayList();
//       ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM uzytkownicy");
//       while(rs.next()){
//           UsersList.add(new Users(rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getLong(7)));
//       }
//     }catch(SQLException ex){
//            System.out.println("Error"+ex);
//    }
//        
//        tabName.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        tabSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
//        tabTel.setCellValueFactory(new PropertyValueFactory<>("TelNumber"));
//        tabEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
//        tabPesel.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
//        
//        tableUsers.setItems(null);
//        tableUsers.setItems(UsersList);
//    }
    
    @FXML
    public void ListReservations() throws IOException {
        
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
    @FXML
    public void backMenu() {
        mainController.loadMenuScreen();
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }
    @FXML
     public void exit() {
        Platform.exit();
    }

}
