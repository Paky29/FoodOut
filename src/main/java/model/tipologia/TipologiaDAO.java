package model.tipologia;

import model.utility.ConPool;
import model.utility.Paginator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TipologiaDAO {
    public TipologiaDAO(){}

    public Tipologia doRetrieveByNome(String nomeTipologia) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT nome, descrizione FROM Tipologia WHERE nome=?");
            ps.setString(1, nomeTipologia);
            ResultSet rs=ps.executeQuery();
            Tipologia t=null;
            if(rs.next()) {
                t = new Tipologia();
                t.setNome(rs.getString("nome"));
                t.setDescrizione(rs.getString("descrizione"));
            }
            return t;
        }
    }

    public ArrayList<Tipologia> doRetrieveByNomeLike(String nome,Paginator paginator) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT nome, descrizione FROM Tipologia WHERE nome LIKE ? LIMIT ?,?");
            ps.setString(1, "%" + nome + "%");
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            ArrayList<Tipologia> tipologie=new ArrayList<>();
            while(rs.next()) {
                Tipologia t = new Tipologia();
                t.setNome(rs.getString("nome"));
                t.setDescrizione(rs.getString("descrizione"));
                tipologie.add(t);
            }
            if(tipologie.isEmpty())
                return null;
            else
                return tipologie;
        }
    }

    public ArrayList<Tipologia> doRetrieveAll() throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT nome, descrizione FROM Tipologia t");
            ResultSet rs=ps.executeQuery();
            ArrayList<Tipologia> tipologie=new ArrayList<>();
            while(rs.next()) {
                Tipologia t = new Tipologia();
                t.setNome(rs.getString("nome"));
                t.setDescrizione(rs.getString("descrizione"));
                tipologie.add(t);
            }
            return tipologie;
        }
    }

    public ArrayList<Tipologia> doRetrieveAll(Paginator paginator) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT nome, descrizione FROM Tipologia t LIMIT ?,?");
            ps.setInt(1, paginator.getOffset());
            ps.setInt(2, paginator.getLimit());
            ResultSet rs=ps.executeQuery();
            ArrayList<Tipologia> tipologie=new ArrayList<>();
            while(rs.next()) {
                Tipologia t = new Tipologia();
                t.setNome(rs.getString("nome"));
                t.setDescrizione(rs.getString("descrizione"));
                tipologie.add(t);
            }
            return tipologie;
        }
    }

    public ArrayList<Tipologia> doRetriveByCitta(String citta) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT distinct t.nome, t.descrizione FROM Tipologia t INNER JOIN AppartenenzaRT art ON art.nomeTip_fk=t.nome INNER JOIN Ristorante r ON art.codRis_fk=r.codiceRistorante WHERE r.citta=?");
            ps.setString(1,citta);
            ResultSet rs=ps.executeQuery();
            ArrayList<Tipologia> tipologie=new ArrayList<>();
            while(rs.next()){
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                tipologie.add(t);
            }
            if(tipologie.isEmpty())
                return null;
            else
                return tipologie;
        }
    }

    public ArrayList<Tipologia> doRetriveByCittaNome(String citta, String nome) throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("SELECT distinct t.nome, t.descrizione FROM Tipologia t INNER JOIN AppartenenzaRT art ON art.nomeTip_fk=t.nome INNER JOIN Ristorante r ON art.codRis_fk=r.codiceRistorante WHERE r.citta=? AND r.nome LIKE ?");
            ps.setString(1,citta);
            ps.setString(2, "%" + nome + "%");
            ResultSet rs=ps.executeQuery();
            ArrayList<Tipologia> tipologie=new ArrayList<>();
            while(rs.next()){
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                t.setDescrizione(rs.getString("t.descrizione"));
                tipologie.add(t);
            }
            if(tipologie.isEmpty())
                return null;
            else
                return tipologie;
        }
    }

    public boolean doSave(Tipologia t) throws SQLException {
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("INSERT INTO Tipologia (nome, descrizione) VALUES (?,?)");
            ps.setString(1,t.getNome());
            ps.setString(2,t.getDescrizione());
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public boolean doUpdate(Tipologia t, String nomeVecchio) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("UPDATE Tipologia SET nome=?, descrizione=? WHERE nome=?");
            ps.setString(1,t.getNome());
            ps.setString(2,t.getDescrizione());
            ps.setString(3, nomeVecchio);
            if (ps.executeUpdate() != 1)
                return false;
            else
                return true;
        }
    }

    public boolean doDelete (String nome) throws SQLException{
        try(Connection conn=ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("DELETE FROM Tipologia WHERE nome=?");
            ps.setString(1, nome);
            if(ps.executeUpdate()!=1)
                return false;
            else
                return true;
        }
    }

    public ArrayList<Tipologia> doRetrieveByVendite() throws SQLException {
        try(Connection conn= ConPool.getConnection()){
            PreparedStatement ps=conn.prepareStatement("select distinct t.nome, count(cop.codOrd_fk) as numOrdini from tipologia t inner join prodotto p on t.nome=p.nometip_fk left join composizioneOP cop on p.codiceProdotto=cop.codProd_fk group by t.nome order by numOrdini desc;");
            ResultSet rs=ps.executeQuery();
            ArrayList<Tipologia> tipologie=new ArrayList<>();
            while(rs.next()){
                Tipologia t=new Tipologia();
                t.setNome(rs.getString("t.nome"));
                tipologie.add(t);
            }
            if(tipologie.isEmpty())
                return null;
            else
                return tipologie;
        }
    }

    public int countAll() throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) as numTip FROM Tipologia t");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numTip");
            }
            else
                return 0;
        }
    }

    public int countNome(String nome) throws SQLException {
        try(Connection conn=ConPool.getConnection()) {
            PreparedStatement ps=conn.prepareStatement("SELECT count(nome) as numTip FROM Tipologia WHERE nome LIKE ?");
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("numTip");
            }
            else
                return 0;
        }
    }
}
