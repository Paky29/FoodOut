package model.utente;

import model.disponibilita.Disponibilita;
import model.disponibilita.DisponibilitaExtractor;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteExtractor;
import model.tipologia.Tipologia;
import model.utility.ConPool;
import model.utility.Paginator;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

//cambiare i do retrieve dividendo la query utente-ordini
public class UtenteDAO {
    public UtenteDAO(){}

    public Utente doRetrieveById(int codiceUtente) throws SQLException{
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT u.codiceUtente, u.nome, u.cognome, u.email, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, r.valido, t.nome, t.descrizione FROM Utente u LEFT JOIN Preferenza p ON u.codiceUtente=p.codUtente_fk LEFT JOIN Ristorante r ON p.codRis_fk=r.codiceRistorante LEFT JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk LEFT JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE u.codiceUtente=?");
            ps.setInt(1, codiceUtente);
            ResultSet rs = ps.executeQuery();
            Utente u=null;
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            OrdineDAO service=new OrdineDAO();
            if(rs.next()){
                u=UtenteExtractor.extract(rs);
                do{
                    int codiceRistorante=rs.getInt("r.codiceRistorante");
                    if(!ristoranti.containsKey(codiceRistorante)){
                        Ristorante r=RistoranteExtractor.extract(rs);
                        ristoranti.put(codiceRistorante, r);
                    }

                    Tipologia t = new Tipologia();
                    t.setNome(rs.getString("t.nome"));
                    t.setDescrizione(rs.getString("t.descrizione"));
                    ristoranti.get(codiceRistorante).getTipologie().add(t);
                }while(rs.next());

                if(!ristoranti.isEmpty())
                    u.setRistorantiPref(new ArrayList<>(ristoranti.values()));

                ArrayList<Ordine> ordini=service.doRetrieveByUtente(u);
                u.setOrdini(ordini);

            }

            return u;
        }
    }

    public Utente doRetrieveByEmailAndPassword(String email, String password) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT u.codiceUtente, u.nome, u.cognome, u.email, u.pw, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, r.valido, t.nome, t.descrizione FROM Utente u LEFT JOIN Preferenza p ON u.codiceUtente=p.codUtente_fk LEFT JOIN Ristorante r ON p.codRis_fk=r.codiceRistorante LEFT JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk LEFT JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE u.email=? AND u.pw=SHA1(?)");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            Utente u=null;
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            OrdineDAO service=new OrdineDAO();
            if(rs.next()){
                u=UtenteExtractor.extract(rs);
                u.setPassword(rs.getString("u.pw"));
                do{
                    int codiceRistorante=rs.getInt("r.codiceRistorante");
                    if(!ristoranti.containsKey(codiceRistorante)){
                        Ristorante r=RistoranteExtractor.extract(rs);
                        ristoranti.put(codiceRistorante, r);
                    }

                    Tipologia t = new Tipologia();
                    t.setNome(rs.getString("t.nome"));
                    t.setDescrizione(rs.getString("t.descrizione"));
                    ristoranti.get(codiceRistorante).getTipologie().add(t);
                }while(rs.next());

                if(!ristoranti.isEmpty())
                    u.setRistorantiPref(new ArrayList<>(ristoranti.values()));

               /* ArrayList<Ordine> ordini=service.doRetrieveByUtente(u);
                u.setOrdini(ordini);*/
            }

            return u;
        }
    }

    public ArrayList<Utente> doRetrieveAll(Paginator paginator) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT u.codiceUtente, u.nome, u.cognome, u.email, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore FROM Utente u LIMIT ?,?");
            ps.setInt(1,paginator.getOffset());
            ps.setInt(2,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            ArrayList utenti=new ArrayList();
            while(rs.next()){
                    Utente u = UtenteExtractor.extract(rs);
                    utenti.add(u);
            }
            if(utenti.isEmpty())
                return null;
            return utenti;
        }
    }

    public ArrayList<Utente> doRetrievebyPref(int codiceRistorante, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT u.codiceUtente, u.nome, u.cognome, u.email, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore FROM Utente u INNER JOIN Preferenza p ON p.codUtente_fk=u.codiceUtente WHERE p.codRis_fk=? LIMIT ?,?");
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

    public ArrayList<Ristorante> doRetrievebyUtentePref(int codiceUtente, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, r.valido FROM Preferenza p INNER JOIN Ristorante r ON p.codRis_fk=r.codiceRistorante WHERE p.codUtente_fk= ? LIMIT ?,?");
            ps.setInt(1,codiceUtente);
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int codiceRistorante=rs.getInt("r.codiceRistorante");
                Ristorante r=RistoranteExtractor.extract(rs);
                ristoranti.put(codiceRistorante, r);
            }

            if(ristoranti.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: ristoranti.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement disp=conn.prepareStatement("SELECT d.codRis_fk, d.giorno, d.oraApertura, d.oraChiusura FROM Disponibilita d WHERE d.codRis_fk IN"+sj.toString());
            ResultSet setDisp=disp.executeQuery();
            while(setDisp.next()){
                int codiceRistorante=setDisp.getInt("d.codRis_fk");
                Disponibilita d=DisponibilitaExtractor.extract(setDisp);
                ristoranti.get(codiceRistorante).getGiorni().add(d);
            }

            PreparedStatement tip=conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN" + sj.toString());
            ResultSet setTip=tip.executeQuery();

            while(setTip.next()){
                int codiceRistorante=setTip.getInt("art.codRis_fk");
                Tipologia t=new Tipologia();
                t.setNome(setTip.getString("t.nome"));
                t.setDescrizione(setTip.getString("t.descrizione"));
                ristoranti.get(codiceRistorante).getTipologie().add(t);
            }

            if(ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());
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
            PreparedStatement ps = conn.prepareStatement("UPDATE Utente SET nome=?, cognome=?, email=?, saldo=?, provincia=?, citta=?, via=?, civico=?, interesse=?, amministratore=? WHERE codiceUtente=?");
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getEmail());
            ps.setFloat(4, u.getSaldo());
            ps.setString(5, u.getProvincia());
            ps.setString(6, u.getCitta());
            ps.setString(7, u.getVia());
            ps.setInt(8, u.getCivico());
            ps.setString(9, u.getInteresse());
            ps.setInt(11, u.getCodice());
            if (u.getEmail().contains("@foodout.com")) {
                ps.setBoolean(10, true);
                u.setAmministratore(true);
            } else {
                ps.setBoolean(10, false);
                u.setAmministratore(false);
            }

            if (ps.executeUpdate() != 1)
                return false;
            else
                return true;
        }
    }

    public boolean doUpdatePw(Utente u) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE Utente SET pw=? WHERE codiceUtente=?");
            ps.setString(1, u.getPassword());
            ps.setInt(2,u.getCodice());
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

    public int countRistPref(Utente u) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps=conn.prepareStatement("SELECT count(*) as numRist FROM Preferenza p INNER JOIN Ristorante r ON p.codRis_fk=r.codiceRistorante WHERE p.codUtente_fk=?");
            ps.setInt(1,u.getCodice());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

    public int countAll() throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numUtenti FROM Utente u");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numUtenti");
            }
            else
                return 0;
        }
    }
}
