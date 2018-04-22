/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Wojtek
 */
public class Baza_Conn {
     private Connection conn = null;
    
    public Connection baza_polacz() throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/protolabdb","root","");
            System.out.println("connected");
            return conn;
        }catch (SQLException e){
            System.err.println("Problem z otwarciem połączenia");
        }catch(ClassNotFoundException e2){
            System.err.println("Niewłaściwy sterownik JDBC ");
            e2.printStackTrace();
        }
        return null;
        
    }
}
