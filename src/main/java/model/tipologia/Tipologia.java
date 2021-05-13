package model.tipologia;

public class Tipologia {

    public Tipologia(){}

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) { this.descrizione=descrizione; }

    private String nome, descrizione;

}
