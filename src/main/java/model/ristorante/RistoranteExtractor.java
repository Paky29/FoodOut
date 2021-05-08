package model.ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RistoranteExtractor {
    public static Ristorante extract(ResultSet rs) throws SQLException {
        Ristorante r=new Ristorante();
        r.setCodice(rs.getInt(1));
        r.setNome(rs.getString(2));
        r.setProvincia(rs.getString(3));
        r.setCitta(rs.getString(4));
        r.setVia(rs.getString(5));
        r.setCivico(rs.getInt(6));
        r.setSpesaMinima(rs.getFloat(7));
        r.setTassoConsegna(rs.getFloat(8));
        return r;
    }
}
