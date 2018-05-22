/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
/**
 * FXML Controller class
 *
 * @author Winnicki Kamil
 */
public class ListUsersController {
    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    @FXML
    private Button addStudent;
    @FXML
    private Button delStudent;
    @FXML
    private TableView<Users> tableUsers;
    @FXML
    private TableColumn<Users, String> userName;
    @FXML
    private TableColumn<Users, String> userSurname;
    @FXML
    private TableColumn<Users, Integer> userTel;
    @FXML
    private TableColumn<Users, String> userEmail;
    @FXML
    private TableColumn<Users, Integer> userPesel;
    
    private ObservableList<Users> usersList;
    
    
    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadUsers();
    }
    
     @FXML
    public void registrationAdmin() throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AddAdmin.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddAdminController addAdminController = loader.getController();
        addAdminController.setMainController(mainController);
        mainController.setScreen(pane);

    }
    
    @FXML
    public void registrationBoss() throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AddBoss.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddBossController addBoss = loader.getController();
        addBoss.setMainController(mainController);
        mainController.setScreen(pane);

    }
    @FXML
    public void registrationStudent() throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AddStudent.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddStudentController addStudentController = loader.getController();
        addStudentController.setMainController(mainController);
        mainController.setScreen(pane);

    }
    @FXML
    public void Back() throws IOException, ClassNotFoundException, SQLException {
        
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
    @FXML
     public void exit() {
        Platform.exit();
    }
     @FXML
    public void loadUsers() throws ClassNotFoundException, SQLException{
        try{
       
       Connection conn = base.baseConnection();
       usersList = FXCollections.observableArrayList();
       ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM uzytkownicy");
       while(rs.next()){
           usersList.add(new Users(rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getLong(7)));
       }
     }catch(SQLException ex){
            System.out.println("Error"+ex);
    }
        
        userName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        userSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        userTel.setCellValueFactory(new PropertyValueFactory<>("TelNumber"));
        userEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        userPesel.setCellValueFactory(new PropertyValueFactory<>("Pesel"));
        
        tableUsers.setItems(null);
        tableUsers.setItems(usersList);
    }
    
}
