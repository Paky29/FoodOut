package model.ristorante;

import model.utility.ConPool;

import java.sql.*;
import java.util.ArrayList;

public class RistoranteDAO {
    public RistoranteDAO(){}

    public ArrayList<Ristorante> retrievebyTipologia(String nomeTipologia) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE art.tipologia_fk=?" );
            ps.setString(1, nomeTipologia);
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

    public ArrayList<Ristorante> retrievebyCitta(String citta) throws SQLException{
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
    public ArrayList<Ristorante> retrievebyTassoConsegna(float tasso) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna FROM Ristorante WHERE tassoConsegne=?" );
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

    public ArrayList<Ristorante> retrievebyNome_Citta(String citta, String nome) throws SQLException{
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

    public ArrayList<Ristorante> retrieveAll() throws SQLException{
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
}
