package model.ristorante;

import com.mysql.cj.util.StringUtils;
import model.disponibilita.Disponibilita;
import model.disponibilita.DisponibilitaExtractor;
import model.tipologia.Tipologia;
import model.utente.Utente;
import model.utente.UtenteExtractor;
import model.utility.ConPool;
import model.utility.Paginator;

import javax.sql.rowset.serial.SerialArray;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class RistoranteDAO {
    public RistoranteDAO(){}

    public Ristorante doRetrieveById(int codice) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT codiceRistorante, r.nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating, t.nome, t.descrizione FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE r.codiceRistorante=?");
            ps.setInt(1, codice);
            ResultSet rs = ps.executeQuery();
            Ristorante r = null;
            if(rs.next()) {
                r = RistoranteExtractor.extract(rs);
                do {
                    Tipologia t = new Tipologia();
                    t.setNome(rs.getString("t.nome"));
                    t.setDescrizione(rs.getString("t.descrizione"));
                    r.getTipologie().add(t);
                } while (rs.next());

                PreparedStatement calendario = conn.prepareStatement("SELECT giorno, oraApertura, oraChiusura FROM Disponibilita d WHERE d.codRis_fk=?");
                calendario.setInt(1, codice);
                rs = calendario.executeQuery();
                while (rs.next()) {
                    Disponibilita d = DisponibilitaExtractor.extract(rs);
                    r.getGiorni().add(d);
                }
            }

            return r;
        }
    }

    public ArrayList<Ristorante> doRetrieveByScontoDisp(String citta, Paginator paginator) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating FROM Ristorante r INNER JOIN Prodotto p ON p.codRis_fk=r.codiceRistorante LEFT JOIN AppartenenzaPM apm ON p.codiceProdotto=apm.codProd_fk LEFT JOIN Menu m ON apm.codMenu_fk=m.codiceMenu WHERE r.citta=? AND (p.sconto>0 OR m.sconto>0) LIMIT ?,?");
            ps.setString(1, citta);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());
            Map<Integer, Ristorante> ristoranti = new LinkedHashMap<>();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codiceRistorante = rs.getInt("r.codiceRistorante");
                if (!ristoranti.containsKey(codiceRistorante)) {
                    Ristorante r = RistoranteExtractor.extract(rs);
                    ristoranti.put(codiceRistorante, r);
                }
            }

            if (ristoranti.isEmpty())
                return null;

            StringJoiner sj = new StringJoiner(",", "(", ")");
            for (int key : ristoranti.keySet()) {
                sj.add(Integer.toString(key));
            }

            PreparedStatement disp = conn.prepareStatement("SELECT d.codRis_fk, d.giorno, d.oraApertura, d.oraChiusura FROM Disponibilita d WHERE d.codRis_fk IN " + sj.toString());
            ResultSet setDisp = disp.executeQuery();

            while (setDisp.next()) {
                int codiceRistorante = setDisp.getInt("d.codRis_fk");
                Disponibilita d = DisponibilitaExtractor.extract(setDisp);
                ristoranti.get(codiceRistorante).getGiorni().add(d);
            }

            PreparedStatement tip = conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN " + sj.toString());
            ResultSet setTip = tip.executeQuery();

            while (setTip.next()) {
                int codiceRistorante = setTip.getInt("art.codRis_fk");
                Tipologia t = new Tipologia();
                t.setNome(setTip.getString("t.nome"));
                t.setDescrizione(setTip.getString("t.descrizione"));
                ristoranti.get(codiceRistorante).getTipologie().add(t);
            }

            if (ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());
        }
    }

    public ArrayList<Ristorante> doRetrieveByTipologieCitta(ArrayList<String> tipologie, String citta, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            StringJoiner sj_tipologie=new StringJoiner(",", "(", ")");
            for(String tipologia : tipologie){
                tipologia="'" + tipologia + "'";
                sj_tipologie.add(tipologia);
            }
            PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE art.nomeTip_fk IN " + sj_tipologie.toString() + " AND r.citta=? LIMIT ?,?");
            ps.setString(1,citta);
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            System.out.println(ps.toString());
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int codiceRistorante=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(codiceRistorante)){
                    Ristorante r=RistoranteExtractor.extract(rs);
                    ristoranti.put(codiceRistorante, r);
                }
            }

            if(ristoranti.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: ristoranti.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement disp=conn.prepareStatement("SELECT d,codRis_fk, d.giorno, d.oraApertura, d.oraChiusura FROM Disponibilita d WHERE d.codRis_fk IN "+sj.toString());
            ResultSet setDisp=disp.executeQuery();

            while(setDisp.next()){
                int codiceRistorante=setDisp.getInt("codRis_fk");
                Disponibilita d =DisponibilitaExtractor.extract(setDisp);
                ristoranti.get(codiceRistorante).getGiorni().add(d);
            }

            PreparedStatement tip=conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN " + sj.toString());
            ResultSet setTip=tip.executeQuery();

            while(setTip.next()){
                int codiceRistorante=setTip.getInt("art.codRis_fk");
                Tipologia t=new Tipologia();
                t.setNome(setTip.getString("t.nome"));
                t.setDescrizione(setTip.getString("t.descrizione"));
                ristoranti.get(codiceRistorante).getTipologie().add(t);
            }

            if(ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());
        }
    }

    public ArrayList<Ristorante> doRetrieveByTipologiaCitta(String nomeTipologia, String citta, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, d.giorno, d.oraApertura, d.oraChiusura FROM Ristorante r INNER JOIN Disponibilita d ON d.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE art.nomeTip_fk=? AND r.citta=? LIMIT ?,?");
            ps.setString(1, nomeTipologia);
            ps.setString(2,citta);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int codiceRistorante=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(codiceRistorante)){
                    Ristorante r=RistoranteExtractor.extract(rs);
                    ristoranti.put(codiceRistorante, r);
                }

                Disponibilita d =DisponibilitaExtractor.extract(rs);
                ristoranti.get(codiceRistorante).getGiorni().add(d);
            }

            if(ristoranti.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: ristoranti.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement tip=conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN " + sj.toString());
            ResultSet setTip=tip.executeQuery();

            while(setTip.next()){
                int codiceRistorante=setTip.getInt("art.codRis_fk");
                Tipologia t=new Tipologia();
                t.setNome(setTip.getString("t.nome"));
                t.setDescrizione(setTip.getString("t.descrizione"));
                ristoranti.get(codiceRistorante).getTipologie().add(t);
            }

            if(ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());
        }
    }

    public ArrayList<Ristorante> doRetrieveByCitta(String citta, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, d.giorno, d.oraApertura, d.oraChiusura FROM Ristorante r INNER JOIN Disponibilita d ON d.codRis_fk=r.codiceRistorante WHERE r.citta=? LIMIT ?,?");
            ps.setString(1,citta);
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int codiceRistorante=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(codiceRistorante)){
                    Ristorante r=RistoranteExtractor.extract(rs);
                    ristoranti.put(codiceRistorante, r);
                }

                Disponibilita d=DisponibilitaExtractor.extract(rs);
                ristoranti.get(codiceRistorante).getGiorni().add(d);
            }

            if(ristoranti.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: ristoranti.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement tip=conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN " + sj.toString());
            ResultSet setTip=tip.executeQuery();

            while(setTip.next()){
                int codiceRistorante=setTip.getInt("art.codRis_fk");
                Tipologia t=new Tipologia();
                t.setNome(setTip.getString("t.nome"));
                t.setDescrizione(setTip.getString("t.descrizione"));
                ristoranti.get(codiceRistorante).getTipologie().add(t);
            }

            if(ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());
        }
    }

    // restituisce i ristoranti con un tasso di consegna inferiore o uguale a quello inserito
    public ArrayList<Ristorante> doRetrieveByTassoConsegna(float tasso, String citta, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, d.giorno, d.oraApertura, d.oraChiusura FROM Ristorante r INNER JOIN Disponibilita d ON d.codRis_fk=r.codiceRistorante WHERE tassoConsegna<=? AND r.citta=? LIMIT ?,?");
            ps.setFloat(1, tasso);
            ps.setString(2,citta);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int codiceRistorante=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(codiceRistorante)){
                    Ristorante r=RistoranteExtractor.extract(rs);
                    ristoranti.put(codiceRistorante, r);
                }

                Disponibilita d=DisponibilitaExtractor.extract(rs);
                ristoranti.get(codiceRistorante).getGiorni().add(d);
            }

            if(ristoranti.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: ristoranti.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement tip=conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN " + sj.toString());
            ResultSet setTip=tip.executeQuery();

            while(setTip.next()){
                int codiceRistorante=setTip.getInt("art.codRis_fk");
                Tipologia t=new Tipologia();
                t.setNome(setTip.getString("t.nome"));
                t.setDescrizione(setTip.getString("t.descrizione"));
                ristoranti.get(codiceRistorante).getTipologie().add(t);
            }

            if(ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());
        }
    }

    //in base alla città dell'utente e al nome del ristorante inserito
    public ArrayList<Ristorante> doRetrieveByNomeAndCitta(String citta, String nome, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, d.giorno, d.oraApertura, d.oraChiusura FROM Ristorante r INNER JOIN Disponibilita d ON d.codRis_fk=r.codiceRistorante WHERE r.nome LIKE ? AND r.citta=? LIMIT ?,?");
            ps.setString(1, "%"+nome+"%");
            ps.setString(2,citta);
            ps.setInt(3,paginator.getOffset());
            ps.setInt(4,paginator.getLimit());
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int codiceRistorante=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(codiceRistorante)){
                    Ristorante r=RistoranteExtractor.extract(rs);
                    ristoranti.put(codiceRistorante, r);
                }

                Disponibilita d=DisponibilitaExtractor.extract(rs);
                ristoranti.get(codiceRistorante).getGiorni().add(d);
            }

            if(ristoranti.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: ristoranti.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement tip=conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN " + sj.toString());
            ResultSet setTip=tip.executeQuery();

            while(setTip.next()){
                int codiceRistorante=setTip.getInt("art.codRis_fk");
                Tipologia t=new Tipologia();
                t.setNome(setTip.getString("t.nome"));
                t.setDescrizione(setTip.getString("t.descrizione"));
                ristoranti.get(codiceRistorante).getTipologie().add(t);
            }

            if(ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());
        }
    }

    public ArrayList<Ristorante> doRetrieveByNome(String nome, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, d.giorno, d.oraApertura, d.oraChiusura FROM Ristorante r INNER JOIN Disponibilita d ON d.codRis_fk=r.codiceRistorante WHERE r.nome LIKE ? LIMIT ?,?");
            ps.setString(1, "%"+nome+"%");
            ps.setInt(2,paginator.getOffset());
            ps.setInt(3,paginator.getLimit());
            Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                int codiceRistorante=rs.getInt("r.codiceRistorante");
                if(!ristoranti.containsKey(codiceRistorante)){
                    Ristorante r=RistoranteExtractor.extract(rs);
                    ristoranti.put(codiceRistorante, r);
                }

                Disponibilita d=DisponibilitaExtractor.extract(rs);
                ristoranti.get(codiceRistorante).getGiorni().add(d);
            }

            if(ristoranti.isEmpty())
                return null;

            StringJoiner sj=new StringJoiner(",","(", ")");
            for(int key: ristoranti.keySet()){
                sj.add(Integer.toString(key));
            }

            PreparedStatement tip=conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN " + sj.toString());
            ResultSet setTip=tip.executeQuery();

            while(setTip.next()){
                int codiceRistorante=setTip.getInt("art.codRis_fk");
                Tipologia t=new Tipologia();
                t.setNome(setTip.getString("t.nome"));
                t.setDescrizione(setTip.getString("t.descrizione"));
                ristoranti.get(codiceRistorante).getTipologie().add(t);
            }

            if(ristoranti.isEmpty())
                return null;
            else
                return new ArrayList<Ristorante>(ristoranti.values());
        }
    }

    public ArrayList<Utente> doRetrievebyPref(int codiceRistorante, Paginator paginator) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT u.codiceUtente, u.nome, u.cognome, u.email, u.saldo, u.provincia, u.citta, u.via, u.civico, u.interesse, u.amministratore FROM Utente u INNER JOIN Preferenza p ON p.codUtente_fk=u.codiceUtente WHERE p.codRis_fk=? LIMIT ?,?");
            ps.setInt(1, codiceRistorante);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            ArrayList<Utente> utenti=new ArrayList<>();
            while(rs.next()){
                Utente u= UtenteExtractor.extract(rs);
                utenti.add(u);
            }
            if(utenti.isEmpty())
                return null;
            return utenti;
        }
    }

    public ArrayList<Ristorante> doRetrieveAll(Paginator paginator) throws SQLException{
            try(Connection conn=ConPool.getConnection()){
                PreparedStatement ps=conn.prepareStatement("SELECT r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, d.giorno, d.oraApertura, d.oraChiusura FROM Ristorante r INNER JOIN Disponibilita d ON d.codRis_fk=r.codiceRistorante LIMIT ?,?");
                ps.setInt(1,paginator.getOffset());
                ps.setInt(2,paginator.getLimit());
                Map<Integer, Ristorante> ristoranti=new LinkedHashMap<>();
                ResultSet rs=ps.executeQuery();

                while(rs.next()){
                    int codiceRistorante=rs.getInt("r.codiceRistorante");
                    if(!ristoranti.containsKey(codiceRistorante)){
                        Ristorante r=RistoranteExtractor.extract(rs);
                        ristoranti.put(codiceRistorante, r);
                    }

                    Disponibilita d=DisponibilitaExtractor.extract(rs);
                    ristoranti.get(codiceRistorante).getGiorni().add(d);
                }

                if(ristoranti.isEmpty())
                    return null;

                StringJoiner sj=new StringJoiner(",","(", ")");
                for(int key: ristoranti.keySet()){
                    sj.add(Integer.toString(key));
                }

                PreparedStatement tip=conn.prepareStatement("SELECT art.codRis_fk, t.nome, t.descrizione FROM AppartenenzaRT art INNER JOIN Tipologia t ON art.nomeTip_fk=t.nome WHERE art.codRis_fk IN" + sj.toString());
                ResultSet setTip=tip.executeQuery();

                while(setTip.next()){
                    int codiceRistorante=setTip.getInt("art.codRis_fk");
                    Tipologia t=new Tipologia();
                    t.setNome(setTip.getString("t.nome"));
                    t.setDescrizione(setTip.getString("t.descrizione"));
                    ristoranti.get(codiceRistorante).getTipologie().add(t);
                }

                if(ristoranti.isEmpty())
                    return null;
                else
                    return new ArrayList<Ristorante>(ristoranti.values());
            }
    }

    public boolean doSave(Ristorante r) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Ristorante (nome, provincia, citta, via, civico, info, spesaMinima, tassoConsegna, urlImmagine, rating) VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,r.getNome());
            ps.setString(2,r.getProvincia());
            ps.setString(3,r.getCitta());
            ps.setString(4,r.getVia());
            ps.setInt(5,r.getCivico());
            ps.setString(6,r.getInfo());
            ps.setFloat(7,r.getSpesaMinima());
            ps.setFloat(8,r.getTassoConsegna());
            ps.setString(9,r.getUrlImmagine());
            ps.setInt(10,r.getRating());
            if(ps.executeUpdate()!=1){
               return false;
            }
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            r.setCodice(id);
            return true;
        }
    }

    public boolean doUpdate(Ristorante r) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Ristorante SET nome=?, provincia=?, citta=?, via=?, civico=?, info=?, spesaMinima=?, tassoConsegna=?, urlImmagine=?, rating=? WHERE codiceRistorante=?");
            ps.setString(1,r.getNome());
            ps.setString(2,r.getProvincia());
            ps.setString(3,r.getCitta());
            ps.setString(4,r.getVia());
            ps.setInt(5,r.getCivico());
            ps.setString(6,r.getInfo());
            ps.setFloat(7,r.getSpesaMinima());
            ps.setFloat(8,r.getTassoConsegna());
            ps.setString(9, r.getUrlImmagine());
            ps.setInt(10,r.getCodice());
            ps.setInt(11,r.getRating());
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete(int codice) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM Ristorante WHERE codiceRistorante=?");
            ps.setInt(1, codice);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean savePreferenza(int codiceRistorante, int codiceUtente) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Preferenza (codUtente_fk, codRis_fk) VALUES(?,?)");
            ps.setInt(1,codiceUtente);
            ps.setInt(2,codiceRistorante);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean deletePreferenza(int codiceRistorante, int codiceUtente) throws SQLException{
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM Preferenza WHERE codRis_fk=? AND codUtente_fk=?");
            ps.setInt(1, codiceRistorante);
            ps.setInt(2, codiceUtente);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public int countAll() throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numRist FROM Ristorante r ");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

    public int countNome(String nome) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numRist FROM Ristorante r WHERE r.nome LIKE ?");
            ps.setString(1, "%"+nome+"%");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

    public int countNomeCitta(String nome, String citta) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numRist FROM Ristorante r WHERE r.nome LIKE ? AND r.citta=?");
            ps.setString(1, "%"+nome+"%");
            ps.setString(2,citta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

    public int countCitta(String citta) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numRist FROM Ristorante r WHERE r.citta=?");
            ps.setString(1,citta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

    public int countTipologieCitta(ArrayList<String> tipologie, String citta) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {StringJoiner sj_tipologie=new StringJoiner(",", "(", ")");
            for(String tipologia : tipologie){
                tipologia="'" + tipologia + "'";
                sj_tipologie.add(tipologia);
            }
            PreparedStatement ps=conn.prepareStatement("SELECT count(distinct r.codiceRistorante) as numRist FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE art.nomeTip_fk IN " + sj_tipologie.toString() + " AND r.citta=?");
            ps.setString(1,citta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

    public int countTipologiaCitta(String tipologia, String citta) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count( distinct r.codiceRistorante) as numRist FROM Ristorante r INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk WHERE art.nomeTip_fk=? AND r.citta=?");
            ps.setString(1, tipologia);
            ps.setString(2,citta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

    public int countScontoCitta(String citta) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps=conn.prepareStatement("SELECT count(distinct r.codiceRistorante) as numRist FROM Ristorante r INNER JOIN Prodotto p ON p.codRis_fk=r.codiceRistorante LEFT JOIN AppartenenzaPM apm ON p.codiceProdotto=apm.codProd_fk LEFT JOIN Menu m ON apm.codMenu_fk=m.codiceMenu WHERE r.citta=? AND (p.sconto>0 OR m.sconto>0)");
            ps.setString(1,citta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

    public int countTassoConsegna(float tasso, String citta) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps=conn.prepareStatement("SELECT count(*) as numRist FROM Ristorante r WHERE tassoConsegna<=? AND r.citta=?");
            ps.setFloat(1,tasso);
            ps.setString(2,citta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numRist");
            }
            else
                return 0;
        }
    }

}
