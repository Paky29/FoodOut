package model.rider;

import model.ordine.Ordine;
import model.ordine.OrdineExtractor;
import model.turno.Turno;
import model.turno.TurnoExtractor;
import model.utility.ConPool;
import model.utility.Paginator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RiderDAO {
    public RiderDAO(){}

    public Rider doRetrievebyId(int codiceRider) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider r INNER JOIN Turno t ON r.codiceRider=t.codRider_fk WHERE codiceRider=? AND password=SHA1(?)");
            ps.setInt(1, codiceRider);
            ResultSet rs=ps.executeQuery();
            Rider r=null;
            if(rs.next()){
                r = RiderExtractor.extract(rs);
                do{
                    Turno t= TurnoExtractor.extract(rs);
                    r.getTurni().add(t);
                }while(rs.next());

                }

            return r;
        }
    }


    public Rider doRetrievebyEmailAndPassword(String email, String password, Paginator paginator) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider r INNER JOIN Turno t ON r.codiceRider=t.codRider_fk WHERE email=? AND password=SHA1(?)");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();
            Rider r=null;
            if(rs.next()){
                r = RiderExtractor.extract(rs);
                do{
                    Turno t= TurnoExtractor.extract(rs);
                    r.getTurni().add(t);
                }while(rs.next());
                //copiare in ordineDAO
                ps=conn.prepareStatement("SELECT codiceOrdine, dataOrdine, totale, nota, oraPartenza, oraArrivo, metodoPagamento, consegnato WHERE consegnato=false LIMIT ?,?");
                ps.setInt(1,paginator.getOffset());
                ps.setInt(2,paginator.getLimit());
                ResultSet set=ps.executeQuery();
                while(set.next()){
                    Ordine o= OrdineExtractor.extract(set);
                    r.getOrdini().add(o);
                }
                //copiare in ordineDAO
                ps=conn.prepareStatement("SELECT codiceOrdine, dataOrdine, totale, nota, oraPartenza, oraArrivo, metodoPagamento, consegnato WHERE consegnato=true LIMIT ?,?");
                ps.setInt(1,paginator.getOffset());
                ps.setInt(2,paginator.getLimit());
                ResultSet set2=ps.executeQuery();
                while(set.next()){
                    Ordine o= OrdineExtractor.extract(set2);
                    r.getOrdini().add(o);
                }

            }

            return r;
        }
    }

    public ArrayList<Rider> doRetrieveAll(Paginator paginator) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider r INNER JOIN Turno t ON r.codiceRider=t.codRider_fk LIMIT ?,?");
            ps.setInt(1, paginator.getOffset());
            ps.setInt(1, paginator.getLimit());

            ResultSet rs=ps.executeQuery();
            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("codiceRider");
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
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider r INNER JOIN Turno t ON r.codiceRider=t.codRider_fk WHERE citta=? LIMIT ?,?");
            ps.setString(1, citta);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());

            ResultSet rs=ps.executeQuery();
            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("codiceRider");
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
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider r INNER JOIN Turno t ON r.codiceRider=t.codRider_fk WHERE giorno=? LIMIT ?,?");
            ps.setString(1, giorno);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());

            ResultSet rs=ps.executeQuery();
            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("codiceRider");
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
            PreparedStatement ps=conn.prepareStatement("SELECT codiceRider, email, citta, veicolo, giorno, oraInizio, oraFine FROM Rider r INNER JOIN Turno t ON r.codiceRider=t.codRider_fk WHERE giorno=? AND citta=?LIMIT ?,?");
            ps.setString(1, giorno);
            ps.setString(2, citta);
            ps.setInt(3, paginator.getOffset());
            ps.setInt(4, paginator.getLimit());

            ResultSet rs=ps.executeQuery();
            Map<Integer, Rider> riders= new LinkedHashMap<>();
            while(rs.next()){
                int codiceRider=rs.getInt("codiceRider");
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
}
