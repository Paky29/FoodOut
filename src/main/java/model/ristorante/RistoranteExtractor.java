package model.ristorante;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RistoranteExtractor {
    public static Ristorante extract(ResultSet rs) throws SQLException {
        Ristorante r=new Ristorante();
        r.setCodice(rs.getInt("r.codiceRistorante"));
        r.setNome(rs.getString("r.nome"));
        r.setProvincia(rs.getString("r.provincia"));
        r.setCitta(rs.getString("r.citta"));
        r.setVia(rs.getString("r.via"));
        r.setCivico(rs.getInt("r.civico"));
        r.setInfo(rs.getString("r.info"));
        r.setSpesaMinima(rs.getFloat("r.spesaMinima"));
        r.setTassoConsegna(rs.getFloat("r.tassoConsegna"));
        r.setUrlImmagine(rs.getString("r.urlImmagine"));
        r.setValido(rs.getBoolean("r.valido"));
        r.setRating(rs.getInt("r.rating"));
        return r;
    }
}
