package model.utente;

import model.ordine.Ordine;
import model.ordine.OrdineExtractor;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.utility.ConPool;

import java.sql.*;

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

    public boolean doSave(Utente u) throws SQLException{
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Utente(nome, cognome, email, pw, saldo, provincia, citta, via, civico, interesse, amministratore) VALUES(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setFloat(5, u.getSaldo());
            ps.setString(6, u.getProvincia());
            ps.setString(7, u.getCitta());
            ps.setString(8, u.getVia());
            ps.setInt(9, u.getCivico());
            ps.setString(10, u.getInteresse());
            if(u.getEmail().contains("@foodout.com")) {
                ps.setBoolean(11, true);
                u.setAmministratore(true);
            }
            else {
                ps.setBoolean(11, false);
                u.setAmministratore(false);
            }

            if(ps.executeUpdate()!=1){
                return false;
            }
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            u.setCodice(id);
            return true;
        }
    }

    public boolean doUpdate(Utente u) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE Utente SET nome=?, cognome=?, email=?, pw=?, saldo=?, provincia=?, citta=?, via=?, civico=?, interesse=?, amministratore=?");
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setFloat(5, u.getSaldo());
            ps.setString(6, u.getProvincia());
            ps.setString(7, u.getCitta());
            ps.setString(8, u.getVia());
            ps.setInt(9, u.getCivico());
            ps.setString(10, u.getInteresse());
            if (u.getEmail().contains("@foodout.com")) {
                ps.setBoolean(11, true);
                u.setAmministratore(true);
            } else {
                ps.setBoolean(11, false);
                u.setAmministratore(false);
            }

            if (ps.executeUpdate() != 1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete (int codice) throws SQLException{
            try(Connection conn=ConPool.getConnection()){
                PreparedStatement ps=conn.prepareStatement("DELETE FROM Utente WHERE codiceUtente=?");
                ps.setInt(1, codice);
                if(ps.executeUpdate()!=1)
                    return false;
                else
                    return true;
            }
    }


}
