import model.tipologia.Tipologia;
import model.tipologia.TipologiaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "Configuration", loadOnStartup = 0, urlPatterns = "/config")
public class Configuration extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        TipologiaDAO service=new TipologiaDAO();
        ArrayList<Tipologia> tipologie= null;
        try {
            tipologie = service.doRetrieveByVendite();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        getServletContext().setAttribute("tipologieVendute",tipologie);
    }
}
