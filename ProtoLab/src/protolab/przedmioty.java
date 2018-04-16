/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Wojtek
 */
public class przedmioty {
    private final IntegerProperty ID;
    private final StringProperty Nazwa;
    private final StringProperty Rodzaj;
    private final IntegerProperty Ilosc;
    private final StringProperty Status;
    
    public przedmioty(int ID, String Nazwa, String Rodzaj, int Ilosc, String Status){
        this.ID = new SimpleIntegerProperty(ID);
        this.Nazwa = new SimpleStringProperty(Nazwa);
        this.Rodzaj = new SimpleStringProperty(Rodzaj);
        this.Ilosc = new SimpleIntegerProperty(Ilosc);
        this.Status = new SimpleStringProperty(Status);
    }
    public int getID(){
         return ID.get();
    }
    public void setID(int value) {
        ID.set(value);
    }
    public String getNumerPokoju(){
         return Nazwa.get();
    }
     public void setNumerPokoju(String value) {
        Nazwa.set(value);
    }
    public String getRodzaj(){
         return Rodzaj.get();
    }
     public void setRodzaj(String value) {
        Rodzaj.set(value);
    } 
     public int getIlosc(){
         return Ilosc.get();
    }
     public void setIlosc(int value) {
        Ilosc.set(value);
    } 
     public String getStatus(){
         return Status.get();
    }
     public void setStatus(String value) {
        Status.set(value);
    }
     public IntegerProperty getIDProperty() {
        return ID;
    }
    public StringProperty getNazwaProperty() {
        return Nazwa;
    }

    public StringProperty getRodzajProperty() {
        return Rodzaj;
    }

    public IntegerProperty getIloscProperty() {
        return Ilosc;
    }

    public StringProperty getStatusProperty() {
        return Status;
    }      
}
