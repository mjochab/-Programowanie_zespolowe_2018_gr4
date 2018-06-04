/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Klasa odpowiadająca za połączenie się z bazą danych.
 * 
 * @author WKozyra
 */
public class BaseConnection {
     private Connection conn = null;
    
     /**
      * Załączony zostaje odpowiedni sterownik JDBC.
      * Następnie podajemy ścieżkę połączenia do bazy, jej nazwę, nazwę użytkownika oraz hasło.
      * W razie problemów z połączeniem z bazą lub problemów z sterownikiem JDBC, zostanie wyświetlony odpowiedni wyjątek z opisem.
      * @throws ClassNotFoundException 
      */
    public Connection baseConnection() throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/protolabdb","root","");
            return conn;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Problem z otwarciem połączenia", "error", JOptionPane.ERROR_MESSAGE);
        }catch(ClassNotFoundException e2){
            System.err.println("Niewłaściwy sterownik JDBC ");
            e2.printStackTrace();
        }
        return null;
        
    }
}
