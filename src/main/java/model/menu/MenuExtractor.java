package model.menu;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuExtractor {
    public static Menu extract(ResultSet set) throws SQLException {
        Menu m=new Menu();
        m.setCodice(set.getInt("m.codiceMenu"));
        m.setNome(set.getString("m.nome"));
        m.setPrezzo(set.getFloat("m.prezzo"));
        m.setSconto(set.getInt("m.sconto"));
        m.setValido(set.getBoolean("m.valido"));
        return m;
    }
}
