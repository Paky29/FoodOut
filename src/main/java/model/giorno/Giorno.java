package model.giorno;

import java.time.LocalTime;

public class Giorno {

    public Giorno(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalTime getOraApertura() {
        return oraApertura;
    }

    public void setOraApertura(LocalTime oraApertura) {
        this.oraApertura = oraApertura;
    }

    public LocalTime getOraChiusura() {
        return oraChiusura;
    }

    public void setOraChiusura(LocalTime oraChiusura) {
        this.oraChiusura = oraChiusura;
    }

    private String nome;
    private LocalTime oraApertura;
    private LocalTime oraChiusura;
}
