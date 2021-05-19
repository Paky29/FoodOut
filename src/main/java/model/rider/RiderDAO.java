package model.rider;

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

public class RiderDAO {
    public RiderDAO(){}

    public Rider doRetrievebyId(int codiceRider) throws SQLException{
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE codiceRider=? AND password=SHA1(?)");
            ps.setInt(1, codiceRider);
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


    public Rider doRetrievebyEmailAndPassword(String email, String password, Paginator paginator) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, pw, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE email=? AND password=SHA1(?)");
            ps.setString(1, email);
            ps.setString(2, password);
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

    public ArrayList<Rider> doRetrieveAll(Paginator paginator) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk LIMIT ?,?");
            ps.setInt(1, paginator.getOffset());
            ps.setInt(1, paginator.getLimit());

            ResultSet rs=ps.executeQuery();
            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("rd.codiceRider");
                if(!riders.containsKey(codiceRider)){
                    Rider r=RiderExtractor.extract(rs);
                    riders.put(codiceRider, r);
                }
                Turno t=TurnoExtractor.extract(rs);
                riders.get(codiceRider).getTurni().add(t);
            }
            if(riders.isEmpty())
                return null;
            return new ArrayList<>(riders.values());
        }
    }

    public ArrayList<Rider> doRetrieveByCitta(String citta, Paginator paginator) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE citta=? LIMIT ?,?");
            ps.setString(1, citta);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());

            ResultSet rs=ps.executeQuery();
            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("rd.codiceRider");
                if(!riders.containsKey(codiceRider)){
                    Rider r=RiderExtractor.extract(rs);
                    riders.put(codiceRider, r);
                }

                Turno t=TurnoExtractor.extract(rs);
                riders.get(codiceRider).getTurni().add(t);

            }

            if(riders.isEmpty())
                return null;
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
                if(!riders.containsKey(codiceRider)){
                    Rider r=RiderExtractor.extract(rs);
                    riders.put(codiceRider, r);
                }

                Turno t=TurnoExtractor.extract(rs);
                riders.get(codiceRider).getTurni().add(t);

            }

            if(riders.isEmpty())
                return null;
            return new ArrayList<>(riders.values());

        }
    }

    public ArrayList<Rider> doRetrieveByGiornoTurnoANDCitta(String giorno, String citta, Paginator paginator) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider rd INNER JOIN Turno t ON rd.codiceRider=t.codRider_fk WHERE giorno=? AND citta=?LIMIT ?,?");
            ps.setString(1, giorno);
            ps.setString(2, citta);
            ps.setInt(3, paginator.getOffset());
            ps.setInt(4, paginator.getLimit());

            ResultSet rs=ps.executeQuery();
            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("rd.codiceRider");
                if(!riders.containsKey(codiceRider)){
                    Rider r=RiderExtractor.extract(rs);
                    riders.put(codiceRider, r);
                }

                Turno t=TurnoExtractor.extract(rs);
                riders.get(codiceRider).getTurni().add(t);

            }

            if(riders.isEmpty())
                return null;
            return new ArrayList<>(riders.values());

        }
    }

    public boolean doSave(Rider r) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Rider (email,pw,veicolo,citta) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,r.getEmail());
            ps.setString(2,r.getPassword());
            ps.setString(3,r.getVeicolo());
            ps.setString(4,r.getCitta());
            if(ps.executeUpdate()!=1)
                return false;
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            r.setCodice(id);
            return true;
        }
    }

    public boolean doUpdate(Rider r) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Rider SET email=?, pw=?, veicolo=?, citta=? WHERE codiceRider=?");
            ps.setInt(1,r.getCodice());
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
}
