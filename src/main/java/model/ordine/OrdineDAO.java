package model.ordine;

import model.menu.Menu;
import model.menu.MenuExtractor;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoExtractor;
import model.rider.Rider;
import model.rider.RiderDAO;
import model.rider.RiderExtractor;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.ristorante.RistoranteExtractor;
import model.tipologia.Tipologia;
import model.utente.Utente;
import model.utente.UtenteDAO;
import model.utente.UtenteExtractor;
import model.utility.ConPool;
import model.utility.Paginator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrdineDAO {

    public Ordine doRetrieveById(int codiceOrdine) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT o.codiceOrdine, o.dataOrdine, o.totale, o.nota, o.oraPartenza, o.oraArrivo, o.metodoPagamento, o.giudizio, o.voto, o.consegnato, o.codRider_fk, rd.codiceRider, rd.email, rd.pw, rd.citta, rd.veicolo, u.codiceUtente, u.nome, u.cognome, u.email, u.pw, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t.nome, t.descrizione FROM Ordine o LEFT JOIN Rider rd ON o.codRider_fk= rd.codiceRider INNER JOIN Utente u ON o.codUtente_fk=u.codiceUtente INNER JOIN Ristorante r ON o.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.nomeTip_fk INNER JOIN Tipologia ON art.nomeTip_fk=t.nome WHERE o.codiceOrdine=?");
            ps.setInt(1,codiceOrdine);
            ResultSet rs=ps.executeQuery();
            Ordine o=null;
            if(rs.next()){
                o=OrdineExtractor.extract(rs);
                Rider rd=RiderExtractor.extract(rs);
                o.setRider(rd);
                Utente u= UtenteExtractor.extract(rs);
                o.setUtente(u);
                Ristorante r= RistoranteExtractor.extract(rs);
                do{
                    Tipologia t=new Tipologia();
                    t.setNome(rs.getString("t.nome"));
                    t.setDescrizione(rs.getString("t.descrizione"));
                    r.getTipologie().add(t);
                }while(rs.next());
                o.setRistorante(r);
                if(!OrdineDAO.composizioneOrdine(conn,o))
                    return null;
            }
            return o;
        }
    }

    public ArrayList<Ordine> doRetrieveAll(Paginator paginator) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT o.codiceOrdine, o.dataOrdine, o.totale, o.nota, o.oraPartenza, o.oraArrivo, o.metodoPagamento, o.giudizio, o.voto, o.consegnato, o.codRider_fk, rd.codiceRider, rd.email, rd.pw, rd.citta, rd.veicolo, u.codiceUtente, u.nome, u.cognome, u.email, u.pw, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t.nome, t.descrizione FROM Ordine o LEFT JOIN Rider rd ON o.codRider_fk= rd.codiceRider INNER JOIN Utente u ON o.codUtente_fk=u.codiceUtente INNER JOIN Ristorante r ON o.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.nomeTip_fk INNER JOIN Tipologia ON art.nomeTip_fk=t.nome WHERE LIMIT ?,?  ");
            ps.setInt(1,paginator.getOffset());
            ps.setInt(2,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer, Ordine> ordini=new LinkedHashMap<>();
            while(rs.next()){
                int codiceOrdine=rs.getInt("o.codiceOrdine");
                if(ordini.containsKey(codiceOrdine)) {
                    Ordine o = OrdineExtractor.extract(rs);
                    Utente u=UtenteExtractor.extract(rs);
                    o.setUtente(u);
                    Ristorante r = RistoranteExtractor.extract(rs);
                    o.setRistorante(r);
                    Rider rd= RiderExtractor.extract(rs);
                    o.setRider(rd);
                    if(!OrdineDAO.composizioneOrdine(conn,o))
                        return null;
                    ordini.put(codiceOrdine, o);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ordini.get(codiceOrdine).getRistorante().getTipologie().add(t);
            }
            if(ordini.isEmpty())
                return null;
            return new ArrayList<>(ordini.values());
        }
    }

    public ArrayList<Ordine> doRetrieveByUtente(Utente u, Paginator paginator) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT o.codiceOrdine, o.dataOrdine, o.totale, o.nota, o.oraPartenza, o.oraArrivo, o.metodoPagamento, o.giudizio, o.voto, o.consegnato, o.codRider_fk, rd.codiceRider, rd.email, rd.pw, rd.citta, rd.veicolo, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t.nome, t.descrizione FROM Ordine o LEFT JOIN Rider rd On o.codOrd_fk=rd.codiceRider INNER JOIN Ristorante r ON o.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.nomeTip_fk INNER JOIN Tipologia ON art.nomeTip_fk=t.nome WHERE o.codUtente_fk=? LIMIT ?,?");
            ps.setInt(1,u.getCodice());
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer, Ordine> ordini=new LinkedHashMap<>();
            while(rs.next()){
                int codiceOrdine=rs.getInt("o.codiceOrdine");
                if(ordini.containsKey(codiceOrdine)) {
                    Ordine o = OrdineExtractor.extract(rs);
                    o.setUtente(u);
                    Ristorante r = RistoranteExtractor.extract(rs);
                    o.setRistorante(r);
                    Rider rd= RiderExtractor.extract(rs);
                    o.setRider(rd);
                    if(!OrdineDAO.composizioneOrdine(conn,o))
                        return null;
                    ordini.put(codiceOrdine, o);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ordini.get(codiceOrdine).getRistorante().getTipologie().add(t);
            }
            if(ordini.isEmpty())
                return null;
            return new ArrayList<>(ordini.values());
        }
    }

    public ArrayList<Ordine> doRetrieveByRistorante(Ristorante r, Paginator paginator) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT o.codiceOrdine, o.dataOrdine, o.totale, o.nota, o.oraPartenza, o.oraArrivo, o.metodoPagamento, o.giudizio, o.voto, o.consegnato, o.codRider_fk, rd.codiceRider, rd.email, rd.pw, rd.citta, rd.veicolo, u.codiceUtente, u.nome, u.cognome, u.email, u.pw, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore FROM Ordine o LEFT JOIN Rider rd On o.codOrd_fk=rd.codiceRider INNER JOIN Utente u ON o.codUtente_fk=u.codiceUtente WHERE o.codRis_fk=? LIMIT ?,?");
            ps.setInt(1,r.getCodice());
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            ArrayList<Ordine> ordini=new ArrayList<>();
            while(rs.next()){
                Ordine o = OrdineExtractor.extract(rs);
                o.setRistorante(r);
                Utente u = UtenteExtractor.extract(rs);
                o.setUtente(u);
                Rider rd= RiderExtractor.extract(rs);
                o.setRider(rd);
                if(!OrdineDAO.composizioneOrdine(conn,o))
                    return null;
                ordini.add(o);
                }

            if(ordini.isEmpty())
                return null;
            return ordini;
            }
    }

    public ArrayList<Ordine> doRetrieveByRider(Rider rd,Paginator paginator) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT o.codiceOrdine, o.dataOrdine, o.totale, o.nota, o.oraPartenza, o.oraArrivo, o.metodoPagamento, o.giudizio, o.voto, o.consegnato, o.codRider_fk, u.codiceUtente, u.nome, u.cognome, u.email, u.pw, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t.nome, t.descrizione FROM Ordine o LEFT JOIN Rider rd On o.codOrd_fk=rd.codiceRider INNER JOIN Ristorante r ON o.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.nomeTip_fk INNER JOIN Tipologia ON art.nomeTip_fk=t.nome WHERE o.codRider_fk=? LIMIT ?,?");
            ps.setInt(1,rd.getCodice());
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer, Ordine> ordini=new LinkedHashMap<>();
            while(rs.next()){
                int codiceOrdine=rs.getInt("o.codiceOrdine");
                if(ordini.containsKey(codiceOrdine)) {
                    Ordine o = OrdineExtractor.extract(rs);
                    o.setRider(rd);
                    Ristorante r = RistoranteExtractor.extract(rs);
                    o.setRistorante(r);
                    Utente u= UtenteExtractor.extract(rs);
                    o.setUtente(u);
                    if(!OrdineDAO.composizioneOrdine(conn,o))
                        return null;
                    ordini.put(codiceOrdine, o);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ordini.get(codiceOrdine).getRistorante().getTipologie().add(t);
            }
            if(ordini.isEmpty())
                return null;
            return new ArrayList<>(ordini.values());
        }
    }

    public ArrayList<Ordine> doRetrieveByCitta(String citta, boolean consegnato, Paginator paginator) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT o.codiceOrdine, o.dataOrdine, o.totale, o.nota, o.oraPartenza, o.oraArrivo, o.metodoPagamento, o.giudizio, o.voto, o.consegnato, o.codRider_fk, rd.codiceRider, rd.email, rd.pw, rd.citta, rd.veicolo, u.codiceUtente, u.nome, u.cognome, u.email, u.pw, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t.nome, t.descrizione FROM Ordine o LEFT JOIN Rider rd ON o.codRider_fk= rd.codiceRider INNER JOIN Utente u ON o.codUtente_fk=u.codiceUtente INNER JOIN Ristorante r ON o.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.nomeTip_fk INNER JOIN Tipologia ON art.nomeTip_fk=t.nome WHERE rd.citta=? AND o.consegnato=? LIMIT ?,?  ");
            ps.setString(1,citta);
            ps.setBoolean(2, consegnato);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer, Ordine> ordini=new LinkedHashMap<>();
            while(rs.next()){
                int codiceOrdine=rs.getInt("o.codiceOrdine");
                if(ordini.containsKey(codiceOrdine)) {
                    Ordine o = OrdineExtractor.extract(rs);
                    Utente u=UtenteExtractor.extract(rs);
                    o.setUtente(u);
                    Ristorante r = RistoranteExtractor.extract(rs);
                    o.setRistorante(r);
                    Rider rd= RiderExtractor.extract(rs);
                    o.setRider(rd);
                    if(!OrdineDAO.composizioneOrdine(conn,o))
                        return null;
                    ordini.put(codiceOrdine, o);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ordini.get(codiceOrdine).getRistorante().getTipologie().add(t);
            }
            if(ordini.isEmpty())
                return null;
            return new ArrayList<>(ordini.values());
        }
    }

    //da vedere
    public ArrayList<Ordine> doRetrieveByData(LocalDate ld, Paginator paginator, int param) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            String sign=null;
            switch(param){
                case 0: sign="="; break;
                case 1: sign=">="; break;
                case 2: sign="<="; break;
                default: sign="=";
            }

            PreparedStatement ps=conn.prepareStatement("SELECT o.codiceOrdine, o.dataOrdine, o.totale, o.nota, o.oraPartenza, o.oraArrivo, o.metodoPagamento, o.giudizio, o.voto, o.consegnato, o.codRider_fk, rd.codiceRider, rd.email, rd.pw, rd.citta, rd.veicolo, u.codiceUtente, u.nome, u.cognome, u.email, u.pw, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t.nome, t.descrizione FROM Ordine o LEFT JOIN Rider rd ON o.codRider_fk= rd.codiceRider INNER JOIN Utente u ON o.codUtente_fk=u.codiceUtente INNER JOIN Ristorante r ON o.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.nomeTip_fk INNER JOIN Tipologia ON art.nomeTip_fk=t.nome WHERE o.dataOrdine" + sign + "? LIMIT ?,?  ");
            ps.setDate(1,Date.valueOf(ld));
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer, Ordine> ordini=new LinkedHashMap<>();
            while(rs.next()){
                int codiceOrdine=rs.getInt("o.codiceOrdine");
                if(ordini.containsKey(codiceOrdine)) {
                    Ordine o = OrdineExtractor.extract(rs);
                    Utente u=UtenteExtractor.extract(rs);
                    o.setUtente(u);
                    Ristorante r = RistoranteExtractor.extract(rs);
                    o.setRistorante(r);
                    Rider rd= RiderExtractor.extract(rs);
                    o.setRider(rd);
                    if(!OrdineDAO.composizioneOrdine(conn,o))
                        return null;
                    ordini.put(codiceOrdine, o);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ordini.get(codiceOrdine).getRistorante().getTipologie().add(t);
            }
            if(ordini.isEmpty())
                return null;
            return new ArrayList<>(ordini.values());
        }
    }

    public ArrayList<Ordine> doRetrieveBetweenData(LocalDate ldInizio, LocalDate ldFine, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){

            PreparedStatement ps=conn.prepareStatement("SELECT o.codiceOrdine, o.dataOrdine, o.totale, o.nota, o.oraPartenza, o.oraArrivo, o.metodoPagamento, o.giudizio, o.voto, o.consegnato, o.codRider_fk, rd.codiceRider, rd.email, rd.pw, rd.citta, rd.veicolo, u.codiceUtente, u.nome, u.cognome, u.email, u.pw, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore, r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t.nome, t.descrizione FROM Ordine o LEFT JOIN Rider rd ON o.codRider_fk= rd.codiceRider INNER JOIN Utente u ON o.codUtente_fk=u.codiceUtente INNER JOIN Ristorante r ON o.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.nomeTip_fk INNER JOIN Tipologia ON art.nomeTip_fk=t.nome WHERE o.dataOrdine>=? AND o.dataOrdine<=? LIMIT ?,?  ");
            ps.setDate(1,Date.valueOf(ldInizio));
            ps.setDate(2,Date.valueOf(ldFine));
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            Map<Integer, Ordine> ordini=new LinkedHashMap<>();
            while(rs.next()){
                int codiceOrdine=rs.getInt("o.codiceOrdine");
                if(ordini.containsKey(codiceOrdine)) {
                    Ordine o = OrdineExtractor.extract(rs);
                    Utente u=UtenteExtractor.extract(rs);
                    o.setUtente(u);
                    Ristorante r = RistoranteExtractor.extract(rs);
                    o.setRistorante(r);
                    Rider rd= RiderExtractor.extract(rs);
                    o.setRider(rd);
                    if(!OrdineDAO.composizioneOrdine(conn,o))
                        return null;
                    ordini.put(codiceOrdine, o);
                }
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                ordini.get(codiceOrdine).getRistorante().getTipologie().add(t);
            }
            if(ordini.isEmpty())
                return null;
            return new ArrayList<>(ordini.values());
        }
    }

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
            ps.setInt(11, o.getRistorante().getCodice());
            ps.setInt(12, o.getCodice());

            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean updateRider(int codiceOrdine, int codiceRider) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Ordine SET codRider_fk=? WHERE codiceOrdine=?");
            ps.setInt(1,codiceRider);
            ps.setInt(2,codiceOrdine);

            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete(int codiceOrdine) throws SQLException{
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps=conn.prepareStatement("DELETE FROM ORDINE WHERE codiceOrdine=?");
            ps.setInt(1, codiceOrdine);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    private static boolean composizioneOrdine(Connection conn, Ordine o) throws SQLException {
        PreparedStatement prodotti=conn.prepareStatement("SELECT p.codiceProdotto, p.nome, p.ingredienti, p.info, p.prezzo, p.sconto, p.valido, p.urlImmagine, cop.quantita, t.nome, t.descrizione FROM ComposizioneOP cop INNER JOIN Prodotto p ON cop.codProd_fk=p.codiceProdotto INNER JOIN Tipologia t ON p.nomeTip_fk=t.nome WHERE cop.codOrd_fk=?");
        prodotti.setInt(1, o.getCodice());
        ResultSet rs= prodotti.executeQuery();
        while(rs.next()){
            Prodotto p= ProdottoExtractor.extract(rs);
            Tipologia t=new Tipologia();
            t.setNome(rs.getString("t.nome"));
            t.setDescrizione(rs.getString("t.descrizione"));
            p.setTipologia(t);
            p.setRistorante(o.getRistorante());
            OrdineItem oi=new OrdineItem();
            oi.setQuantita(rs.getInt("cop.quantita"));
            oi.setOff(p);
            o.getOrdineItems().add(oi);
        }

        PreparedStatement menu=conn.prepareStatement("SELECT m.codiceMenu, m.none, m.prezzo, m.sconto, m.valido, com.quantita,  p.codiceProdotto, p.nome, p.ingredienti, p.info, p.prezzo, p.sconto, p.valido, p.urlImmagine, t.nome, t.descrizione FROM ComposizioneOM com INNER JOIN Menu m ON com.codProd_fk=m.codiceMenu INNER JOIN AppartenenzaPM apm ON m.codiceMenu=apm.codMenu_fk INNER JOIN Prodotto p ON apm.codProd_fk=p.codiceProdotto INNER JOIN Tipologia t ON p.nomeTip_fk=t.nome WHERE com.codOrd_fk=?");
        menu.setInt(1, o.getCodice());
        rs= menu.executeQuery();
        Map<Integer, Menu> menus=new LinkedHashMap<>();
        ArrayList<Integer> quantita =new ArrayList<>();
        while(rs.next()){
            int codiceMenu=rs.getInt("m.codiceMenu");
            if(!menus.containsKey(codiceMenu)){
                Menu m= MenuExtractor.extract(rs);
                quantita.add(rs.getInt("com.quantita"));
                menus.put(codiceMenu, m);
            }
            Prodotto p= ProdottoExtractor.extract(rs);
            Tipologia t=new Tipologia();
            t.setNome(rs.getString("t.nome"));
            t.setDescrizione(rs.getString("t.descrizione"));
            p.setTipologia(t);
            p.setRistorante(o.getRistorante());
            menus.get(codiceMenu).getProdotti().add(p);
        }

        ArrayList<Menu> menusArray= new ArrayList<>(menus.values());
        int i=0;
        for(Menu m : menusArray){
            OrdineItem oi=new OrdineItem();
            int q=quantita.get(i);
            oi.setOff(m);
            oi.setQuantita(q);
            o.getOrdineItems().add(oi);
            i++;
        }

        if(o.getOrdineItems().isEmpty())
            return false;
        else
             return true;
    }
}