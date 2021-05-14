package model.turno;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnoExtractor {
    public static Turno extract(ResultSet rs) throws SQLException {
            Turno t=new Turno();
            t.setGiorno(rs.getString("t.giorno"));
            t.setOraInizio(rs.getTime("t.oraInizio"));
            t.setOraFine(rs.getTime("t.oraFine"));
            return t;
        }
    }
