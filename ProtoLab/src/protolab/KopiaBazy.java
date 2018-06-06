/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protolab;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Piotr Medygrał
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class KopiaBazy {

    @XmlElementWrapper(name = "rodzajePrzedmiotow")
    @XmlElement(name = "rodzajPrzedmiotu")
    private List<rodzaj_przedmiotu> rodzajePrzedmiotow;

    @XmlElementWrapper(name = "przedmioty")
    @XmlElement(name = "przedmiot")
    private List<przedmioty> przedmioty;

    @XmlElementWrapper(name = "uzytkownicy")
    @XmlElement(name = "uzytkownik")
    private List<uzytkownicy> uzytkownicy;

    @XmlElementWrapper(name = "rezerwacje")
    @XmlElement(name = "rezerwacja")
    private List<rezerwacje> rezerwacje;

    @XmlElementWrapper(name = "daneLogowania")
    @XmlElement(name = "konto")
    private List<dane_logowania> daneLogowania;

    public List<rodzaj_przedmiotu> getRodzajePrzedmiotow() {
        return rodzajePrzedmiotow;
    }

    public void setRodzajePrzedmiotow(List<rodzaj_przedmiotu> rodzajePrzedmiotow) {
        this.rodzajePrzedmiotow = rodzajePrzedmiotow;
    }

    public List<przedmioty> getPrzedmioty() {
        return przedmioty;
    }

    public void setPrzedmioty(List<przedmioty> przedmioty) {
        this.przedmioty = przedmioty;
    }

    public List<uzytkownicy> getUzytkownicy() {
        return uzytkownicy;
    }

    public void setUzytkownicy(List<uzytkownicy> uzytkownicy) {
        this.uzytkownicy = uzytkownicy;
    }

    public List<rezerwacje> getRezerwacje() {
        return rezerwacje;
    }

    public void setRezerwacje(List<rezerwacje> rezerwacje) {
        this.rezerwacje = rezerwacje;
    }

    public List<dane_logowania> getDaneLogowania() {
        return daneLogowania;
    }

    public void setDaneLogowania(List<dane_logowania> daneLogowania) {
        this.daneLogowania = daneLogowania;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class rodzaj_przedmiotu {

        private Integer idRodzaj;
        private String nazwaTypu;

        public Integer getIdRodzaj() {
            return idRodzaj;
        }

        public void setIdRodzaj(Integer idRodzaj) {
            this.idRodzaj = idRodzaj;
        }

        public String getNazwaTypu() {
            return nazwaTypu;
        }

        public void setNazwaTypu(String nazwaTypu) {
            this.nazwaTypu = nazwaTypu;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class przedmioty {

        private Integer idPrzedmiotu;
        private String nazwa;
        private Integer idRodzaj;
        private Integer ilosc;
        private String status;

        public Integer getIdPrzedmiotu() {
            return idPrzedmiotu;
        }

        public void setIdPrzedmiotu(Integer idPrzedmiotu) {
            this.idPrzedmiotu = idPrzedmiotu;
        }

        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        public Integer getIdRodzaj() {
            return idRodzaj;
        }

        public void setIdRodzaj(Integer idRodzaj) {
            this.idRodzaj = idRodzaj;
        }

        public Integer getIlosc() {
            return ilosc;
        }

        public void setIlosc(Integer ilosc) {
            this.ilosc = ilosc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class uzytkownicy {

        private Integer idUzytkownika;
        private Integer idUprawnienia;
        private String imie;
        private String nazwisko;
        private String numerTel;
        private String email;
        private String pesel;

        public Integer getIdUzytkownika() {
            return idUzytkownika;
        }

        public void setIdUzytkownika(Integer idUzytkownika) {
            this.idUzytkownika = idUzytkownika;
        }

        public Integer getIdUprawnienia() {
            return idUprawnienia;
        }

        public void setIdUprawnienia(Integer idUprawnienia) {
            this.idUprawnienia = idUprawnienia;
        }

        public String getImie() {
            return imie;
        }

        public void setImie(String imie) {
            this.imie = imie;
        }

        public String getNazwisko() {
            return nazwisko;
        }

        public void setNazwisko(String nazwisko) {
            this.nazwisko = nazwisko;
        }

        public String getNumerTel() {
            return numerTel;
        }

        public void setNumerTel(String numerTel) {
            this.numerTel = numerTel;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPesel() {
            return pesel;
        }

        public void setPesel(String pesel) {
            this.pesel = pesel;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class rezerwacje {

        private Integer idRezerwacji;
        private Integer idUzytkownika;
        private Integer idPrzedmiotu;
        private Date odKiedy;
        private Date doKiedy;
        private Integer ilosc;

        public Integer getIdRezerwacji() {
            return idRezerwacji;
        }

        public void setIdRezerwacji(Integer idRezerwacji) {
            this.idRezerwacji = idRezerwacji;
        }

        public Integer getIdUzytkownika() {
            return idUzytkownika;
        }

        public void setIdUzytkownika(Integer idUzytkownika) {
            this.idUzytkownika = idUzytkownika;
        }

        public Integer getIdPrzedmiotu() {
            return idPrzedmiotu;
        }

        public void setIdPrzedmiotu(Integer idPrzedmiotu) {
            this.idPrzedmiotu = idPrzedmiotu;
        }

        public Date getOdKiedy() {
            return odKiedy;
        }

        public void setOdKiedy(Date odKiedy) {
            this.odKiedy = odKiedy;
        }

        public Date getDoKiedy() {
            return doKiedy;
        }

        public void setDoKiedy(Date doKiedy) {
            this.doKiedy = doKiedy;
        }

        public Integer getIlosc() {
            return ilosc;
        }

        public void setIlosc(Integer ilosc) {
            this.ilosc = ilosc;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class dane_logowania {

        private Integer idKonta;
        private String login;
        private String haslo;
        private Integer passCounter;

        public Integer getIdKonta() {
            return idKonta;
        }

        public void setIdKonta(Integer idKonta) {
            this.idKonta = idKonta;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getHaslo() {
            return haslo;
        }

        public void setHaslo(String haslo) {
            this.haslo = haslo;
        }

        public Integer getPassCounter() {
            return passCounter;
        }

        public void setPassCounter(Integer passCounter) {
            this.passCounter = passCounter;
        }

    }

}
