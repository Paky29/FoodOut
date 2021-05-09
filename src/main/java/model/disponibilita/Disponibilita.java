package model.disponibilita;

import java.sql.Time;
import java.time.LocalTime;

public class Disponibilita {

    public Disponibilita(){}

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public Time getOraApertura() {
        return oraApertura;
    }

    public void setOraApertura(Time oraApertura) {
        this.oraApertura = oraApertura;
    }

    public Time getOraChiusura() {
        return oraChiusura;
    }

    public void setOraChiusura(Time oraChiusura) {
        this.oraChiusura = oraChiusura;
    }

    private String giorno;
    private Time oraApertura;
    private Time oraChiusura;
}
