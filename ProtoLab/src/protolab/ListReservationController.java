package protolab;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import protolabpdf.*;

import com.itextpdf.text.*;
import javax.swing.JOptionPane;


/**
@ -20,16 +29,62 @@ import javafx.scene.layout.Pane;
 * @author Pc
 */
public class ListReservationController  {
    BaseConnection base = new BaseConnection();
    
    
     private FXMLDocumentController mainController;
    @FXML
    private Button delStudent;
    @FXML
    private TableView<Reservations> tableReservations;
    @FXML
    private TableColumn<Reservations, String> resName;
    @FXML
    private TableColumn<Reservations, String> resSurname;
    @FXML
    private TableColumn<Reservations, String> resItem;
    @FXML
    private TableColumn<Reservations, Integer> resNumber;
    @FXML
    private TableColumn<Reservations, String> resFrom;
    @FXML
    private TableColumn<Reservations, String> resTo;
    
    private ObservableList<Reservations> ResList;
    
      public void generatePDF() throws ClassNotFoundException, SQLException, IOException, DocumentException{
        
        ProtoLabRaportPDF pdf=new ProtoLabRaportPDF();
        
        pdf.rs=pdf.executeDefaultQuery();
        pdf.rs.first();
        pdf.savePdf();
        pdf.document=pdf.setDocumentInfo( pdf.document, "autor", SessionService.getUsername()+" "+SessionService.getUserSurname(), "cos", "cos") ;
        pdf.document.open();
        pdf.document.add(pdf.setHeaderTab());
        pdf.document.add(ProtoLabRaportPDF.setInfoTable(ProtoLabRaportPDF.setInfoCell("Nadawca", SessionService.getUsername()+" "+SessionService.getUserSurname(), " ") , ProtoLabRaportPDF.setInfoCell("Odbiorca", "Prowadzący zajęcia", "M.O.")));
        pdf.document.add(pdf.setItemTable());
        pdf.document.close();
        
    }
    @FXML
    public void generatePdfStudent() throws DocumentException, IOException, ClassNotFoundException, SQLException{
                ProtoLabRaportPDF pdf=new ProtoLabRaportPDF();
         try{    
        Connection conn = base.baseConnection();
        Reservations res= tableReservations.getSelectionModel().getSelectedItem();
        String name=res.getName();
        String surname= res.getSurname();
        String querry="SELECT uzytkownicy.ID_uzytkownika FROM uzytkownicy WHERE uzytkownicy.imie = '"+name+"' AND uzytkownicy.nazwisko= '"+surname+"'";
        ResultSet rc=conn.createStatement().executeQuery(querry);
        int idStudent;
        rc.first();
        idStudent=rc.getInt(1);
        pdf.rs=pdf.executeStudentQuery(idStudent);
        pdf.rs.first();
        pdf.savePdfStudent(pdf.rs);
        pdf.document=pdf.setDocumentInfo( pdf.document, SessionService.getUsername()+" "+SessionService.getUserSurname(), "cos ", "cos", "cos") ;
        pdf.document.open();
        pdf.document.add(pdf.setHeaderTab());
        pdf.document.add(ProtoLabRaportPDF.setInfoTable(ProtoLabRaportPDF.setInfoCell("Nadawca", SessionService.getUsername()+" "+SessionService.getUserSurname(), " ") , ProtoLabRaportPDF.setInfoCell("Nadawca", "Zespół programistyczny", "Numer 4")));
        pdf.document.add(pdf.setItemTable());
        pdf.document.close();}
        
         catch(NullPointerException ne){
             JOptionPane.showMessageDialog(null, "Nie wybrano elementu!", "info", JOptionPane.INFORMATION_MESSAGE);
         }
          catch(Exception e){
             System.out.println(e);
         }
    }
    @FXML
    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadReservations();
    }

    @FXML
    public void loadReservations() throws ClassNotFoundException, SQLException{
        try{
         String querry = "SELECT uzytkownicy.imie, uzytkownicy.nazwisko, przedmioty.Nazwa, rezerwacje.ilosc, rezerwacje.od_kiedy, rezerwacje.do_kiedy "
                + "FROM rezerwacje, uzytkownicy, przedmioty "
                + "WHERE uzytkownicy.ID_uzytkownika = rezerwacje.ID_uzytkownika and przedmioty.ID_przedmiotu = rezerwacje.ID_przedmiotu";
         Connection conn = base.baseConnection();
         ResList = FXCollections.observableArrayList();
         ResultSet rs = conn.createStatement().executeQuery(querry);
         while(rs.next()){
           ResList.add(new Reservations(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6)));
       }
         }catch(SQLException ex){
            System.out.println("Error"+ex);
    }
        resName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        resSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        resItem.setCellValueFactory(new PropertyValueFactory<>("Item"));
        resNumber.setCellValueFactory(new PropertyValueFactory<>("Number"));
        resFrom.setCellValueFactory(new PropertyValueFactory<>("From"));
        resTo.setCellValueFactory(new PropertyValueFactory<>("To"));
        
        tableReservations.setItems(null);
        tableReservations.setItems(ResList);
         
    }
    @FXML
   public void Back() throws ClassNotFoundException, SQLException, IOException {
       getWindow(SessionService.getUserRights());
               }

    @FXML
    
    public void getWindow(int idRights) throws IOException, ClassNotFoundException, SQLException{
        if(idRights==1){
           BackStudentReservation();
           
        }
        if(idRights==2){
           
           BackAdminReservation(); 
        }
        if(idRights==3){
           
           BackBossReservation();
            
        }
    }
        
    @FXML
    public void BackAdminReservation() throws IOException, ClassNotFoundException, SQLException {
        
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
    public void BackBossReservation() throws IOException, ClassNotFoundException, SQLException {
        
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
    public void BackStudentReservation() throws IOException, ClassNotFoundException, SQLException {
        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StudentPanel.fxml"));

        Pane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentPanelController studentController = loader.getController();
        studentController.setMainController(mainController);
        mainController.setScreen(pane);
    }
    
    @FXML
     public void exit() {
        Platform.exit();
    }  
    }