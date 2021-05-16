package model.ordine;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class OrdineExtractor {
    public static Ordine extract(ResultSet rs) throws SQLException {
        Ordine o = new Ordine();
        o.setCodice(rs.getInt("o.codiceOrdine"));
        //o.setDataOrdine(LocalDate.from(Instant.ofEpochMilli(rs.getDate("o.dataOrdine").getTime())));
        o.setDataOrdine(rs.getDate("o.dataOrdine").toLocalDate());
        o.setTotale(rs.getFloat("o.totale"));
        o.setConsegnato(rs.getBoolean("o.consegnato"));
        o.setGiudizio(rs.getString("o.giudizio"));
        o.setVoto(rs.getInt("o.voto"));
        o.setNota(rs.getString("o.nota"));
        o.setOraPartenza(rs.getTime("o.oraPartenza").toLocalTime());
        o.setOraArrivo(rs.getTime("o.oraArrivo").toLocalTime());
        o.setMetodoPagamento(rs.getString("o.metodoPagamento"));
        return o;
    }
}
