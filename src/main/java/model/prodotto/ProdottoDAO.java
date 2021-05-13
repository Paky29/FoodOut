package model.prodotto;

import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;
import model.tipologia.TipologiaDAO;
import model.utility.ConPool;

import java.sql.*;

public class ProdottoDAO {
    public ProdottoDAO(){};

    public Prodotto doRetrievebyId(int codiceProdotto) throws SQLException{
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps= conn.prepareStatement("SELECT codiceProdotto, nome, ingredienti, genere, info, prezzo, sconto, valido, urlImmagine, codRis_fk, nomeTip_fk WHERE codiceProdotto=?");
            ps.setInt(1, codiceProdotto);
            ResultSet rs=ps.executeQuery();
            Prodotto p=ProdottoExtractor.extract(rs);
            RistoranteDAO service1=new RistoranteDAO();
            Ristorante r=service1.doRetrieveById(rs.getInt("codRis_fk"));
            p.setRistorante(r);
            TipologiaDAO service2=new TipologiaDAO();
            Tipologia t=service2.doRetrieveByNome((rs.getString("nomTip_fk")));
            p.setTipologia(t);
            return p;

        }

    }

    public boolean doSave(Prodotto p) throws SQLException {
        try(Connection conn= ConPool.getConnection()) {
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Prodotto(nome, ingredienti, genere, info, prezzo, sconto, valido, urlImmagine, codRis_fk, nomeTip_fk) VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getIngredienti());
            ps.setString(3, p.getGenere());
            ps.setString(4, p.getInfo());
            ps.setFloat(5, p.getPrezzo());
            ps.setInt(6, p.getSconto());
            ps.setBoolean(7, p.isValido());
            ps.setString(8, p.getUrlImmagine());
            ps.setInt(9, p.getRistorante().getCodice());
            ps.setString(10, p.getTipologia().getNome());

            if(ps.executeUpdate()!=1){
                return false;
            }
            ResultSet rs=ps.getGeneratedKeys();
            rs.next();
            int id=rs.getInt(1);
            p.setCodice(id);
            return true;
        }
    }

    public boolean doUpdate(Prodotto p) throws SQLException{
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE Prodotto p SET nome=?, ingredienti=?, genere=?, info=?, prezzo=?, sconto=?, valido=?, urlImmagine=?, codRis_fk=?, nomeTip_fk=? WHERE codiceProdotto=?");
            ps.setString(1, p.getNome());
            ps.setString(2, p.getIngredienti());
            ps.setString(3, p.getGenere());
            ps.setString(4, p.getInfo());
            ps.setFloat(5, p.getPrezzo());
            ps.setInt(6, p.getSconto());
            ps.setBoolean(7, p.isValido());
            ps.setString(8, p.getUrlImmagine());
            ps.setInt(9, p.getRistorante().getCodice());
            ps.setString(10, p.getTipologia().getNome());
            ps.setInt(11, p.getCodice());

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
