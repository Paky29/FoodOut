package model.rider;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RiderExtractor {
    public RiderExtractor(){}

    public static Rider extract(ResultSet rs) throws SQLException {
        Rider r=new Rider();
        r.setCodice(rs.getInt("rd.codiceRider"));
        r.setEmail(rs.getString("rd.email"));
        r.setCitta(rs.getString("rd.citta"));
        r.setVeicolo(rs.getString("rd.veicolo"));
        return r;
    }
}
