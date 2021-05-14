package model.prodotto;

import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;
import model.tipologia.TipologiaDAO;
import model.utility.ConPool;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class ProdottoDAO {
    public ProdottoDAO(){};

    public Prodotto doRetrievebyId(int codiceProdotto) throws SQLException{
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps= conn.prepareStatement("SELECT codiceProdotto, nome, ingredienti, info, prezzo, sconto, valido, urlImmagine, codRis_fk, nomeTip_fk FROM Prodotto p WHERE codiceProdotto=?");
            ps.setInt(1, codiceProdotto);
            ResultSet rs=ps.executeQuery();
            Prodotto p=null;
            if(rs.next()) {
                p = ProdottoExtractor.extract(rs);
                RistoranteDAO service1 = new RistoranteDAO();
                Ristorante r = service1.doRetrieveById(rs.getInt("codRis_fk"));
                p.setRistorante(r);
                TipologiaDAO service2 = new TipologiaDAO();
                Tipologia t = service2.doRetrieveByNome((rs.getString("nomeTip_fk")));
                p.setTipologia(t);
            }
            return p;
        }
    }

    public ArrayList<Prodotto> doRetrieveByRistorante(int codiceRistorante) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT codiceProdotto, nome, ingredienti, info, prezzo, sconto, valido, urlImmagine, nomeTip_fk FROM Prodotto p WHERE codRis_fk=?");
            ps.setInt(1,codiceRistorante);
            ResultSet rs=ps.executeQuery();
            ArrayList<Prodotto> prodotti=new ArrayList<>();
            while(rs.next()) {
                Prodotto p = ProdottoExtractor.extract(rs);
                TipologiaDAO service = new TipologiaDAO();
                Tipologia t = service.doRetrieveByNome((rs.getString("nomeTip_fk")));
                p.setTipologia(t);
                prodotti.add(p);
            }
            return prodotti;
        }
    }

    public boolean doSave(Prodotto p) throws SQLException {
        try(Connection conn= ConPool.getConnection()) {
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

            if(ps.executeUpdate()!=1){
                return false;
            }
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            p.setCodice(id);

            try {
                ps = conn.prepareStatement("INSERT INTO AppartenenzaRT (codRis_fk,nomeTip_fk) VALUES (?,?)");
                ps.setInt(1,p.getRistorante().getCodice());
                ps.setString(2,p.getTipologia().getNome());
            }catch(SQLException e){}
            return true;
        }
    }

    public boolean doUpdate(Prodotto p) throws SQLException{
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE Prodotto p SET nome=?, ingredienti=?, info=?, prezzo=?, sconto=?, valido=?, urlImmagine=?, codRis_fk=?, nomeTip_fk=? WHERE codiceProdotto=?");
            ps.setString(1, p.getNome());
            ps.setString(2, p.getIngredienti());
            ps.setString(3, p.getInfo());
            ps.setFloat(4, p.getPrezzo());
            ps.setInt(5, p.getSconto());
            ps.setBoolean(6, p.isValido());
            ps.setString(7, p.getUrlImmagine());
            ps.setInt(8, p.getRistorante().getCodice());
            ps.setString(9, p.getTipologia().getNome());
            ps.setInt(10, p.getCodice());

            if (ps.executeUpdate() != 1)
                return false;
            else
                return true;
        }
    }

    public boolean updateValidita(int codiceProdotto, boolean valido) throws SQLException {
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE Prodotto SET valido=? WHERE codiceProdotto=?");
            ps.setBoolean(1, valido);
            ps.setInt(2, codiceProdotto);

            if (ps.executeUpdate() != 1)
                return false;
            else
                return true;
        }
    }

}
