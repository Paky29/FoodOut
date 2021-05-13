package model.tipologia;

import model.utility.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipologiaDAO {
    public TipologiaDAO(){}

    public Tipologia doRetrieveByNome(String nomeTipologia) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT nome, descrizione FROM Tipologia WHERE nome=?");
            ps.setString(1, nomeTipologia);
            ResultSet rs=ps.executeQuery();
            Tipologia t=new Tipologia();
            t.setNome(rs.getString("nome"));
            t.setDescrizione(rs.getString("descrizione"));
            return t;
        }
    }
}
