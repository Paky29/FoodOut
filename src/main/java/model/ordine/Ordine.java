package model.ordine;

import model.rider.Rider;
import model.ristorante.Ristorante;
import model.utente.Utente;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;

public class Ordine {

    public Ordine(){
        ordineItems=new ArrayList<>();
    }

    public ArrayList<OrdineItem> getOrdineItems() {
        return ordineItems;
    }

    public void setOrdineItems(ArrayList<OrdineItem> ordineItems) {
        this.ordineItems = ordineItems;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getGiudizio() {
        return giudizio;
    }

    public void setGiudizio(String giudizio) { this.giudizio = giudizio; }

    public LocalTime getOraPartenza() {
        return oraPartenza;
    }

    public void setOraPartenza(LocalTime oraPartenza) {
        this.oraPartenza = oraPartenza;
    }

    public LocalTime getOraArrivo() {
        return oraArrivo;
    }

    public void setOraArrivo(LocalTime oraArrivo) {
        this.oraArrivo = oraArrivo;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public boolean isConsegnato() {
        return consegnato;
    }

    public void setConsegnato(boolean consegnato) {
        this.consegnato = consegnato;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Ristorante getRistorante() { return ristorante; }

    public void setRistorante(Ristorante ristorante) { this.ristorante = ristorante; }

    public boolean removeOrdineItem(int id, String tipo){
        int pos=-1;
        int i=0;
            for(OrdineItem oi : ordineItems) {
                if (oi.getClass().getName().contains(tipo) && oi.getOff().getCodice() == id)
                    pos = i;
                i++;
            }
            if(pos!=-1) {
                ordineItems.remove(pos);
                return true;
            }
            else
                return false;


    }

    private int codice,voto;
    private LocalDate dataOrdine;
    private float totale;
    private String nota,metodoPagamento,giudizio;
    private LocalTime oraPartenza, oraArrivo;
    private Rider rider;
    private Utente utente;
    private boolean consegnato;
    private ArrayList<OrdineItem> ordineItems;
    private Ristorante ristorante;
}
