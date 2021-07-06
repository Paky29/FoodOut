package model.rider;

import model.disponibilita.Disponibilita;
import model.disponibilita.DisponibilitaExtractor;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.turno.Turno;
import model.turno.TurnoExtractor;
import model.utility.ConPool;
import model.utility.Paginator;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class RiderDAO {
    public RiderDAO(){}

    public Rider doRetrievebyId(int codiceRider) throws SQLException{
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE codiceRider=?");
            ps.setInt(1, codiceRider);
            ResultSet rs=ps.executeQuery();
            OrdineDAO service=new OrdineDAO();
            Rider rd=null;
            if(rs.next()){
                rd = RiderExtractor.extract(rs);
               /* do{
                    Turno t= TurnoExtractor.extract(rs);
                    rd.getTurni().add(t);
                }while(rs.next());*/

                ArrayList<Ordine> ordini=service.doRetrieveByRider(rd);
                rd.setOrdini(ordini);
                }
            return rd;
        }
    }

    public Rider doRetrievebyEmail(String email) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE email=?");
            ps.setString(1, email);
            ResultSet rs=ps.executeQuery();
            OrdineDAO service=new OrdineDAO();
            Rider rd=null;
            if(rs.next()){
                rd = RiderExtractor.extract(rs);
                do{
                    Turno t= TurnoExtractor.extract(rs);
                    rd.getTurni().add(t);
                }while(rs.next());

                ArrayList<Ordine> ordini=service.doRetrieveByRider(rd);
                rd.setOrdini(ordini);
            }
            return rd;
        }
    }

    public Rider doRetrievebyEmailAndPassword(String email, String password) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, pw, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE email=? AND pw=SHA1(?)");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();
            OrdineDAO service=new OrdineDAO();
            Rider rd=null;
            if(rs.next()){
                rd = RiderExtractor.extract(rs);
                rd.setPassword(rs.getString("rd.pw"));
                do{
                    Turno t= TurnoExtractor.extract(rs);
                    rd.getTurni().add(t);
                }while(rs.next());

                ArrayList<Ordine> ordini=service.doRetrieveByRider(rd);
                rd.setOrdini(ordini);
            }
            return rd;
        }
    }

    public ArrayList<Rider> doRetrieveAll(Paginator paginator) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT rd.codiceRider, rd.email, rd.citta, rd.veicolo FROM Rider rd LIMIT ?,?");
            ps.setInt(1, paginator.getOffset());
            ps.setInt(2, paginator.getLimit());
            ResultSet rs=ps.executeQuery();

            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("rd.codiceRider");
                Rider rd=RiderExtractor.extract(rs);
                riders.put(codiceRider, rd);
            }

            if(riders.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: riders.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement turno=conn.prepareStatement("SELECT t.codRider_fk, t.giorno, t.oraInizio, t.oraFine FROM Turno t WHERE t.codRider_fk IN "+sj.toString());
            ResultSet setTurno=turno.executeQuery();

            while(setTurno.next()){
                int codiceRider=setTurno.getInt("t.codRider_fk");
                Turno t = TurnoExtractor.extract(setTurno);
                riders.get(codiceRider).getTurni().add(t);
            }

            return new ArrayList<>(riders.values());
        }
    }

    public ArrayList<Rider> doRetrieveByCitta(String citta, Paginator paginator) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo FROM Rider rd WHERE citta=? LIMIT ?,?");
            ps.setString(1, citta);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());
            ResultSet rs=ps.executeQuery();

            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("rd.codiceRider");
                Rider rd=RiderExtractor.extract(rs);
                riders.put(codiceRider, rd);
            }

            if(riders.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: riders.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement turno=conn.prepareStatement("SELECT t.codRider_fk, t.giorno, t.oraInizio, t.oraFine FROM Turno t WHERE t.codRider_fk IN "+sj.toString());
            ResultSet setTurno=turno.executeQuery();

            while(setTurno.next()){
                int codiceRider=setTurno.getInt("t.codRider_fk");
                Turno t = TurnoExtractor.extract(setTurno);
                riders.get(codiceRider).getTurni().add(t);
            }

            return new ArrayList<>(riders.values());
        }
    }

    public ArrayList<Rider> doRetrieveByGiornoTurno(String giorno, Paginator paginator) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE giorno=? LIMIT ?,?");
            ps.setString(1, giorno);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());
            ResultSet rs=ps.executeQuery();

            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("rd.codiceRider");
                Rider rd=RiderExtractor.extract(rs);
                riders.put(codiceRider, rd);
            }

            if(riders.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: riders.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement turno=conn.prepareStatement("SELECT t.codRider_fk, t.giorno, t.oraInizio, t.oraFine FROM Turno t WHERE t.codRider_fk IN "+sj.toString());
            ResultSet setTurno=turno.executeQuery();

            while(setTurno.next()){
                int codiceRider=setTurno.getInt("t.codRider_fk");
                Turno t = TurnoExtractor.extract(setTurno);
                riders.get(codiceRider).getTurni().add(t);
            }

            return new ArrayList<>(riders.values());
        }
    }

    public ArrayList<Rider> doRetrieveByGiornoTurnoANDCitta(String giorno, String citta, Paginator paginator) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE giorno=? AND citta=? LIMIT ?,?");
            ps.setString(1, giorno);
            ps.setString(2, citta);
            ps.setInt(3, paginator.getOffset());
            ps.setInt(4, paginator.getLimit());
            ResultSet rs = ps.executeQuery();

            Map<Integer, Rider> riders = new LinkedHashMap<>();
            while (rs.next()) {
                int codiceRider = rs.getInt("rd.codiceRider");
                Rider rd = RiderExtractor.extract(rs);
                riders.put(codiceRider, rd);
            }

            if (riders.isEmpty())
                return null;

            StringJoiner sj = new StringJoiner(",", "(", ")");
            for (int key : riders.keySet()) {
                sj.add(Integer.toString(key));
            }

            PreparedStatement turno = conn.prepareStatement("SELECT t.codRider_fk, t.giorno, t.oraInizio, t.oraFine FROM Turno t WHERE t.codRider_fk IN " + sj.toString());
            ResultSet setTurno = turno.executeQuery();

            while (setTurno.next()) {
                int codiceRider = setTurno.getInt("t.codRider_fk");
                Turno t = TurnoExtractor.extract(setTurno);
                riders.get(codiceRider).getTurni().add(t);
            }

            return new ArrayList<>(riders.values());
        }
    }

    public boolean doSave(Rider rd) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            if(!rd.getEmail().contains("@foodout.rider.com"))
                return false;
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Rider (email,pw,veicolo,citta) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,rd.getEmail());
            ps.setString(2,rd.getPassword());
            ps.setString(3,rd.getVeicolo());
            ps.setString(4,rd.getCitta());
            if(ps.executeUpdate()!=1)
                return false;
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            rd.setCodice(id);
            return true;
        }
    }

    public boolean doUpdate(Rider rd) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            if(!rd.getEmail().contains("foodout.rider.com"))
                return false;
            PreparedStatement ps=conn.prepareStatement("UPDATE Rider SET email=?, pw=?, veicolo=?, citta=? WHERE codiceRider=?");
            ps.setString(1,rd.getEmail());
            ps.setString(2,rd.getPassword());
            ps.setString(3,rd.getVeicolo());
            ps.setString(4,rd.getCitta());
            ps.setInt(5,rd.getCodice());
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete (int codice) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM Rider WHERE codiceRider=?");
            ps.setInt(1, codice);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public int countAll() throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numRider FROM Rider rd ");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRider");
            }
            else
                return 0;
        }
    }

    public int countCitta(String citta) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numRider FROM Rider rd WHERE rd.citta=?");
            ps.setString(1,citta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRider");
            }
            else
                return 0;
        }
    }

    public int countGiornoTurnoCitta(String giorno,String citta) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numRider FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE t.giorno=? AND r.citta=?");
            ps.setString(1,giorno);
            ps.setString(2,citta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRider");
            }
            else
                return 0;
        }
    }

    public int countGiornoTurno(String giorno) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numRider FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE t.giorno=?");
            ps.setString(1,giorno);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRider");
            }
            else
                return 0;
        }
    }
}
