package model.ristorante;

import model.tipologia.Tipologia;
import model.utility.ConPool;

import java.sql.*;
import java.util.ArrayList;

public class RistoranteDAO {
    public RistoranteDAO(){}

    public ArrayList<Ristorante> retrievebyTipologia(String nomeTipologia) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT id, nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegne FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE art.tipologia_fk=?" );
            ps.setString(1, nomeTipologia);
            ResultSet rs=ps.executeQuery();
            ArrayList<Ristorante> ristoranti=new ArrayList<>();
            while(rs.next()){
                int id=rs.getInt(1);
                int nome=rs.getString(2);
                int provincia=rs.getString(2);
                int citta=rs.getString(2);
                int via=rs.getString(2);
                int nome=rs.getString(2);
                int nome=rs.getString(2);


            }

        }
    }

    public boolean doSave(Ristorante r) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Ristorante (nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegne) VALUES(?,?, ?,?, ?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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
}
