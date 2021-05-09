package model.lavororider;
import model.utility.ConPool;
import java.sql.*;

public class LavoroRiderDAO {
    public LavoroRiderDAO(){}

    public boolean doSave(LavoroRider lr, int codiceRider) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO LavoroRider (codRider_fk,nomeG_fk,oraApertura,oraChiusura) VALUES(?,?,?,?)");
            ps.setInt(1,codiceRider);
            ps.setString(2,lr.getGiorno());
            ps.setTime(3,lr.getOraApertura());
            ps.setTime(4,lr.getOraChiusura());
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doUpdate(LavoroRider lr, int codiceRider) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE LavoroRider SET giorno=?, oraApertura=?, oraChiusura=?) WHERE codRider_fk=?)");
            ps.setString(1,lr.getGiorno());
            ps.setTime(2,lr.getOraApertura());
            ps.setTime(3,lr.getOraChiusura());
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

