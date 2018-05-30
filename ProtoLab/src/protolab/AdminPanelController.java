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
/**
 * 
 * @author Winnicki Kamil
 */


public class AdminPanelController {
    
    
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
    private TableColumn<Items, String> tabStatus;
    @FXML
    private ObservableList<Items> itemList;
    
   @FXML
    public void loadItems() throws ClassNotFoundException, SQLException{
        
        try{
       
       Connection conn = base.baseConnection();
       itemList = FXCollections.observableArrayList();
       ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM przedmioty");
       while(rs.next()){
           itemList.add(new Items(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
       }
     }catch(SQLException ex){
            System.out.println("Error"+ex);
    }
        
        tabName.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        tabType.setCellValueFactory(new PropertyValueFactory<>("Rodzaj"));
        tabQuantity.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
        tabStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        
        tablePrzedmioty.setItems(null);
        tablePrzedmioty.setItems(itemList);
    }
    /**
     * metoda uruchamiajaca okno listy użytkowników
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
     * @throws IOException 
     */
            
    @FXML
    public void ListReservations() throws IOException, ClassNotFoundException, SQLException {
        
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
     * @throws IOException 
     */
    @FXML
    public void addToTheWarehouse() throws IOException {
        
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
   public void backMenu(){
       mainController.loadMenuScreen();
   }

    public void setMainController(FXMLDocumentController mainController) throws ClassNotFoundException, SQLException {
        this.mainController = mainController;
        loadItems();
    }
    
    @FXML
     public void searchItem2() throws ClassNotFoundException{
       try{
        
       String szuk = WpisywanieProdukt.getText().toLowerCase();
       Connection conn = base.baseConnection();
       itemList = FXCollections.observableArrayList();
       ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM przedmioty");
       while(rs.next()){
          if(rs.getString(2).toLowerCase().startsWith(szuk)){
           itemList.add(new Items(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
       }}
     }catch(SQLException ex){
            System.out.println("Error"+ex);
    }
          tabName.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        tabType.setCellValueFactory(new PropertyValueFactory<>("Rodzaj"));
        tabQuantity.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
        tabStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        
        tablePrzedmioty.setItems(null);
        tablePrzedmioty.setItems(itemList);     
     }
    /**
     * metoda zamkniecia aplikacji
     */
    public void exit() {
        Platform.exit();
    }
   
   
    
}
