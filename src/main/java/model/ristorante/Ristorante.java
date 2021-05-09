package model.ristorante;

import model.giorno.Giorno;
import model.immagine.Immagine;
import model.prodotto.Prodotto;
import java.util.ArrayList;

public class Ristorante {

    public Ristorante(){
        prodotti=new ArrayList<>();
        giorni=new ArrayList<>();
        immagini=new ArrayList<>();
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

    public java.lang.String getNome() {
        return nome;
    }

    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }

    public java.lang.String getProvincia() {
        return provincia;
    }

    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }

    public java.lang.String getCitta() {
        return citta;
    }

    public void setCitta(java.lang.String citta) {
        this.citta = citta;
    }

    public java.lang.String getVia() {
        return via;
    }

    public void setVia(java.lang.String via) {
        this.via = via;
    }

    public java.lang.String getInfo() {
        return info;
    }

    public void setInfo(java.lang.String info) {
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

    public ArrayList<String> getTipologia() {
        return tipologia;
    }

    public void setTipologia(ArrayList<String> tipologia) {
        this.tipologia = tipologia;
    }

    public ArrayList<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public ArrayList<Giorno> getGiorni() {
        return giorni;
    }

    public void setGiorni(ArrayList<Giorno> giorni) {
        this.giorni = giorni;
    }

    public ArrayList<Immagine> getImmagini() {
        return immagini;
    }

    public void setImmagini(ArrayList<Immagine> immagini) {
        this.immagini = immagini;
    }

    private int codice, civico;
    private java.lang.String nome, provincia, citta, via, info;
    private float spesaMinima, tassoConsegna;
    private ArrayList<Prodotto> prodotti;
    private ArrayList<Giorno> giorni;
    private ArrayList<Immagine> immagini;
    private ArrayList<String> tipologia;
}
