/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Klasa dzieki której możliwe jest pobieranie danych z bazy danych i ustawianie wartości atrybutu obiektu.
 * 
 * @author Wojtek
 */
public class Users {
    
    private final StringProperty Name;
    private final StringProperty Surname;
    private final IntegerProperty TelNumber;
    private final StringProperty Email;
    private final LongProperty Pesel;
    
    /**
     * Konstruktor klasy Users.
     * 
     * @param Name
     * @param Surname
     * @param TelNumber
     * @param Email
     * @param Pesel 
     */
     public Users(String Name, String Surname, int TelNumber, String Email, long Pesel){
        
        this.Name = new SimpleStringProperty(Name);
        this.Surname = new SimpleStringProperty(Surname);
        this.TelNumber = new SimpleIntegerProperty(TelNumber);
        this.Email = new SimpleStringProperty(Email);
        this.Pesel = new SimpleLongProperty(Pesel);
        
    }
     /**
      * Getter pobierający wartość Name
      * @return zwraca wartość Name
      */
     public String getName(){
         return Name.get();
    }
    /**
     * Setter ustawiający wartość atrybutu obiektu.
     * @param value 
     */
    public void setName(String value) {
        Name.set(value);
    }
     /**
      * Geter pobierający wartość Surname
      * @return zwraca wartość Surname
      */
    public String getSurname(){
         return Surname.get();
    }
    /**
     * Setter ustawiający wartość atrybutu obiektu.
     * @param value 
     */
    public void setSurname(String value) {
        Surname.set(value);
    }
     /**
      * Geter pobierający wartość TelNumber
      * @return zwraca wartość TelNumber
      */
    public int getTelNumber(){
         return TelNumber.get();
    }
     /**
     * Setter ustawiający wartość atrybutu obiektu.
     * @param value 
     */
     public void setTelNumber(int value) {
        TelNumber.set(value);
    }
     /**
      * Geter pobierający wartość Email
      * @return zwraca wartość Email
      */
     public String getEmail(){
         return Email.get();
    }
     /**
     * Setter ustawiający wartość atrybutu obiektu.
     * @param value 
     */
     public void setEmail(String value) {
        Email.set(value);
    }
     /**
      * Geter pobierający wartość Pesel
      * @return zwraca wartość Pesel
      */
     public long getPesel(){
         return Pesel.get();
    }
     /**
     * Setter ustawiający wartość atrybutu obiektu.
     * @param value 
     */
     public void setPesel(long value) {
        Pesel.set(value);
    }
    public StringProperty getNameProperty() {
        return Name;
    }

    public StringProperty getSurnameProperty() {
        return Surname;
    }

    public IntegerProperty getTelNumberProperty() {
        return TelNumber;
    }

    public StringProperty getEmailProperty() {
        return Email;
    } 
    public LongProperty getPeselProperty() {
        return Pesel;
    }
}
