/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ListStudentsController {
    BaseConnection base = new BaseConnection();
    private FXMLDocumentController mainController;
    @FXML
    private Button addStudent;
    @FXML
    private Button deleteStudent;
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
    @FXML
    private Button editStudent;

  
    
    
    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadUsers();
       
    }
    @FXML
    public void loadUsers() throws ClassNotFoundException, SQLException{
        try{
       
       Connection conn = base.baseConnection();
       usersList = FXCollections.observableArrayList();
       ResultSet rs = conn.createStatement().executeQuery("SELECT uzytkownicy.ID_uzytkownika, uzytkownicy.imie, uzytkownicy.nazwisko, uzytkownicy.numerTel, uzytkownicy.email, uzytkownicy.pesel FROM uzytkownicy WHERE uzytkownicy.ID_uprawnienia = 1");
       while(rs.next()){
           usersList.add(new Users(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getLong(6)));
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
    
    @FXML
    public void deleteStudent() throws IOException, ClassNotFoundException{
        try{
        
        Connection conn = base.baseConnection();
        Statement stmnt = conn.createStatement();
        long pesel = tableUsers.getSelectionModel().getSelectedItem().getPesel();
        int selectedIndex = tableUsers.getSelectionModel().getSelectedIndex();
                
        String query = "delete from uzytkownicy where pesel ='" +pesel+"';";
        PreparedStatement ps = conn.prepareStatement(query);
        int deleteStudent = stmnt.executeUpdate(query);
        deleteStudent = ps.executeUpdate();
        this.loadUsers();
        } 
        catch(NullPointerException ex){
              JOptionPane.showMessageDialog(null,"Nie wybrano użytkownika do usunięcia","info",JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
      
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
    
    @FXML
    private void editStudent(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
          FXMLLoader loader = new FXMLLoader();
          
         Users user = null;
          loader.setLocation(getClass().getResource("EditStudent.fxml"));
          try{
              user = tableUsers.getSelectionModel().getSelectedItem();
              loader.load();
          }
          
          catch(IOException ex){
             System.out.println(ex.getMessage());
          }
          if(user != null){
             
          EditStudentController editStudent = loader.getController();
          editStudent.setStudent(user);
          Parent p = loader.getRoot();
          Stage stage = new Stage();
          stage.setScene(new Scene(p));
          stage.showAndWait();
           
          }
          else{
               JOptionPane.showMessageDialog(null,"Nie wybrano użytkownika do edycji","info",JOptionPane.INFORMATION_MESSAGE);
          }
         
    }
    
     public void exit() {
        Platform.exit();
    }
    
}
