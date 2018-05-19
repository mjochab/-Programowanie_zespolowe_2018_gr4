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
 *  klasa dzieki ktorej mozliwe jest pobieranie danych z bazy danych i ustawienie ich wartosci
 * @author Pjoter
 */
public class Items {
    
    private final StringProperty Nazwa;
    private final StringProperty Rodzaj;
    private final IntegerProperty Ilosc;
    private final StringProperty Status;
    /**
     * konstruktor klasy Items
     * @param Nazwa
     * @param Rodzaj
     * @param Ilosc
     * @param Status 
     */
    public Items(String Nazwa, String Rodzaj, int Ilosc, String Status){
        
        this.Nazwa = new SimpleStringProperty(Nazwa);
        this.Rodzaj = new SimpleStringProperty(Rodzaj);
        this.Ilosc = new SimpleIntegerProperty(Ilosc);
        this.Status = new SimpleStringProperty(Status);
    }
    /**
     * metoda pobierajaca pole nazwa
     * @return 
     */
    public String getNazwa(){
         return Nazwa.get();
    }
    /**
     * metoda przypisujaca wartosc do pola nazwa
     * @param value 
     */
    public void setNazwa(String value) {
        Nazwa.set(value);
    }
     /**
      * metoda pobierajaca pole rodzaj
      * @return 
      */
    public String getRodzaj(){
         return Rodzaj.get();
    }
    /**
     * metoda przypisujaca wartosc do pola rodzaj
     * @param value 
     */
    public void setRodzaj(String value) {
        Rodzaj.set(value);
    }
     /**
      * metoda pobierajaca pole ilosc
      * @return 
      */
    public int getIlosc(){
         return Ilosc.get();
    }
     /**
      * metoda przypisujaca wartosc do pola liosc
      * @param value 
      */
     public void setIlosc(int value) {
        Ilosc.set(value);
    }
     /**
      * metoda pobierajaca pole status
      * @return 
      */
     public String getStatus(){
         return Status.get();
    }
     /**
      * metoda przypisujaca wartosc do pola status
      * @param value 
      */
     public void setStatus(String value) {
        Status.set(value);
    }
     /**
      * metoda zwracajaca nazwa
      * @return 
      */
    public StringProperty getNazwaProperty() {
        return Nazwa;
    }
/**
 * metoda zwracajaca rodzaj
 * @return 
 */
    public StringProperty getRodzajProperty() {
        return Rodzaj;
    }
/**
 * metoda zwracajaca ilosc
 * @return 
 */
    
    public IntegerProperty getIloscProperty() {
        return Ilosc;
    }
/**
 * metoda zwracajaca status
 * @return 
 */
    public StringProperty getStatusProperty() {
        return Status;
    }      
}
