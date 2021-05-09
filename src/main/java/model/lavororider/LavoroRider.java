package model.lavororider;

import java.sql.Time;

public class LavoroRider {
    public LavoroRider(){}

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
