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
public class Reservations {
    private final StringProperty Name;
    private final StringProperty Surname;
    private final StringProperty Item;
    private final IntegerProperty Number;
    private final StringProperty From;
    private final StringProperty To;
    
    public Reservations(String Name, String Surname, String Item, int Number, String From, String To){
        this.Name = new SimpleStringProperty(Name);
        this.Surname = new SimpleStringProperty(Surname);
        this.Item = new SimpleStringProperty(Item);
        this.Number = new SimpleIntegerProperty(Number);
        this.From = new SimpleStringProperty(From);
        this.To = new SimpleStringProperty(To);
        
    }
    public String getName(){
         return Name.get();
    }
    public String getSurname(){
         return Surname.get();
    }
    public String getItem(){
         return Item.get();
    }
    public int getNumber(){
         return Number.get();
    }
    public String getFrom(){
         return From.get();
    }
    public String getTo(){
         return To.get();
    }
    public void setName(String value) {
        Name.set(value);
    }
    public void setSurname(String value) {
        Surname.set(value);
    }
    public void setItem(String value) {
        Item.set(value);
    }
    public void setNumber(int value) {
        Number.set(value);
    }
    public void setFrom(String value) {
        From.set(value);
    }
    public void setTo(String value) {
        To.set(value);
    }
    public StringProperty getNameProperty() {
        return Name;
    }
    public StringProperty getSurnameProperty() {
        return Surname;
    }
    public StringProperty getItemProperty() {
        return Item;
    }
    public IntegerProperty getNumberProperty() {
        return Number;
    }
    public StringProperty getFromProperty() {
        return From;
    }
    public StringProperty getToProperty() {
        return To;
    }
}
