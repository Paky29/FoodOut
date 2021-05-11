package model.rider;

import model.turno.Turno;
import model.ordine.Ordine;

import java.util.ArrayList;

public class Rider {

    public Rider(){
        ordini=new ArrayList<>();
        giorni=new ArrayList<>();
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(String veicolo) {
        this.veicolo = veicolo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public ArrayList<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(ArrayList<Ordine> ordini) {
        this.ordini = ordini;
    }

    public ArrayList<Turno> getGiorni() {
        return giorni;
    }

    public void setGiorni(ArrayList<Turno> giorni) {
        this.giorni = giorni;
    }

    private int codice;
    private String email, password, veicolo, citta;
    private ArrayList<Ordine> ordini;
    private ArrayList<Turno> giorni;
}
