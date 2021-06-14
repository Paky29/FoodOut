package controller;

import model.tipologia.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="MyInit", urlPatterns = "/MyInit", loadOnStartup = 0)
public class Configurazione extends HttpServlet {
    public void init() throws ServletException {
        TipologiaDAO tipologiaDAO=new TipologiaDAO();
        try {
            List<Tipologia> tipologie=tipologiaDAO.doRetrieveAll();
            getServletContext().setAttribute("tipologie",tipologie);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        super.init();
    }
}
