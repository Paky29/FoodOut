package model.turno;

import java.sql.Time;

public class Turno {
    public Turno(){}

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public Time getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Time getOraFine() {
        return oraFine;
    }

    public void setOraFine(Time oraFine) {
        this.oraFine = oraFine;
    }

    private String giorno;
    private Time oraInizio;
    private Time oraFine;
}
