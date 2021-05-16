package model.disponibilita;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisponibilitaExtractor {
    public static Disponibilita extract(ResultSet rs) throws SQLException {
        Disponibilita d=new Disponibilita();
        d.setGiorno(rs.getString("d.giorno"));
        d.setOraApertura(rs.getTime("d.oraApertura").toLocalTime());
        d.setOraChiusura(rs.getTime("d.oraChiusura").toLocalTime());
        return d;
    }
}
