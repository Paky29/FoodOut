package model.disponibilita;

import model.utility.ConPool;
import java.sql.*;

public class DisponibilitaDAO {
    public DisponibilitaDAO(){}

    public boolean doSave(Disponibilita d, int codiceRistorante) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Disponibilita (codRis_fk,giorno,oraApertura,oraChiusura) VALUES(?,?,?,?)");
            ps.setInt(1,codiceRistorante);
            ps.setString(2,d.getGiorno());
            ps.setTime(3,Time.valueOf(d.getOraApertura()));
            ps.setTime(4,Time.valueOf(d.getOraChiusura()));
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doUpdate(Disponibilita d, int codiceRistorante) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Disponibilita SET oraApertura=?, oraChiusura=?) WHERE codRis_fk=? AND giorno=?)");
            ps.setTime(1,Time.valueOf(d.getOraApertura()));
            ps.setTime(2,Time.valueOf(d.getOraChiusura()));
            ps.setInt(3,codiceRistorante);
            ps.setString(4, d.getGiorno());
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete(String giorno, int codiceRistorante) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM Disponibilita WHERE codRis_fk=? AND giorno=?");
            ps.setInt(1, codiceRistorante);
            ps.setString(2,giorno);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }
}
