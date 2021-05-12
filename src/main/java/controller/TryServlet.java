package controller;


import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.Tipologia;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/Tryservlet")
public class TryServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        RistoranteDAO service=new RistoranteDAO();
        int id=1;
        Ristorante r= null;
        try {
            r = service.doRetrieveById(id);
            System.out.println(r.getCodice());
            System.out.println(r.getNome());
            System.out.println(r.getProvincia());
            System.out.println(r.getCitta());
            System.out.println(r.getCivico());
            System.out.println(r.getSpesaMinima());
            System.out.println(r.getTassoConsegna());
            System.out.println(r.getGiorni().get(0).getGiorno()+" "+r.getGiorni().get(0).getOraApertura());
            for(Tipologia t: r.getTipologie()){
                System.out.println("Tipologia: " + t.getNome());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
