package model.utente;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteExtractor {
    public static Utente extract(ResultSet set) throws SQLException {
        Utente u=new Utente();
        u.setCodice(set.getInt("u.codiceUtente"));
        u.setNome(set.getString("u.nome"));
        u.setCognome(set.getString("u.cognome"));
        u.setSaldo(set.getFloat("u.saldo"));
        u.setEmail(set.getString("u.email"));
        u.setPassword(set.getString("u.pw"));
        u.setProvincia(set.getString("u.provincia"));
        u.setCitta(set.getString("u.citta"));
        u.setVia(set.getString("u.via"));
        u.setCivico(set.getInt("u.civico"));
        u.setInteresse(set.getString("u.interesse"));
        u.setAmministratore(set.getBoolean("u.amministratore"));
        return u;
    }
}
