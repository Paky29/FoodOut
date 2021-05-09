package model.immagine;

import model.ristorante.Ristorante;

public class Immagine {
    public Immagine(){};


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPrincipale() {
        return principale;
    }

    public void setPrincipale(boolean principale) {
        this.principale = principale;
    }

    private boolean principale;
    private String url;


}
