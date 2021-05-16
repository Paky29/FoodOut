package model.ordine;

import model.utility.ConPool;

import java.sql.*;
import java.time.LocalDate;

public class OrdineDAO {
    public boolean doSave(Ordine o) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            conn.setAutoCommit(false);
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Ordine (dataOrdine, totale, nota, metodoPagamento, consegnato, codUtente_fk, codRis_fk) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(o.getDataOrdine()));
            ps.setFloat(2,o.getTotale());
            ps.setString(3,o.getNota());
            ps.setString(4,o.getMetodoPagamento());
            ps.setBoolean(5,o.isConsegnato());
            ps.setInt(6,o.getUtente().getCodice());
            ps.setInt(7, o.getRistorante().getCodice());
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

    public boolean doUpdate(Ordine o) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Ordine SET dataOrdine=?, totale=?, nota=?, oraPartenza=?, oraArrivo=?, voto=?, giudizio=?, metodoPagamento=?, consegnato=?, codUtente_fk=?, codRis_fk=? WHERE codiceOrdine=?");
            ps.setDate(1,Date.valueOf(o.getDataOrdine()));
            ps.setFloat(2, o.getTotale());
            ps.setString(3, o.getNota());
            ps.setTime(4, Time.valueOf(o.getOraPartenza()));
            ps.setTime(5, Time.valueOf(o.getOraArrivo()));
            ps.setInt(6, o.getVoto());
            ps.setString(7, o.getGiudizio());
            ps.setString(8, o.getMetodoPagamento());
            ps.setBoolean(9, o.isConsegnato());
            ps.setInt(10, o.getUtente().getCodice());
            ps.setInt(12, o.getRistorante().getCodice());
            ps.setInt(13, o.getCodice());

            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }

    }

    public boolean doDelete(int codiceOrdine) throws SQLException{
        try(Connection conn=ConPool.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement ps=conn.prepareStatement("DELETE FROM ORDINE WHERE codiceOrdine=?");
            ps.setInt(1, codiceOrdine);
            if(ps.executeUpdate()!=1){
                conn.setAutoCommit(true);
                return false;
            }
            else{
                PreparedStatement ps1=conn.prepareStatement("DELETE FROM ComposizioneOP WHERE codOrd_fk=? ");
                ps1.setInt(1, codiceOrdine);
                PreparedStatement ps2=conn.prepareStatement("DELETE FROM ComposizioneOM WHERE codOrd_fk=? ");
                ps2.setInt(1, codiceOrdine);
                if(ps1.executeUpdate()!=1 || ps2.executeUpdate()!=1){
                    conn.rollback();
                    conn.setAutoCommit(true);
                    return false;
                }
                else{
                    conn.commit();
                    conn.setAutoCommit(true);
                    return true;
                }

            }

        }
    }
}
