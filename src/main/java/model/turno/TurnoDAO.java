package model.turno;
import model.utility.ConPool;
import java.sql.*;

public class TurnoDAO {
    public TurnoDAO(){}

    public boolean doSave(Turno t, int codiceRider) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO LavoroRider (codRider_fk,nomeG_fk,oraApertura,oraChiusura) VALUES(?,?,?,?)");
            ps.setInt(1,codiceRider);
            ps.setString(2,t.getGiorno());
            ps.setTime(3,t.getOraApertura());
            ps.setTime(4,t.getOraChiusura());
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doUpdate(Turno t, int codiceRider) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE LavoroRider SET giorno=?, oraApertura=?, oraChiusura=?) WHERE codRider_fk=?)");
            ps.setString(1,t.getGiorno());
            ps.setTime(2,t.getOraApertura());
            ps.setTime(3,t.getOraChiusura());
            ps.setInt(4,codiceRider);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete(String giorno, int codiceRider) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM LavoroRider WHERE codRider_fk=? AND giorno=?");
            ps.setInt(1, codiceRider);
            ps.setString(2,giorno);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }
}

