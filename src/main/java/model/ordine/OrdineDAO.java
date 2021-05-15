package model.ordine;

import model.utility.ConPool;

import java.sql.*;

public class OrdineDAO {
    public boolean doSave(Ordine o) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            conn.setAutoCommit(false);
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Ordine (dataOrdine, totale, nota, metodoPagamento, consegnato, codUtente_fk) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1,o.getDataOrdine());
            ps.setFloat(2,o.getTotale());
            ps.setString(3,o.getNota());
            ps.setString(4,o.getMetodoPagamento());
            ps.setBoolean(5,o.isConsegnato());
            ps.setInt(6,o.getUtente().getCodice());
            int rows=ps.executeUpdate();

            if(rows!=1) {
                conn.setAutoCommit(true);
                return false;
            }

            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            o.setCodice(id);

            int total=0;
            for(OrdineItem oi:o.getOrdineItems()){
                if(oi.getClass().getName().equals("Prodotto"))
                    ps = conn.prepareStatement("INSERT INTO ComposizioneOP (codOrd_fk,codProd_fk,quantita) VALUES(?,?,?)");
                else
                    ps = conn.prepareStatement("INSERT INTO ComposizioneOM (codOrd_fk,codProd_fk,quantita) VALUES(?,?,?)");
                ps.setInt(1,id);
                ps.setInt(2,oi.getOff().getCodice());
                ps.setInt(3,oi.getQuantita());
                total+=ps.executeUpdate();
            }

            if(total==(rows*o.getOrdineItems().size())){
                conn.commit();
                conn.setAutoCommit(true);
                return true;
            }
            else
            {
                conn.rollback();
                conn.setAutoCommit(true);
                return false;
            }
        }
    }
}
