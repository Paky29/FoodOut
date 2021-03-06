package model.utility;

import model.utente.Utente;

public class UtenteSession {
    private final String nome, cognome;
    private final int id;
    private final boolean admin;

    public UtenteSession(Utente u){
        this.nome=u.getNome();
        this.cognome=u.getCognome();
        this.id=u.getCodice();
        this.admin=u.isAmministratore();
    }

    public String getNome(){
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getId() {
        return id;
    }

    public boolean isAdmin() {
        return admin;
    }
}
