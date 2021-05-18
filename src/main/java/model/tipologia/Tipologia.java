package model.tipologia;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tipologia tipologia = (Tipologia) o;
        return Objects.equals(nome, tipologia.nome) && Objects.equals(descrizione, tipologia.descrizione);
    }

}
