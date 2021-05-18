package model.ristorante;

import model.disponibilita.Disponibilita;
import model.disponibilita.DisponibilitaExtractor;
import model.tipologia.Tipologia;
import model.utility.ConPool;
import model.utility.Paginator;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class RistoranteDAO {
    public RistoranteDAO(){}

    public Ristorante doRetrieveById(int codice) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, t.nome, t.descrizione FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE r.codiceRistorante=?");
            ps.setInt(1, codice);
            ResultSet rs = ps.executeQuery();
            Ristorante r = null;
            if(rs.next()) {
                r = RistoranteExtractor.extract(rs);
                do {
                    Tipologia t = new Tipologia();
                    t.setNome(rs.getString("t.nome"));
                    t.setDescrizione(rs.getString("t.descrizione"));
                    r.getTipologie().add(t);
                } while (rs.next());

                PreparedStatement calendario = conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                calendario.setInt(1, codice);
                rs = calendario.executeQuery();
                while (rs.next()) {
                    Disponibilita d = DisponibilitaExtractor.extract(rs);
                    r.getGiorni().add(d);
                }
            }

            return r;
        }
    }

//da visionare
    public ArrayList<Ristorante> doRetrievebyScontoDisp(String citta, Paginator paginator) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t.nome, t.descrizione FROM Ristorante r INNER JOIN Prodotto p ON p.codRis_fk=r.codiceRistorante INNER JOIN Tipologia t ON p.nomeTip_fk=t.nome LEFT JOIN AppartenenzaPM apm ON p.codiceProdotto=apm.codProd_fk INNER JOIN Menu m ON apm.codMenu_fk=m.codiceMenu WHERE r.citta=? AND (p.sconto>0 OR m.sconto>0) LIMIT ?,?");
            ps.setString(1,citta);
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                int codiceRistorante=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(codiceRistorante)){
                    Ristorante r=RistoranteExtractor.extract(rs);
                    ristoranti.put(codiceRistorante, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                Ristorante ris_tip=ristoranti.get(codiceRistorante);
                if(!ris_tip.getTipologie().contains(t))
                    ris_tip.getTipologie().add(t);
            }

            if(ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());

        }
    }

    public ArrayList<Ristorante> doRetrieveByTipologiaCitta(String nomeTipologia, String citta, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, giorno, oraApertura, oraChiusura FROM Ristorante r INNER JOIN Disponibilita d ON d.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE art.nomeTip_fk=? AND r.citta=? LIMIT ?,?");
            ps.setString(1, nomeTipologia);
            ps.setString(2,citta);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement tip=conn.prepareStatement("SELECT t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk=?");
                    tip.setInt(1,ristoranteid);
                    ResultSet set=tip.executeQuery();
                    while(set.next()){
                        Tipologia t=new Tipologia();
                        t.setNome(set.getString("t.nome"));
                        t.setDescrizione(set.getString("t.descrizione"));
                        r.getTipologie().add(t);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Disponibilita d=DisponibilitaExtractor.extract(rs);
                ristoranti.get(ristoranteid).getGiorni().add(d);
            }
            if(ristoranti.isEmpty())
                return null;
            return new ArrayList<>(ristoranti.values());
        }
    }

    //dobbiamo decidere se è meglio città o provincia
    public ArrayList<Ristorante> doRetrieveByCitta(String citta, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, t.nome, t.descrizione FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE r.citta=? LIMIT ?,?" );
            ps.setString(1, citta);
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                    calendario.setInt(1,ristoranteid);
                    ResultSet set=calendario.executeQuery();
                    while(set.next())
                    {
                        Disponibilita d=DisponibilitaExtractor.extract(set);
                        r.getGiorni().add(d);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ristoranti.get(ristoranteid).getTipologie().add(t);
            }
            if(ristoranti.isEmpty())
                return null;
            return new ArrayList<>(ristoranti.values());
        }
    }

    // restituisce i ristoranti con un tasso di consegna inferiore o uguale a quello inserito
    public ArrayList<Ristorante> doRetrieveByTassoConsegna(float tasso, String citta, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, t.nome, t.descrizione FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome  WHERE r.tassoConsegna<=? AND r.citta=? LIMIT ?,?" );
            ps.setFloat(1, tasso);
            ps.setString(2,citta);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                    calendario.setInt(1,ristoranteid);
                    ResultSet set=calendario.executeQuery();
                    while(set.next())
                    {
                        Disponibilita d=DisponibilitaExtractor.extract(set);
                        r.getGiorni().add(d);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ristoranti.get(ristoranteid).getTipologie().add(t);
            }
            if(ristoranti.isEmpty())
                return null;
            return new ArrayList<>(ristoranti.values());
        }
    }

    //in base alla città dell'utente e al nome del ristorante inserito
    public ArrayList<Ristorante> doRetrieveByNome(String citta, String nome, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, t.nome, t.descrizione FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE r.nome=? AND r.citta=? LIMIT ?,?" );
            ps.setString(1, citta);
            ps.setString(2, nome);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                    calendario.setInt(1,ristoranteid);
                    ResultSet set=calendario.executeQuery();
                    while(set.next())
                    {
                        Disponibilita d=DisponibilitaExtractor.extract(set);
                        r.getGiorni().add(d);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ristoranti.get(ristoranteid).getTipologie().add(t);
            }
            if(ristoranti.isEmpty())
                return null;
            return new ArrayList<>(ristoranti.values());
        }
    }

    public ArrayList<Ristorante> doRetrieveAll(Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, art.nomeTip_fk FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome LIMIT ?,?" );
            ps.setInt(1,paginator.getOffset());
            ps.setInt(2,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                    calendario.setInt(1,ristoranteid);
                    ResultSet set=calendario.executeQuery();
                    while(set.next())
                    {
                        Disponibilita d=DisponibilitaExtractor.extract(set);
                        r.getGiorni().add(d);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ristoranti.get(ristoranteid).getTipologie().add(t);
            }
            if(ristoranti.isEmpty())
                return null;
            return new ArrayList<>(ristoranti.values());
        }
    }

    public boolean doSave(Ristorante r) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Ristorante (nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating) VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,r.getNome());
            ps.setString(2,r.getProvincia());
            ps.setString(3,r.getCitta());
            ps.setString(4,r.getVia());
            ps.setInt(5,r.getCivico());
            ps.setString(6,r.getInfo());
            ps.setFloat(7,r.getSpesaMinima());
            ps.setFloat(8,r.getTassoConsegna());
            ps.setString(9,r.getUrlImmagine());
            ps.setInt(10,r.getRating());
            if(ps.executeUpdate()!=1){
               return false;
            }
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            r.setCodice(id);
            return true;
        }
    }

    public boolean doUpdate(Ristorante r) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Ristorante SET nome=?, provincia=?, citta=?, via=?, civico=?, info=?, spesaMinima=?, tassoConsegna=?, urlImmagine=?, rating=? WHERE codiceRistorante=?");
            ps.setString(1,r.getNome());
            ps.setString(2,r.getProvincia());
            ps.setString(3,r.getCitta());
            ps.setString(4,r.getVia());
            ps.setInt(5,r.getCivico());
            ps.setString(6,r.getInfo());
            ps.setFloat(7,r.getSpesaMinima());
            ps.setFloat(8,r.getTassoConsegna());
            ps.setString(9, r.getUrlImmagine());
            ps.setInt(10,r.getCodice());
            ps.setInt(11,r.getRating());
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete(int codice) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM Ristorante WHERE codiceRistorante=?");
            ps.setInt(1, codice);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean savePreferenza(int codiceRistorante, int codiceUtente) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Preferenza (codUtente_fk, codRis_fk) VALUES(?,?)");
            ps.setInt(1,codiceRistorante);
            ps.setInt(2,codiceUtente);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean deletePreferenza(int codiceRistorante, int codiceUtente) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM Preferenza WHERE codRis_fk=? AND codUtente_fk=?");
            ps.setInt(1, codiceRistorante);
            ps.setInt(2, codiceUtente);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

}
