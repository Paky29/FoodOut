import model.tipologia.Tipologia;
import model.tipologia.TipologiaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "Configuration", loadOnStartup = 0, urlPatterns = "/config")
public class Configuration extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        TipologiaDAO service=new TipologiaDAO();
        Tipologia tipologie=service.doRetrieveByVendite();
        getServletContext().setAttribute("tipologieVendute",tipologie);
    }
}
