package model.ordine;

public class OrdineItem {
    public OrdineItem(){}

    public Offerta getOff() {
        return off;
    }

    public void setOff(Offerta off) {
        this.off = off;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Offerta off;
    private int quantita;
}
