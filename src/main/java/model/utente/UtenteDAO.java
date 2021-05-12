package model.utente;

import model.ordine.Ordine;
import model.ordine.OrdineExtractor;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.utility.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {
    public UtenteDAO(){}

    public Utente doRetrieveByEmailAndPassword(String email, String password) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceUtente, nome, cognome, email, pw, saldo, provincia, citta, via, civico, interesse, amministratore, codiceOrdine, dataOrdine, totale, nota, oraPartenza, oraArrivo, metodoPagamento, giudizio, voto, consegnato FROM Utente u INNER JOIN Ordine o ON o.codUtente_fk=u.codiceUtente WHERE email=? AND pw=SHA1(?)");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            Utente u=null;
            if(rs.next()){
                u=UtenteExtractor.extract(rs);
                do{
                    Ordine o= OrdineExtractor.extract(rs);
                    u.getOrdini().add(o);
                }while(rs.next());
            }

            ps=conn.prepareStatement("SELECT codRis_fk FROM Preferenza pr WHERE pr.codUtente_fk=?");
            ps.setInt(1, u.getCodice());
            rs=ps.executeQuery();
            RistoranteDAO service=new RistoranteDAO();
            while(rs.next()){
                Ristorante r=service.doRetrieveById(rs.getInt("codRis_fk"));
                u.getRistorantiPref().add(r);
            }

            return u;

        }
    }


}
