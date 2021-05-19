package model.prodotto;

import model.ristorante.Ristorante;
import model.ristorante.RistoranteExtractor;
import model.tipologia.Tipologia;
import model.utility.ConPool;
import java.sql.*;
import java.util.ArrayList;

public class ProdottoDAO {
    public ProdottoDAO(){};

    public Prodotto doRetrievebyId(int codiceProdotto) throws SQLException{
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps= conn.prepareStatement("SELECT p.codiceProdotto, p.nome, p.ingredienti, p.info, p.prezzo, p.sconto, p.valido, p.urlImmagine, p.codRis_fk, p.nomeTip_fk, t1.nome, t1.descrizione,  r.codiceRistorante, r.nome, r.provincia, r.citta, r.via, r.civico, r.info, r.spesaMinima, r.tassoConsegna, r.urlImmagine, r.rating, t2.nome, t2.descrizione FROM Prodotto p INNER JOIN Tipologia t1 ON p.nomeTip_fk=t1.nome INNER JOIN Ristorante r ON p.codRis_fk=r.codiceRistorante INNER JOIN AppartenenzaRT art ON r.codiceRistorante=art.codRis_fk INNER JOIN Tipologia t2 ON art.nomeTip_fk=t2.nome WHERE codiceProdotto=?");
            ps.setInt(1, codiceProdotto);
            ResultSet rs=ps.executeQuery();
            Prodotto p=null;
            if(rs.next()) {
                p = ProdottoExtractor.extract(rs);
                Tipologia t =new Tipologia();
                t.setNome(rs.getString("t1.nome"));
                t.setDescrizione(rs.getString("t1.descrizione"));
                p.setTipologia(t);
                Ristorante r= RistoranteExtractor.extract(rs);
                do{
                    Tipologia t_ris=new Tipologia();
                    t_ris.setNome(rs.getString("t2.nome"));
                    t_ris.setDescrizione(rs.getString("t2.descrizione"));
                    r.getTipologie().add(t);
                }while(rs.next());
                p.setRistorante(r);
            }
            return p;
        }
    }

    public ArrayList<Prodotto> doRetrieveByRistorante(int codiceRistorante) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT p.codiceProdotto, p.nome, p.ingredienti, p.info, p.prezzo, p.sconto, p.valido, p.urlImmagine, p.nomeTip_fk, t.nome, t.descrizione FROM Prodotto p INNER JOIN Tipologia t ON p.nomeTip_fk=t.nome WHERE codRis_fk=?");
            ps.setInt(1,codiceRistorante);
            ResultSet rs=ps.executeQuery();
            ArrayList<Prodotto> prodotti=new ArrayList<>();
            while(rs.next()) {
                Prodotto p = ProdottoExtractor.extract(rs);
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                p.setTipologia(t);
                prodotti.add(p);
            }
            return prodotti;
        }
    }

    public boolean doSave(Prodotto p) throws SQLException {
        try(Connection conn= ConPool.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Prodotto(nome, ingredienti, info, prezzo, sconto, valido, urlImmagine, codRis_fk, nomeTip_fk) VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getIngredienti());
            ps.setString(3, p.getInfo());
            ps.setFloat(4, p.getPrezzo());
            ps.setInt(5, p.getSconto());
            ps.setBoolean(6, p.isValido());
            ps.setString(7, p.getUrlImmagine());
            ps.setInt(8, p.getRistorante().getCodice());
            ps.setString(9, p.getTipologia().getNome());

            int rows=ps.executeUpdate();
            if(rows!=1)
            {
                conn.setAutoCommit(true);
                return false;
            }

            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            p.setCodice(id);

            ps = conn.prepareStatement("INSERT INTO AppartenenzaRT (codRis_fk,nomeTip_fk) VALUES (?,?)");
            ps.setInt(1,p.getRistorante().getCodice());
            ps.setString(2,p.getTipologia().getNome());
            int total=ps.executeUpdate();

            if (rows==total)
            {
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

    public boolean doUpdate(Prodotto p) throws SQLException{
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE Prodotto p SET nome=?, ingredienti=?, info=?, prezzo=?, sconto=?, urlImmagine=?, codRis_fk=?, nomeTip_fk=? WHERE codiceProdotto=?");
            ps.setString(1, p.getNome());
            ps.setString(2, p.getIngredienti());
            ps.setString(3, p.getInfo());
            ps.setFloat(4, p.getPrezzo());
            ps.setInt(5, p.getSconto());
            ps.setString(6, p.getUrlImmagine());
            ps.setInt(7, p.getRistorante().getCodice());
            ps.setString(8, p.getTipologia().getNome());
            ps.setInt(9, p.getCodice());

            if (ps.executeUpdate() != 1)
                return false;
            else
                return true;
        }
    }

    public boolean updateValidita(Prodotto p, boolean valido) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("UPDATE Prodotto SET valido=? WHERE codiceProdotto=?");
            ps.setBoolean(1, valido);
            ps.setInt(2, p.getCodice());

            if (ps.executeUpdate() != 1) {
                conn.setAutoCommit(true);
                return false;
            }
            else {
                ps = conn.prepareStatement("SELECT Count(*) FROM AppartenenzaRT art INNER JOIN Prodotto p ON (p.codRis_fk=art.codRis_fk AND p.nomeTip_fk=art.nomeTip_fk)  WHERE p.codRis_fk=? AND p.nomeTip_fk=? AND p.valido=true");
                ps.setInt(1, p.getRistorante().getCodice());
                ps.setString(2, p.getTipologia().getNome());
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    int count=rs.getInt(1);
                    if(count==0){
                        if(valido){
                            ps=conn.prepareStatement("INSERT INTO AppartenenzaRT(codRis_fk, nomeTip_fk) VALUES(?,?)");
                        }
                        else{
                            ps=conn.prepareStatement("DELETE FROM AppartenenzaRT WHERE codRis_fk=? AND nomeTip_fk=?");
                        }

                        ps.setInt(1, p.getRistorante().getCodice());
                        ps.setString(2, p.getTipologia().getNome());
                        if(ps.executeUpdate()!=1) {
                            conn.rollback();
                            conn.setAutoCommit(true);
                            return false;
                        }

                        conn.commit();
                        conn.setAutoCommit(true);
                        return true;

                    }

                    conn.commit();
                    conn.setAutoCommit(true);
                    return true;

                }
                conn.rollback();
                conn.setAutoCommit(true);
                return false;
            }
        }
    }

}
