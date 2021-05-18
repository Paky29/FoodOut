package model.disponibilita;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Objects;

public class Disponibilita {

    public Disponibilita(){}

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disponibilita that = (Disponibilita) o;
        return Objects.equals(giorno, that.giorno) && Objects.equals(oraApertura, that.oraApertura) && Objects.equals(oraChiusura, that.oraChiusura);
    }

    private String giorno;
    private LocalTime oraApertura;
    private LocalTime oraChiusura;
}
