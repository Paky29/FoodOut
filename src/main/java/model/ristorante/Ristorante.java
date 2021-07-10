package model.ristorante;

import model.disponibilita.Disponibilita;
import model.prodotto.Prodotto;
import model.tipologia.Tipologia;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Ristorante {

    public Ristorante(){
        prodotti=new ArrayList<>();
        giorni=new ArrayList<>();
        tipologie=new ArrayList<>();
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public int getCivico() {
        return civico;
    }

    public void setCivico(int civico) {
        this.civico = civico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getSpesaMinima() {
        return spesaMinima;
    }

    public void setSpesaMinima(float spesaMinima) {
        this.spesaMinima = spesaMinima;
    }

    public float getTassoConsegna() {
        return tassoConsegna;
    }

    public void setTassoConsegna(float tassoConsegna) {
        this.tassoConsegna = tassoConsegna;
    }

    public ArrayList<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public ArrayList<Disponibilita> getGiorni() {
        return giorni;
    }

    public void setGiorni(ArrayList<Disponibilita> giorni) {
        this.giorni = giorni;
    }

    public ArrayList<Tipologia> getTipologie() { return tipologie; }

    public void setTipologie(ArrayList<Tipologia> tipologie) { this.tipologie = tipologie; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public String getUrlImmagine() {
        return urlImmagine;
    }

    public void setUrlImmagine(String urlImmagine) {
        this.urlImmagine=urlImmagine;
    }

    public boolean isValido() { return valido; }

    public void setValido(boolean valido) { this.valido = valido; }

    public boolean isOpen(LocalDateTime ldt){
        boolean isOpen=false;
        DayOfWeek dw=ldt.getDayOfWeek();
        String giorno=Disponibilita.giorni[dw.getValue()-1];
        LocalTime ora=ldt.toLocalTime();
        for(Disponibilita d:giorni){
            if(d.getGiorno().equalsIgnoreCase(giorno) && (d.getOraApertura().isBefore(ora) || d.getOraApertura().equals(ora)) && (d.getOraChiusura().isAfter(ora) || d.getOraChiusura().equals(ora))){
                isOpen=true;
                break;
            }
        }
        return isOpen;
    }

    private int codice, civico;
    private String nome, provincia, citta, via, info;
    private float spesaMinima, tassoConsegna;
    private ArrayList<Prodotto> prodotti;
    private ArrayList<Disponibilita> giorni;
    private ArrayList<Tipologia> tipologie;
    private int rating;
    private String urlImmagine;
    private boolean valido;
}
