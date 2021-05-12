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
            PreparedStatement ps = conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, art.nomeTip_fk FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE r.codiceRistorante=?");
            ps.setInt(1, codice);
            ResultSet rs = ps.executeQuery();
            Ristorante r = null;
            if(rs.next()) {
                r = RistoranteExtractor.extract(rs);
                do {
                    Tipologia t=new Tipologia();
                    t.setNome(rs.getString("art.nomeTip_fk"));
                    r.getTipologie().add(t);
                }while(rs.next());
            }
            PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
            calendario.setInt(1,codice);
            rs=calendario.executeQuery();
            while(rs.next())
            {
                Disponibilita d=DisponibilitaExtractor.extract(rs);
                r.getGiorni().add(d);
            }
            return r;
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
                    PreparedStatement tip=conn.prepareStatement("SELECT art.nomeTip_fk FROM AppartenenzaRT art WHERE art.codRis_fk=?");
                    tip.setInt(1,ristoranteid);
                    ResultSet set=tip.executeQuery();
                    while(set.next()){
                        Tipologia t=new Tipologia();
                        t.setNome(set.getString("art.nomeTip_fk"));
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
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, art.nomeTip_fk FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE r.citta=? LIMIT ?,?" );
            ps.setString(1, citta);
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                    calendario.setInt(1,ristoranteid);
                    rs=calendario.executeQuery();
                    while(rs.next())
                    {
                        Disponibilita d=DisponibilitaExtractor.extract(rs);
                        r.getGiorni().add(d);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("art.nomeTip_fk"));
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
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, art.nomeTip_fk FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE r.tassoConsegna<=? AND r.citta=? LIMIT ?,?" );
            ps.setFloat(1, tasso);
            ps.setString(2,citta);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                    calendario.setInt(1,ristoranteid);
                    rs=calendario.executeQuery();
                    while(rs.next())
                    {
                        Disponibilita d=DisponibilitaExtractor.extract(rs);
                        r.getGiorni().add(d);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("art.nomeTip_fk"));
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
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, art.nomeTip_fk FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE r.nome=? AND r.citta=? LIMIT ?,?" );
            ps.setString(1, citta);
            ps.setString(2, nome);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                    calendario.setInt(1,ristoranteid);
                    rs=calendario.executeQuery();
                    while(rs.next())
                    {
                        Disponibilita d=DisponibilitaExtractor.extract(rs);
                        r.getGiorni().add(d);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("art.nomeTip_fk"));
                ristoranti.get(ristoranteid).getTipologie().add(t);
            }
            if(ristoranti.isEmpty())
                return null;
            return new ArrayList<>(ristoranti.values());
        }
    }

    public ArrayList<Ristorante> doRetrieveAll(Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, art.nomeTip_fk FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk LIMIT ?,?" );
            ps.setInt(1,paginator.getOffset());
            ps.setInt(2,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer,Ristorante> ristoranti=new LinkedHashMap<>();
            while(rs.next()){
                int ristoranteid=rs.getInt("codiceRistorante");
                if(!ristoranti.containsKey(ristoranteid)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    PreparedStatement calendario=conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                    calendario.setInt(1,ristoranteid);
                    rs=calendario.executeQuery();
                    while(rs.next())
                    {
                        Disponibilita d=DisponibilitaExtractor.extract(rs);
                        r.getGiorni().add(d);
                    }
                    ristoranti.put(ristoranteid, r);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("art.nomeTip_fk"));
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
            PreparedStatement ps=conn.prepareStatement("UPDATE Ristorante SET nome=?, provincia=?, citta=?, via=?, civico=?, info=?, spesaMinima=?, tassoConsegna=?, urlImmagine=?, rating=?) WHERE codiceRistorante=?)");
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
}
