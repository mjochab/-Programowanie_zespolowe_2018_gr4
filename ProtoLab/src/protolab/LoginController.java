package protolab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import protolab.SessionService;

public class LoginController {

    private FXMLDocumentController mainController;
        @FXML
    private TextField textLogin;
        @FXML
    private TextField textPass;

    @FXML
    public void signIn() throws IOException, ClassNotFoundException, SQLException {
        BaseConnection base = new BaseConnection();
        Connection conn = base.baseConnection();
        String login=textLogin.getText();
        String pass=textPass.getText();
        int id=0;
        ResultSet rs = conn.createStatement().executeQuery("Select * from dane_logowania");
        rs.first();
        do{
            if(login.equals(rs.getString(2))&& pass.equals(rs.getString(3))){ 
                id=rs.getInt(1);
                break;     
            }
            
        }while(rs.next());
        if(id>0){
         setSession(id);
         setWindow(SessionService.getUserRights());
        }else{
         JOptionPane.showMessageDialog(null, "nie ma takiego użytkownika o takim loginie", "Exception", JOptionPane.ERROR_MESSAGE);

        }
    }
    
    public void setSession(int id) throws ClassNotFoundException, SQLException{
        BaseConnection base = new BaseConnection();
        Connection conn = base.baseConnection();
        ResultSet rx = null;
        try{
            //                                            1                               2               3                       4               5
        rx= conn.createStatement().executeQuery("SELECT uzytkownicy.ID_uzytkownika,uzytkownicy.imie,uzytkownicy.nazwisko,uzytkownicy.pesel, uzytkownicy.ID_uprawnienia "
                + "FROM uzytkownicy "
                + "WHERE uzytkownicy.ID_uzytkownika="+id); 
        rx.first();
        System.out.println(rx.getInt(1)+" "+rx.getString(2)+" "+rx.getString(3)+" "+rx.getInt(5));
        SessionService.setUserID(rx.getInt(1));
        SessionService.setUsername(rx.getString(2));
        SessionService.setUserRights(rx.getInt(5));
        }catch(SQLException sqle){
            System.out.println("Error: "+sqle);
            JOptionPane.showMessageDialog(null, "zły login/hasło lub nie ma takiego użytkownika", "SQLException", JOptionPane.ERROR_MESSAGE);
        }catch(Exception e){
            System.out.println(e);
        }

        
       
    }
    
    public void setWindow(int idRights) throws IOException, ClassNotFoundException, SQLException{
        if(idRights==1){
           
           signInStudent();
        }
        if(idRights==2){
           
            signInAdmin(); 
        }
        if(idRights==3){
             signInBoss();
        }
        
    }
     public void signInAdmin() throws IOException, ClassNotFoundException, SQLException {
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

//    @FXML
    public void signInBoss() throws IOException, ClassNotFoundException, SQLException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("BossPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BossPanelController szefController = loader.getController();
        szefController.setMainController(mainController);
        mainController.setScreen(pane);

    }
    
   // @FXML
    public void signInStudent() throws IOException, ClassNotFoundException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StudentPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StudentPanelController studentController = loader.getController();
        try {
            studentController.setMainController(mainController);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainController.setScreen(pane);
        
    }
    
    

    public void exit() {
        Platform.exit();
    }

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

}
