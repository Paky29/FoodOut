package model.disponibilita;

import model.ristorante.Ristorante;
import model.utility.ConPool;

import javax.swing.*;
import java.sql.*;

public class DisponibilitaDAO {
    public DisponibilitaDAO(){}

    public boolean doSave(Disponibilita d, int codiceRistorante) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Disponibilita (codRis_fk,nomeG_fk,oraApertura,oraChiusura) VALUES(?,?,?,?)");
            ps.setInt(1,codiceRistorante);
            ps.setString(2,d.getGiorno());
            ps.setTime(3,d.getOraApertura());
            ps.setTime(4,d.getOraChiusura());
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doUpdate(Disponibilita d, int codiceRistorante) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Disponibilita SET giorno=?, oraApertura=?, oraChiusura=?) WHERE codRis_fk=?)");
            ps.setString(1,d.getGiorno());
            ps.setTime(2,d.getOraApertura());
            ps.setTime(3,d.getOraChiusura());
            ps.setInt(4,codiceRistorante);
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
