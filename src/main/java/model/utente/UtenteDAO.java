package model.utente;

import model.disponibilita.Disponibilita;
import model.disponibilita.DisponibilitaExtractor;
import model.ordine.Ordine;
import model.ordine.OrdineExtractor;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.ristorante.RistoranteExtractor;
import model.tipologia.Tipologia;
import model.utility.ConPool;
import model.utility.Paginator;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

//cambiare i do retrieve dividendo la query utente-ordini
public class UtenteDAO {
    public UtenteDAO(){}

    public Utente doRetrieveById(int codiceUtente) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceUtente, nome, cognome, email, pw, saldo, provincia, citta, via, civico, interesse, amministratore FROM Utente u WHERE codiceUtente=?");
            ps.setInt(1, codiceUtente);
            ResultSet rs = ps.executeQuery();
            Utente u=null;
            if(rs.next()){
                u=UtenteExtractor.extract(rs);
                //togliere?
                ps=conn.prepareStatement("SELECT codiceOrdine, dataOrdine, totale, nota, oraPartenza, oraArrivo, metodoPagamento, giudizio, voto, consegnato FROM Ordine o WHERE o.codUtente_fk=? ");
                ps.setInt(1, u.getCodice());
                rs=ps.executeQuery();
                while(rs.next()){
                    Ordine o= OrdineExtractor.extract(rs);
                    u.getOrdini().add(o);
                }
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

    public Utente doRetrieveByEmailAndPassword(String email, String password) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceUtente, nome, cognome, email, pw, saldo, provincia, citta, via, civico, interesse, amministratore FROM Utente u WHERE email=? AND pw=SHA1(?)");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            Utente u=null;
            if(rs.next()){
                u=UtenteExtractor.extract(rs);
                ps=conn.prepareStatement("SELECT codiceOrdine, dataOrdine, totale, nota, oraPartenza, oraArrivo, metodoPagamento, giudizio, voto, consegnato FROM Ordine o WHERE o.codUtente_fk=? ");
                ps.setInt(1, u.getCodice());
                rs=ps.executeQuery();
                while(rs.next()){
                    Ordine o= OrdineExtractor.extract(rs);
                    u.getOrdini().add(o);
                }
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

    public ArrayList<Utente> doRetrieveAll(Paginator paginator) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceUtente, nome, cognome, email, saldo, provincia, citta, via, civico, interesse, amministratore, codiceOrdine, dataOrdine, totale, nota, oraPartenza, oraArrivo, metodoPagamento, giudizio, voto, consegnato FROM Utente u INNER JOIN Ordine o ON o.codUtente_fk=u.codiceUtente LIMIT ?,?");
            ps.setInt(1,paginator.getOffset());
            ps.setInt(2,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Utente> utenti=new LinkedHashMap<>();
            while(rs.next()){
                int utenteid=rs.getInt("u.codiceUtente");
                if(!utenti.containsKey(utenteid)) {
                    Utente u = UtenteExtractor.extract(rs);
                    PreparedStatement ordini=conn.prepareStatement("SELECT codiceOrdine, dataOrdine, totale, nota, oraPartenza, oraArrivo, metodoPagamento, giudizio, voto, consegnato FROM Ordine o WHERE o.codUtente_fk=? ");
                    ps.setInt(1, utenteid);
                    ResultSet set=ordini.executeQuery();
                    while(set.next())
                    {
                        Ordine o=OrdineExtractor.extract(set);
                        u.getOrdini().add(o);
                    }
                    PreparedStatement preferenze=conn.prepareStatement("SELECT codRis_fk FROM Preferenza pr WHERE pr.codUtente_fk=?");
                    preferenze.setInt(1,utenteid);
                    set=preferenze.executeQuery();
                    RistoranteDAO service=new RistoranteDAO();
                    while(set.next())
                    {
                        Ristorante r= service.doRetrieveById(set.getInt("codRis_fk"));
                        u.getRistorantiPref().add(r);
                    }
                    utenti.put(utenteid, u);
                }
                Ordine o=OrdineExtractor.extract(rs);
                utenti.get(utenteid).getOrdini().add(o);
            }
            if(utenti.isEmpty())
                return null;
            return new ArrayList<>(utenti.values());
        }
    }

    public ArrayList<Utente> doRetrievebyPref(int codiceRistorante, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceUtente, nome, cognome, email, saldo, provincia, citta, via, civico, interesse, amministratore FROM Utente u INNER JOIN Preferenza o ON p.codUtente_fk=u.codiceUtente WHERE p.codRis_fk=? LIMIT ?,?");
            ps.setInt(1, codiceRistorante);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            ArrayList<Utente> utenti=new ArrayList<>();
            while(rs.next()){
                Utente u=UtenteExtractor.extract(rs);
                utenti.add(u);
            }
            if(utenti.isEmpty())
                return null;
            return utenti;
        }

    }

    public ArrayList<Utente> doRetrievebyCitta(String citta, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceUtente, nome, cognome, email, saldo, provincia, citta, via, civico, interesse, amministratore FROM Utente u  WHERE citta=? LIMIT ?,?");
            ps.setString(1, citta);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            ArrayList<Utente> utenti=new ArrayList<>();
            while(rs.next()){
                Utente u=UtenteExtractor.extract(rs);
                utenti.add(u);
            }
            if(utenti.isEmpty())
                return null;
            return utenti;
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
            PreparedStatement ps = conn.prepareStatement("UPDATE Utente SET nome=?, cognome=?, email=?, pw=?, saldo=?, provincia=?, citta=?, via=?, civico=?, interesse=?, amministratore=? WHERE codiceUtente=?");
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
            ps.setInt(11, u.getCodice());
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
