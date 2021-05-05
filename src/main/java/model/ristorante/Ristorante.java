package model.ristorante;

import model.giorno.Giorno;
import model.prodotto.Prodotto;
import model.tipologia.Tipologia;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
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

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
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

    public ArrayList<String> getImmagini() {
        return immagini;
    }

    public void setImmagini(ArrayList<String> immagini) {
        this.immagini = immagini;
    }

    private int codice, civico;
    private String nome, provincia, citta, via, info;
    private float spesaMinima, tassoConsegna;
    private ArrayList<Prodotto> prodotti;
    private ArrayList<Giorno> giorni;
    private ArrayList<String> immagini;
    private Tipologia tipologia;
}
