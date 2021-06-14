package model.turno;
import model.utility.ConPool;
import java.sql.*;

public class TurnoDAO {
    public TurnoDAO(){}

    public boolean doSave(Turno t, int codiceRider) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Turno (codRider_fk,giorno,oraInizio,oraFine) VALUES(?,?,?,?)");
            ps.setInt(1,codiceRider);
            ps.setString(2,t.getGiorno());
            ps.setTime(3,Time.valueOf(t.getOraInizio()));
            ps.setTime(4,Time.valueOf(t.getOraFine()));
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doUpdate(Turno t, int codiceRider) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Turno SET giorno=?, oraInizio=?, oraFine=?) WHERE codRider_fk=?)");
            ps.setString(1,t.getGiorno());
            ps.setTime(2,Time.valueOf(t.getOraInizio()));
            ps.setTime(3,Time.valueOf(t.getOraFine()));
            ps.setInt(4,codiceRider);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete(String giorno, int codiceRider) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM Turno WHERE codRider_fk=? AND giorno=?");
            ps.setInt(1, codiceRider);
            ps.setString(2,giorno);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }
}

