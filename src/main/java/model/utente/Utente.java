package model.utente;

import model.ordine.Ordine;
import model.ristorante.Ristorante;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Utente {

    public Utente(){
        ordini=new ArrayList<>();
        ristorantiPref=new ArrayList<>();
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

    public String getNome() { return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() { return cognome; }

    public void setCognome(String cognome) {
        this.cognome = cognome;
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
        try{
        MessageDigest digest=MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(password.getBytes(StandardCharsets.UTF_8));
        this.password = String.format("%040x",new BigInteger(1,digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
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

    public void setVia(String via) { this.via = via; }

    public String getInteresse() {
        return interesse;
    }

    public void setInteresse(String interesse) {
        this.interesse = interesse;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public boolean isAmministratore() { return amministratore; }

    public void setAmministratore(boolean amministratore) { this.amministratore = amministratore; }

    public ArrayList<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(ArrayList<Ordine> ordini) { this.ordini = ordini; }

    public ArrayList<Ristorante> getRistorantiPref() {
        return ristorantiPref;
    }

    public void setRistorantiPref(ArrayList<Ristorante> ristorantiPref) {
        this.ristorantiPref = ristorantiPref;
    }

    private int codice, civico;
    private String nome, cognome, email, password, provincia, citta, via, interesse;
    private float saldo;
    private boolean amministratore;
    private ArrayList<Ordine> ordini;
    private ArrayList<Ristorante> ristorantiPref;
}
