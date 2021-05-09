package model.immagine;

import model.ristorante.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImmagineExtractor {
    public static Immagine extract(ResultSet rs) throws SQLException {
        Immagine i=new Immagine();
        i.setUrl(rs.getString("i.url"));
        i.setPrincipale(rs.getBoolean("i.principale"));
        return i;
    }
}
