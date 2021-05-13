package model.prodotto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdottoExtractor {
    public static Prodotto extract(ResultSet rs) throws SQLException {
        Prodotto p=new Prodotto();
        p.setCodice(rs.getInt("p.codiceProdotto"));
        p.setNome(rs.getString("p.nome"));
        p.setUrlImmagine(rs.getString("p.urlImmagine"));
        p.setSconto(rs.getInt("p.sconto"));
        p.setPrezzo(rs.getFloat("p.prezzo"));
        p.setInfo(rs.getString("p.info"));
        p.setIngredienti(rs.getString("p.ingredienti"));
        p.setValido(rs.getBoolean("p.valido"));
        return p;
    }
}
