package model.rider;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RiderExtractor {
    public RiderExtractor(){}

    public static Rider extract(ResultSet rs) throws SQLException {
        Rider r=new Rider();
        r.setCodice(rs.getInt("r.codiceRider"));
        r.setEmail(rs.getString("r.email"));
        r.setPassword(rs.getString("r.pw"));
        r.setCitta(rs.getString("r.citta"));
        r.setVeicolo(rs.getString("r.veicolo"));
        return r;
    }
}
