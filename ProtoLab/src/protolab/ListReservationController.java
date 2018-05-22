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
    public void generatePDF() throws ClassNotFoundException, SQLException, IOException, DocumentException{
//        cos jest jeszcze nie tak jak powinno 
        
        ProtoLabRaportPDF pdf=new ProtoLabRaportPDF();
        
        pdf.rs=pdf.executeDefaultQuery();
        pdf.rs.first();
        pdf.savePdf();
        pdf.document=pdf.setDocumentInfo( pdf.document, "autor", "cos ", "cos", "cos") ;
        pdf.document.open();
        pdf.document.add(pdf.setHeaderTab());
        pdf.document.add(ProtoLabRaportPDF.setInfoTable(ProtoLabRaportPDF.setInfoCell("Nadawca", "Zespół programistyczny", "Numer 4") , ProtoLabRaportPDF.setInfoCell("Nadawca", "Zespół programistyczny", "Numer 4")));
        pdf.document.add(pdf.setItemTable());
        pdf.document.close();
        
    }
    @FXML
    public void generatePdfStudent(){
        
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
    }