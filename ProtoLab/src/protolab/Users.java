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
 *
 * @author Wojtek
 */
public class Users {
    private final StringProperty Name;
    private final StringProperty Surname;
    private final IntegerProperty TelNumber;
    private final StringProperty Email;
    private final LongProperty Pesel;
    
     public Users(String Name, String Surname, int TelNumber, String Email, long Pesel){
        
        this.Name = new SimpleStringProperty(Name);
        this.Surname = new SimpleStringProperty(Surname);
        this.TelNumber = new SimpleIntegerProperty(TelNumber);
        this.Email = new SimpleStringProperty(Email);
        this.Pesel = new SimpleLongProperty(Pesel);
        
    }
     
     public String getName(){
         return Name.get();
    }
    
    public void setName(String value) {
        Name.set(value);
    }
     
    public String getSurname(){
         return Surname.get();
    }
    
    public void setSurname(String value) {
        Surname.set(value);
    }
     
    public int getTelNumber(){
         return TelNumber.get();
    }
     
     public void setTelNumber(int value) {
        TelNumber.set(value);
    }
     
     public String getEmail(){
         return Email.get();
    }
     
     public void setEmail(String value) {
        Email.set(value);
    }
     
     public long getPesel(){
         return Pesel.get();
    }
     
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
