package model.ristorante;

import model.immagine.ImmagineExtractor;
import model.utility.ConPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class RistoranteDAO {
    public RistoranteDAO(){}

    public Ristorante doRetrieveById(int codice) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna FROM Ristorante r WHERE codiceRistorante=?");
            ps.setInt(1, codice);
            ResultSet rs = ps.executeQuery();
            Ristorante r = null;
            if (rs.next()) {
                r = RistoranteExtractor.extract(rs);
            }
            return r;
        }
    }

    public ArrayList<Ristorante> doRetrieveByTipologia(String nomeTipologia) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, url, principale FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk INNER JOIN Immagine i ON r.codiceRistorante=i.codRis_fk WHERE art.tipologia_fk=? AND i.principale=true" );
            ps.setString(1, nomeTipologia);
            ResultSet rs=ps.executeQuery();
            ArrayList<Ristorante> ristoranti=new ArrayList<>();
            while(rs.next()){
                Ristorante r=RistoranteExtractor.extract(rs);
                r.getImmagini().add(ImmagineExtractor.extract(rs));
                ristoranti.add(r);

            }

            if(ristoranti.isEmpty())
                return null;
            return ristoranti;
        }
    }

    public ArrayList<Ristorante> doRetrieveByCitta(String citta) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna FROM Ristorante WHERE citta=?" );
            ps.setString(1, citta);
            ResultSet rs=ps.executeQuery();
            ArrayList<Ristorante> ristoranti=new ArrayList<>();
            while(rs.next()){
                ristoranti.add(RistoranteExtractor.extract(rs));
            }

            if(ristoranti.isEmpty())
                return null;
            return ristoranti;
        }
    }

    // restituisce i ristoranti con un tasso di consegna inferiore o uguale a quello inserito
    public ArrayList<Ristorante> doRetrieveByTassoConsegna(float tasso) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna FROM Ristorante WHERE tassoConsegne<=?" );
            ps.setFloat(1, tasso);
            ResultSet rs=ps.executeQuery();
            ArrayList<Ristorante> ristoranti=new ArrayList<>();
            while(rs.next()){
                ristoranti.add(RistoranteExtractor.extract(rs));
            }

            if(ristoranti.isEmpty())
                return null;
            return ristoranti;
        }
    }

    public ArrayList<Ristorante> doRetrieveByNome_Citta(String citta, String nome) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna FROM Ristorante WHERE citta=? and nome=?" );
            ps.setString(1, citta);
            ps.setString(2, nome);
            ResultSet rs=ps.executeQuery();
            ArrayList<Ristorante> ristoranti=new ArrayList<>();
            while(rs.next()){
                ristoranti.add(RistoranteExtractor.extract(rs));
            }

            if(ristoranti.isEmpty())
                return null;
            return ristoranti;
        }
    }

    //da vedere
    /*
    public Optional<Ristorante> fetchRistoranteWithProdotti(int codice) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT *  FROM Ristorante r INNER JOIN Prodotto p ON r.codiceRistorante=p.codRis_fk WHERE r.codiceRistorante=?" );
            ps.setInt(1, codice);
            ResultSet rs=ps.executeQuery();
            Ristorante r=null;
            if(rs.next()){
                r=RistoranteExtractor.extract(rs);
            }
        }
    }*/

    public ArrayList<Ristorante> doRetrieveAll() throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna FROM Ristorante" );
            ResultSet rs=ps.executeQuery();
            ArrayList<Ristorante> ristoranti=new ArrayList<>();
            while(rs.next()){
                ristoranti.add(RistoranteExtractor.extract(rs));
            }

            if(ristoranti.isEmpty())
                return null;
            return ristoranti;
        }
    }

    public boolean doSave(Ristorante r) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Ristorante (nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna) VALUES(?,?, ?,?, ?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,r.getNome());
            ps.setString(2,r.getProvincia());
            ps.setString(3,r.getCitta());
            ps.setString(4,r.getVia());
            ps.setInt(5,r.getCivico());
            ps.setString(6,r.getInfo());
            ps.setFloat(7,r.getSpesaMinima());
            ps.setFloat(8,r.getTassoConsegna());
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
            PreparedStatement ps=conn.prepareStatement("UPDATE Ristorante SET nome=?, provincia=?, citta=?, via=?, civico=?, info=?, spesaMinima=?, tassoConsegne=?) WHERE codiceRistorante=?)");
            ps.setString(1,r.getNome());
            ps.setString(2,r.getProvincia());
            ps.setString(3,r.getCitta());
            ps.setString(4,r.getVia());
            ps.setInt(5,r.getCivico());
            ps.setString(6,r.getInfo());
            ps.setFloat(7,r.getSpesaMinima());
            ps.setFloat(8,r.getTassoConsegna());
            ps.setInt(9, r.getCodice());
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
